package me.ewriter.zhihuwelcome;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    IntroAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        adapter = new IntroAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);



        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {

                TextView tv = (TextView) page.findViewById(R.id.intro_text);
                // 1080;
                int left = tv.getLeft();

                Log.d("aaa", "left = " + left);
                Log.d("aaa", "position = " + position);

                if (position < -1) {
                    tv.setAlpha(0);
                } else if (position <= 1) {
                    float alpha = 1 - Math.abs(position);

                    // 0 ~ -1 move 在 变大右移 ，1 ~ 0 在 减小 左移
                    int move = (int) (left - dpToPx(50) * position);

                    tv.setAlpha(alpha);
                    // 设置 getLeft 的距离
                    tv.setTranslationX(move);
                } else {
                    tv.setAlpha(0);
                }
            }
        });
    }

    private int dpToPx(float dp) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


}
