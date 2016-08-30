package me.ewriter.daynightsample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;

import me.ewriter.nightmodedemo.R;

public class TansferActivity extends AppCompatActivity {

    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tansfer);


//        bitmap = getIntent().getParcelableExtra("bitmap");
        File file = new File(this.getCacheDir().getAbsolutePath() + "/intent.png");
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(this.getCacheDir().getAbsolutePath() + "/intent.png");
        }

        View view = new View(this);
        view.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        ((ViewGroup) getWindow().getDecorView()).addView(view, layoutParam);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    overridePendingTransition(R.anim.none, R.anim.fade_out);
                }
            }, 500);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null) {
            bitmap.recycle();
        }

        bitmap = null;
    }
}
