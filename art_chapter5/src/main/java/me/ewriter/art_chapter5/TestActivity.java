package me.ewriter.art_chapter5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TestActivity";

    private Button mButton1;
    private View mButton2;

    private static int sId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getSupportActionBar().setTitle("TestActivity");
        initView();
    }

    private void initView() {
        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(this);
        mButton2 = findViewById(R.id.button2);
        mButton2.setOnClickListener(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                sId++;
                /**
                Notification notification = new Notification();
                notification.icon = R.drawable.ic_launcher;
                notification.tickerText = "hello world";
                notification.when = System.currentTimeMillis();
                notification.flags = Notification.FLAG_AUTO_CANCEL;
                Intent intent = new Intent(this, DemoActivity_2.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                // removed methods https://developer.android.com/sdk/api_diff/23/changes/android.app.Notification.html
                notification.setLatestEventInfo(this, "chapter_5", "this is notification.", pendingIntent);

                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(sId, notification); */


                // 上面是书中的方法，这里修改一下用新的方法构造
                Intent intent = new Intent(this, DemoActivity_2.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setContentText("This is ContentText")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setTicker("hello world")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setWhen(System.currentTimeMillis());

                NotificationManager manager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                manager.notify(sId, mBuilder.build());

                break;

            case R.id.button2:
                sId++;
                // 点其他部分就跳转到 demoActivity1
                Intent intent2 = new Intent(this, DemoActivity_1.class);
                PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent2,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                RemoteViews remoteViews = new RemoteViews(getPackageName(),
                        R.layout.layout_notification);
                remoteViews.setTextViewText(R.id.msg, "chapter_5");
                remoteViews.setImageViewResource(R.id.icon, R.drawable.icon1);
                // 点文字就跳转到 demoActivity2
                PendingIntent openActivity2PendingInent = PendingIntent.getActivity(this, 0,
                        new Intent(this, DemoActivity_2.class), PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.open_activity2, openActivity2PendingInent);

                NotificationCompat.Builder mBuilder2 = new NotificationCompat.Builder(this)
                        .setTicker("hello world")
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent2)
                        .setContent(remoteViews);

                NotificationManager manager2 =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                manager2.notify(sId, mBuilder2.build());

                break;
        }
    }
}
