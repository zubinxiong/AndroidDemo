package me.ewriter.chapter4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnViewHolder(View view) {
        Toast.makeText(MainActivity.this, "ViewHolder的使用很熟悉，这部分就不再概述", Toast.LENGTH_SHORT).show();
    }

    public void btnScrollHideListView(View view) {
        startActivity(new Intent(this, ScrollHideListView.class));
    }

}
