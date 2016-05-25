package me.ewriter.chapter3.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.ewriter.chapter3.R;

public class MainActivity extends AppCompatActivity {

    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIntent = new Intent(this, MyViewTest.class);
    }

    public void btnMyTextView(View view) {
        mIntent.putExtra("flag", 1);
        startActivity(mIntent);
    }

    public void btnShineTextView(View view) {
        mIntent.putExtra("flag", 2);
        startActivity(mIntent);
    }

    public void btnTopBar(View view) {
        mIntent.putExtra("flag", 0);
        startActivity(mIntent);
    }

    public void btnCircleProgress(View view) {
        mIntent.putExtra("flag", 3);
        startActivity(mIntent);
    }

    public void btnVolumeView(View view) {
        mIntent.putExtra("flag", 4);
        startActivity(mIntent);
    }

    public void btnMyScrollView(View view) {
        mIntent.putExtra("flag", 5);
        startActivity(mIntent);
    }

    // ViewGroup （dispatchTouchEvent, onInterceptTouchEvent）比View(dispatchTouchEvent) 的传递多了一个onInterceptTouchEvent方法
    // View 和ViewGroup 事件的消费是一致的都是在onTouchEvent 中
    // 事件的传递是从上到下的在这个例子重视MyViewGroupA -> MyViewGroupB -> MyView (返回true, 拦截了， 返回false，不拦截 继续)
    // 事件的处理是传递到底部返回到顶部：MyView -> MyGroupB -> MyGroupA (true，处理了， false , 没处理)
    public void btnTouchEvent(View view) {
        mIntent.putExtra("flag", 6);
        startActivity(mIntent);
    }
}
