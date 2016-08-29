package me.ewriter.daynightsample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.ewriter.nightmodedemo.R;

public class ZhihuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(R.layout.activity_zhihu);
    }

    // setTheme 必须在 setContentView 之前才能生效
    private void initTheme() {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        boolean isNight = sharedPreferences.getBoolean("isNight", false);
        if (isNight) {
            setTheme(R.style.Night);
        } else {
            setTheme(R.style.Day);
        }
    }

    public void onZhihuClick(View view) {
        // 切换主题设置
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        boolean isNight = sharedPreferences.getBoolean("isNight", false);
        if (isNight) {
            setTheme(R.style.Day);
            sharedPreferences.edit().putBoolean("isNight", false).commit();
        } else {
            setTheme(R.style.Night);
            sharedPreferences.edit().putBoolean("isNight", true).commit();
        }

        showAnim();
        refresh();

        // 或者这里直接 recreate ，但是这个会闪一下，体验不是很好
//        recreate();
    }

    private void showAnim() {
        final View decorView = getWindow().getDecorView();
        // 设置false 清除缓存
        decorView.setDrawingCacheEnabled(false);
        // 设置为 true 获取 bitmap
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache(true);
        Bitmap cacheBitmap = null;

        if (decorView.getDrawingCache() != null) {
            cacheBitmap = Bitmap.createBitmap(decorView.getDrawingCache());
            decorView.setDrawingCacheEnabled(false);
        }

        if (decorView instanceof  ViewGroup && cacheBitmap != null) {
            final View view = new View(this);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(500);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });

            objectAnimator.start();
        }

    }

    /**
     *  更新layout的 view,可以更新部分或者全部来避免通过 recreate 造成的闪烁问题
     *  使用这种方法需要处理好各种 view 来进行设置
     * */
    private void refresh() {
        TypedValue background = new TypedValue();
        TypedValue textColor = new TypedValue();

        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.colorBackground, background, true);
        theme.resolveAttribute(R.attr.colorTextView, textColor, true);

        findViewById(R.id.background).setBackgroundColor(background.data);
        TextView text = (TextView) findViewById(R.id.text);
        text.setTextColor(textColor.data);

        // statusBar and actionbar
        if (Build.VERSION.SDK_INT >= 21) {
            TypedValue typedValue = new TypedValue();
            TypedValue typedValue1 = new TypedValue();
            Resources.Theme theme1 = getTheme();
            theme1.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
            theme1.resolveAttribute(R.attr.colorPrimary, typedValue1, true);
            getWindow().setStatusBarColor(getResources().getColor(typedValue.resourceId));

            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(typedValue1.data));

        }
    }
}
