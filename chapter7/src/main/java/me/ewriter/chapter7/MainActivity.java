package me.ewriter.chapter7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.ewriter.chapter7.animator.DropTestActivity;
import me.ewriter.chapter7.animator.PropertyTest;
import me.ewriter.chapter7.animator.TimerTest;
import me.ewriter.chapter7.customAnimator.CustomAnim;
import me.ewriter.chapter7.customAnimator.CustomTV;
import me.ewriter.chapter7.vector.VectorActivity;
import me.ewriter.chapter7.viewAnim.ViewAnimActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void BtnViewAnim(View view) {
        startActivity(new Intent(this, ViewAnimActivity.class));
    }

    public void BtnPropertyAnim(View view) {
        startActivity(new Intent(this, PropertyTest.class));
    }

    public void BtnValueAnim(View view) {
        startActivity(new Intent(this, TimerTest.class));
    }

    public void BtnValueDropAnim(View view) {
        startActivity(new Intent(this, DropTestActivity.class));
    }

    public void btnAnim(View view) {
        CustomAnim customAnim = new CustomAnim();
        customAnim.setRotateY(30);
        view.startAnimation(customAnim);
    }

    public void imgClose(View view) {
        CustomTV customTV = new CustomTV();
        view.startAnimation(customTV);
    }

    public void BtnVector(View view) {
        startActivity(new Intent(this, VectorActivity.class));
    }
}
