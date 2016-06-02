package me.ewriter.chapter9;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.ewriter.chapter9.systeminfo.SystemInfoActivity;
import me.ewriter.chapter9.systemtest.AMProcessTestActivity;
import me.ewriter.chapter9.systemtest.PMTestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void BtnSystemInfo(View view) {
        startActivity(new Intent(this, SystemInfoActivity.class));
    }

    public void BtnPackageManager(View view) {
        startActivity(new Intent(this, PMTestActivity.class));
    }

    public void BtnActivityManager(View view) {
        startActivity(new Intent(this, AMProcessTestActivity.class));
    }
}
