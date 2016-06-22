package me.ewriter.art_chapter2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import me.ewriter.art_chapter2.manager.UserManager;
import me.ewriter.art_chapter2.model.User;
import me.ewriter.art_chapter2.utils.MyConstants;
import me.ewriter.art_chapter2.utils.MyUtils;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SecondActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 我们在MainActivity中赋值为2，但是到了 :remote 中值还是1
//        Log.d(TAG, "UserManager.sUserId = " + UserManager.sUserId);

        User user = (User) getIntent().getSerializableExtra("extra_user");
        Log.d(TAG, "user: " + user.toString());
        
        recoverFromFile();
    }

    /**从文件中恢复*/
    private void recoverFromFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                File cacheFile = new File(MyConstants.CACHE_FILE_PATH);
                if (cacheFile.exists()) {
                    ObjectInputStream objectInputStream = null;
                    try {
                        objectInputStream = new ObjectInputStream(new FileInputStream(cacheFile));
                        user = (User) objectInputStream.readObject();
                        Log.d(TAG, "recover user: " + user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        MyUtils.close(objectInputStream);
                    }
                }
            }
        }).start();
    }
}
