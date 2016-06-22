package me.ewriter.art_chapter2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import me.ewriter.art_chapter2.aidl.Book;
import me.ewriter.art_chapter2.manager.UserManager;
import me.ewriter.art_chapter2.model.User;
import me.ewriter.art_chapter2.utils.MyConstants;
import me.ewriter.art_chapter2.utils.MyUtils;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserManager.sUserId = 2;
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SecondActivity.class);
                User user = new User(0, "jake", true);
                user.book = new Book();
                // 序列化传输数据
                intent.putExtra("extra_user", (Serializable) user);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        Log.d(TAG, "UserManage.sUserId=" + UserManager.sUserId);

        persistToFile();
        super.onResume();
    }

    /**使用文件共享*/
    private void persistToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(1, "hello world", false);
                File dir = new File(MyConstants.CHAPTER_2_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File cachedFile = new File(MyConstants.CACHE_FILE_PATH);
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(cachedFile));
                    objectOutputStream.writeObject(user);
                    Log.d(TAG, "persist user: " + user);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    MyUtils.close(objectOutputStream);
                }
            }
        }).start();
    }
}
