package me.ewriter.chapter6.layer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyLayer(this));
    }
}
