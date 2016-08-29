package me.ewriter.daynightsample;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import me.ewriter.daynightsample.ZhihuActivity;
import me.ewriter.nightmodedemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** 使用 support library 的夜间模式,如果不需要自定义的话不用定义 value-night
     *
     * 参考资料
     * https://kingideayou.github.io/2016/03/07/appcompat_23.2_day_night/
     * http://blog.nkdroidsolutions.com/android-daynight-theme-example-using-appcompat-v23-2/
     * */
    public void onClickSupport(View view) {

        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
        recreate();
    }


    /**
     * 类似 Zhihu 的切换夜间模式
     * */
    public void onZhihuClick(View view) {
        startActivity(new Intent(this, ZhihuActivity.class));
    }

    // Github 上也有一些切换主题的开源库，可以查看
    // https://github.com/Bilibili/MagicaSakura
    // https://github.com/hongyangAndroid/AndroidChangeSkin
}
