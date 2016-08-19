package me.ewriter.lambdasample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.internal.util.Predicate;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text1);

        // 简化一个接口
        simpleInterface();

        // 简化一个 new Thread
        simpleNewThread();

        // 静态方法引用
        staticfunctionUse();

        // 实例方法引用
        InstanceMethodUse();

        // 构造方法引用
        ConstructMethodUse();

        // Lambda 的域以及访问限制就不举例子了，总结下就是
        // 局部变量 可读不可谢写
        // 成员变量和静态变量 可读可写

        // java.util.function 下常用的函数接口
        commonInterface();

        // Stream
    }

    private void simpleInterface() {
        // 传统的方法实现一个 接口
        Converter<String, Integer> converter = new Converter<String, Integer>() {
            @Override
            public Integer convert(String from) {
                return Integer.parseInt(from);
            }
        };

        Integer result = converter.convert("200");
        Log.d(TAG, "normal result = " + result);

        // Lambda 简化一个接口
        Converter<String, Integer> converter1 = (String str) -> Integer.parseInt(str);
        Integer integer2 = converter1.convert("123");
        Log.d(TAG, "lambda result = " + integer2);
    }

    interface Converter<F, T> {
        T convert(F from);
    }

    private void simpleNewThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("normal thread");
            }
        }).start();

        // 避免使用大量的匿名类
        new Thread(() -> System.out.println("lambda thread")).start();
    }

    static int String2Int(String from) {
        return Integer.valueOf(from);
    }

    /**静态方法引用*/
    private void staticfunctionUse() {
        Converter<String, Integer> converter = new Converter<String, Integer>() {
            @Override
            public Integer convert(String from) {
                return String2Int(from);
            }
        };

        converter.convert("123");

        // 如果上面的转换方法用 Lambda 的静态方法引用
        Converter<String, Integer> converter1 = MainActivity::String2Int;
        converter.convert("222");
    }


    class Helper {
        public int String2int(String from) {
            return Integer.valueOf(from);
        }
    }

    /**实例方法引用*/
    private void InstanceMethodUse() {
        //普通的方法
        Converter<String, Integer> converter = new Converter<String, Integer>() {
            @Override
            public Integer convert(String from) {
                return new Helper().String2int(from);
            }
        };

        converter.convert("123");

        // 实例方法引用
        Helper helper = new Helper();
        Converter<String, Integer> converter1 = helper::String2int;
        converter1.convert("232");
    }

    /**构造方法引用*/
    private void ConstructMethodUse() {
        // 传统的方法, 用工厂方法构造两个对象
        Factory factory = new Factory() {
            @Override
            public Animal create(String name, int age) {
                return new Dog(name, age);
            }
        };
        factory.create("alias", 3);
        factory = new Factory() {
            @Override
            public Animal create(String name, int age) {
                return new Bird(name, age);
            }
        };
        factory.create("smook", 2);

        // 下面用 Lambda 构造方法引用
        Factory<Animal> dogFactory = Dog::new;
        Animal dog = dogFactory.create("alias", 3);

        Factory<Bird> birdFactory = Bird::new;
        Bird bird = birdFactory.create("smook", 4);

    }

    /** java.util.function 下常用的函数接口
     *  这个包是在 api 24 加入的 https://developer.android.com/reference/java/util/function/package-summary.html
     * */
    private void commonInterface() {
        // Predicate 接口， 输入一个参数，返回 Boolean 值
        Predicate<String> predicate = s -> s.length() > 0;
        Log.d(TAG, predicate.apply("abc") + " ;" + predicate.apply(""));

        // Function 接口，接收一个参数，返回单一的结果
        // Supplier 接口， 无输入,无输出
        // Consumer 接口，单一输入，无返回
    }
}
