package me.ewriter.eventbusdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.demo_frag_1, new TopFragment())
                .replace(R.id.demo_frag_2, new BottomFragment())
                .commit();
    }
}
