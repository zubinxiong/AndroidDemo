package me.ewriter.art_chapter2;

import android.app.Application;
import android.os.Process;
import android.util.Log;

import me.ewriter.art_chapter2.utils.MyUtils;

/**
 * Created by Zubin on 2016/6/21.
 */
public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = MyUtils.getProcessName(getApplicationContext(), Process.myPid());
        Log.d(TAG, "application oncreate, process name = " + processName);
    }
}
