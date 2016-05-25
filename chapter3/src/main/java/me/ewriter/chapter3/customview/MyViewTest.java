package me.ewriter.chapter3.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import me.ewriter.chapter3.R;

public class MyViewTest extends AppCompatActivity {

    private TopBar mTopbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag = getIntent().getIntExtra("flag", -1);
        switch (flag) {
            case 0:
                setContentView(R.layout.topbar_test);
                mTopbar = (TopBar) findViewById(R.id.topbar);
                mTopbar.setOnTopbarClickListener(new TopBar.topbarClickListener() {
                    @Override
                    public void leftClick() {
                        Toast.makeText(MyViewTest.this, "leftClick", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void rightClick() {
                        Toast.makeText(MyViewTest.this, "rightClick", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case 1:
                setContentView(R.layout.my_textview);
                break;

            case 2:
                setContentView(R.layout.shine_textview);
                break;

            case 3:
                setContentView(R.layout.circle_progress);
                CircleProgressView circle = (CircleProgressView) findViewById(R.id.circle);
                circle.setSweepValue(0);
                break;

            case 4:
                setContentView(R.layout.volume);
                break;

            case 5:
                setContentView(R.layout.my_scrollview);
                break;

            case 6:
                setContentView(R.layout.touch_event);
            default:
                break;
        }
    }
}
