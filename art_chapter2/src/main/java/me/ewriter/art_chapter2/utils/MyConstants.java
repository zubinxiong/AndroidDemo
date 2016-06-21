package me.ewriter.art_chapter2.utils;

import android.os.Environment;

import java.io.File;

/**
 * Created by Zubin on 2016/6/21.
 */
public class MyConstants {

    public static final String CHAPTER_2_PATH = Environment.
            getExternalStorageDirectory().getPath() + File.separator + "singwahtiwanna"
            + File.separator + "chapter2" + File.separator;

    public static final String CACHE_FILE_PATH = CHAPTER_2_PATH + "usercache";

    public static final int MSG_FROM_CLIENT = 0;
    public static final int MSG_FROM_SERVICE = 1;
}
