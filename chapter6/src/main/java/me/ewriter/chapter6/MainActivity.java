package me.ewriter.chapter6;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.ewriter.chapter6.clock.ClockActivity;
import me.ewriter.chapter6.layer.LayerActivity;
import me.ewriter.chapter6.layer.MyLayer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void BtnClock(View view) {
        startActivity(new Intent(this, ClockActivity.class));
    }

    public void BtnMyLayer(View view) {
        startActivity(new Intent(this, LayerActivity.class));
    }
}
