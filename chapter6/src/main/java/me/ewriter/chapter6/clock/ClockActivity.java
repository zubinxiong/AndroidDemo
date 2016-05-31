package me.ewriter.chapter6.clock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.ewriter.chapter6.R;

public class ClockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Clock(this));
    }
}
