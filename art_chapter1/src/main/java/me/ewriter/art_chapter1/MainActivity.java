package me.ewriter.art_chapter1;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            String test = savedInstanceState.getString("extra_test");
            Log.d(TAG, "[onCreate]restore extra_test:" + test);
        }

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, MainActivity.class);
//                intent.putExtra("time", System.currentTimeMillis());
//                startActivity(intent);
//                startActivity(new Intent(MainActivity.this, SecondActivity.class));

                // 最好处理下如果没有可以接收这种 Intent 的情况该如何处理
                Intent intent = new Intent("com.ryg.charpter_1.c");
                intent.putExtra("time", System.currentTimeMillis());
                intent.addCategory("com.ryg.category.c");
                intent.setDataAndType(Uri.parse("file://abc"), "text/plain");
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "onNewIntent, time=" + intent.getLongExtra("time", 0));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        outState.putString("extra_test", "test");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String test = savedInstanceState.getString("extra_test");
        Log.d(TAG, "[onRestoreInstanceState]restore extra_test:" + test);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 在 Manifest 中配置 configuration 后，config 变了，但是 并没有重新创建
        Log.d(TAG, "onConfigurationChanged, newOrientation:" + newConfig.orientation);
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }
}
