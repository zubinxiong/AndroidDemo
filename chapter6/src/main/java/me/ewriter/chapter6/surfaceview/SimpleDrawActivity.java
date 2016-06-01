package me.ewriter.chapter6.surfaceview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SimpleDrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SimpleDraw(this));
    }
}
