package me.ewriter.art_chapter2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import me.ewriter.art_chapter2.manager.UserManager;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 我们在MainActivity中赋值为2，但是到了 :remote 中值还是1
        Log.d(TAG, "UserManager.sUserId = " + UserManager.sUserId);
    }
}
