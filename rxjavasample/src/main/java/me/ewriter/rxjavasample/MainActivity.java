package me.ewriter.rxjavasample;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createObserver();
        createObservable();

        printArray();
        displayImage();

        // RxJava 中，发送和订阅是在同一个线程中的。下面就引入了 Scheduler,内置了几个 Scheduler
        simpleScheduler();

        // RxJava 的核心是对事件序列进行变换：变换是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列
        simpleMap();
        simpleFlatMap();

        // 线程的自由控制
        SchedulerTwo();
    }

    /** 创建观察者 observer，它决定事件触发时的行为*/
    private void createObserver() {
        // RxJava 还内置了一个 实现了Observer 的抽象类 Subscriber。 实际上Observer
        // 也是转换成 Subscriber 来运行, 多了 onStart 和 unSubscribe 两个方法
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Item: " + s);
            }
        };
    }

    /** 创建 Observable 事件源，当被订阅的时候，OnSubscribe 的 call 方法会被调用 */
    private void createObservable() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("ABC");
                subscriber.onCompleted();
            }
        });
    }

    /** 打印字符串数组, action0 指 onComplete， action1 是 onNext 和 onError*/
    private void printArray() {
        String[] name = {"name1", "name2", "name3"};
        Observable.from(name)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, s);
                    }
                });
    }

    /**指定的一个 drawable 文件 id drawableRes取得图片 ，并显示在 ImageView 中*/
    private void displayImage() {
        final int drawableRes = R.drawable.ic_launcher;
        final ImageView imageView = (ImageView) findViewById(R.id.image);

        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getResources().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }
        });
    }

    /**简单的Scheduler, 并介绍了几种 Scheduler*/
    private void simpleScheduler() {
        // Schedulers.immediate() 当前线程，默认的 Scheduler
        // Schedulers.newThread() 总是启用新线程
        // Schedulers.io()  io操作(读取文件，读取数据库，网络信息交互等)，和上面的区别是 io 内部有可重用的无上限线程池。
        // Schedulers.computation(); CPU 密集型计算，不会被 IO 限制住性能的操作，例如图形计算。固定的线程池，为CPU 核数
        // AndroidScheduler.mainThread() Android 专用的主线程
        // subscribeOn 事件产生的线程, observeOn 事件消费的线程

        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "number" + integer);
                    }
                });

        // 上面的适用于大多数后台线程取数据，主线程显示
    }

    /**RxJava 中基础的 map 变换*/
    private void simpleMap() {
        // map(): 事件对象的直接变换。例如把参数 String 对象 转换成一个 Bitmap 对象后返回。
        // Func1 和 Action1 类似，区别在于一个有返回值，一个没有
        Observable.just("images/logo.png") // 输入类型为 String
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String filePath) { // 参数类型 String
                        return null;
                        // return getBitmapFromPath(filePath); // 返回类型 Bitmap
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
                        //showBitmap(bitmap);
                    }
                });
    }

    /**RxJava 中的 flatMap 变换*/
    private void simpleFlatMap() {
        //  自定义的数据结构
        List<Student.Course> mCourseList = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            Student.Course course = new Student.Course("课程" + i);
            mCourseList.add(course);
        }

        Student student1 = new Student("A", mCourseList);
        Student student2 = new Student("B", mCourseList);
        Student student3 = new Student("C", mCourseList);

        Student[] students = {student1, student2, student3};

        // 打印出一组学生的名字
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "name = " + s);
            }
        };

        Observable.from(students)
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.getName();
                    }
                })
                .subscribe(subscriber);


        // 如果要打印出每个学生的课程，区别在于每个学生有多个课程
        Subscriber<Student> subscriber1 = new Subscriber<Student>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student student) {
                for (int i = 0; i < student.getCourse().size(); i++) {
                    Student.Course course = student.getCourse().get(i);
                    Log.d(TAG, "course = " + course.getName());
                }
            }
        };

        Observable.from(students).subscribe(subscriber1);


        // 如果不想要在 Subscriber 中使用 for 循环，而是直接传入 Course 对象。用map 是不行的，因为map 是一对一的转换，只能使用 flatMap
        Subscriber<Student.Course> subscriber2 = new Subscriber<Student.Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student.Course course) {
                Log.d(TAG, "FlatMap Course = " + course.getName());
            }
        };

        // 和 map 不同的是这里返回的是 Observable 对象，并且没有直接发送到 Subscriber 的回调方法。
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Student.Course>>() {
                    @Override
                    public Observable<Student.Course> call(Student student) {
                        return Observable.from(student.getCourse());
                    }
                })
                .subscribe(subscriber2);

        // 基于同一个基础的变换方法 lift(Operator)， 在 Observable 执行了 lift 方法后，会返回
        // 一个新的Observable,这个新的 Observable 会像一个代理一样，负责接收原始的 Observable
        // 发出的事件， 并在处理后发送给 Subscriber


    }


    private void SchedulerTwo() {
        // observeOn() 指定的是 Subscriber 的线程。但这个 Subscriber 并不一定是subscribe() 参数中的 Subscriber。
        // 而是 observeOn() 执行时的当前 Observable 所对应的 Subscriber ，即它的直接下级 Subscriber 。
        // 换句话说，observeOn() 指定的是它之后的操作所在的线程。因此如果有多次切换线程的需求，
        // 只要在每个想要切换线程的位置调用一次 observeOn() 即可.

        // 不同于 observeOn() ， subscribeOn() 的位置放在哪里都可以，但它是只能调用一次的。
        Observable.just(1, 2, 3, 4) // io 线程，由 subscribeOn() 指定
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
//                .map(mapOperator) // 新线程，由 observeOn 中指定
                .observeOn(Schedulers.io())
//                .map(mapOperator2) // IO 线程，由 observeOn() 指定
                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);  // Android 主线程，由 observeOn() 指定
        ;

    }
}
