package me.ewriter.chapter12;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.ewriter.chapter12.activityTrans.AActivity;
import me.ewriter.chapter12.animatedSelector.AnimatedSelectorActivity;
import me.ewriter.chapter12.circleReveral.CircleReveralActivity;
import me.ewriter.chapter12.notification.NotificationActivity;
import me.ewriter.chapter12.palette.PaletteActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void BtnPaletteDemo(View view) {
        startActivity(new Intent(this, PaletteActivity.class));
    }

    public void BtnActivityTransDemo(View view) {
        startActivity(new Intent(this, AActivity.class));
    }

    public void BtnCircleReveralDemo(View view) {
        startActivity(new Intent(this, CircleReveralActivity.class));
    }

    public void BtnAnimatedSelectorDemo(View view) {
        startActivity(new Intent(this, AnimatedSelectorActivity.class));
    }

    public void BtnNotificationDemo(View view) {
        startActivity(new Intent(this, NotificationActivity.class));
    }

}
