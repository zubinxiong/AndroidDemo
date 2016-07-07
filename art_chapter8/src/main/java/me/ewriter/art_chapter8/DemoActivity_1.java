package me.ewriter.art_chapter8;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class DemoActivity_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_activity_1);
        initView();
    }

    private void initView() {
        Dialog dialog = new Dialog(this.getApplicationContext());
        TextView textView = new TextView(this);
        textView.setText("this is toast!");
        dialog.setContentView(textView);
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        dialog.show();
    }
}
