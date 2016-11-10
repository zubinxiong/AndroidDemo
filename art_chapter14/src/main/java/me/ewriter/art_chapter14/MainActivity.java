package me.ewriter.art_chapter14;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.text);
        tv.setText(stringFromJNI());
    }

    public native String stringFromJNI();

    public static void methodCalledByJni(String msgFromJni) {
        Log.d(TAG, "methodCalledByJni, msg = " + msgFromJni);
    }
}
