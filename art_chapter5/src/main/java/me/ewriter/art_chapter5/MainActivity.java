package me.ewriter.art_chapter5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import me.ewriter.art_chapter5.utils.MyConstants;

/**
 * MainActivity process 是 ：remote，
 * 模拟通知栏的存在，DemoActivity2 中点击一次就发送一个remoteView 的广播
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private LinearLayout mRemoteViewsContent;

    private BroadcastReceiver mRemoteViewReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            RemoteViews remoteViews = intent.getParcelableExtra(MyConstants.EXTRA_REMOTE_VIEWS);
            if (remoteViews != null) {
                updateUI(remoteViews);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("MainActivity");
        initView();
    }

    private void initView() {
        mRemoteViewsContent = (LinearLayout) findViewById(R.id.remote_views_content);
        IntentFilter filter = new IntentFilter(MyConstants.REMOTE_ACTION);
        registerReceiver(mRemoteViewReceiver, filter);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.button1) {
            Intent intent = new Intent(this, TestActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.button2) {
            Intent intent = new Intent(this, DemoActivity_2.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mRemoteViewReceiver);
        super.onDestroy();
    }

    private void updateUI(RemoteViews remoteViews) {
//        int layoutId = getResources().getIdentifier("layout_simulated_notification",
//                "layout", getPackageName());
//        View view = getLayoutInflater().inflate(layoutId, mRemoteViewsContent, false);
//        remoteViews.reapply(this, view);
//        mRemoteViewsContent.addView(view);

        View view = remoteViews.apply(this, mRemoteViewsContent);
        mRemoteViewsContent.addView(view);
    }

}
