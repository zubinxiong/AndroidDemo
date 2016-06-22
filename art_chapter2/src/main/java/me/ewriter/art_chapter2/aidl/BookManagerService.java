package me.ewriter.art_chapter2.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Zubin on 2016/6/21.
 */
public class BookManagerService extends Service {

    private static final String TAG = "BMS";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
