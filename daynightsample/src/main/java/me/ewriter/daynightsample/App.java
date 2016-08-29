package me.ewriter.daynightsample;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

/**
 * Created by Zubin on 2016/8/29.
 */
public class App extends Application {

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
