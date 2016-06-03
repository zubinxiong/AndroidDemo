package me.ewriter.chapter12.palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.view.Window;

import me.ewriter.chapter12.R;

public class PaletteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test);

        // 创建Palette 对象
//        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                // 通过Palette 来获取对应的色调
//                Palette.Swatch vibrant = palette.getDarkVibrantSwatch();
//                // 将颜色设置给相应的组件
//                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(vibrant.getRgb()));
//                Window window = getWindow();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    window.setStatusBarColor(vibrant.getRgb());
//                }
//
//            };
//        });

        // 上面的创建方法被废弃了
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getMutedSwatch();
                if (swatch != null) {
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(swatch.getRgb()));
                }
            }
        });

    }
}
