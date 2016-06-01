package me.ewriter.chapter6.xfermode;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import me.ewriter.chapter6.R;

/**
 * Created by Zubin on 2016/6/1.
 */
public class RoundRectXfermodeView extends View {

    private Bitmap mBitmap;
    private Bitmap mOut;
    private Paint mPaint;

    public RoundRectXfermodeView(Context context) {
        super(context);
        initView();
    }

    public RoundRectXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RoundRectXfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test1);
        mOut = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);


        // 初始化画布，画布的大小取的是图片的长宽大小
        Canvas canvas = new Canvas(mOut);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        // 先画圆角矩形,也就是遮罩层（默认是黑色）
        canvas.drawRoundRect(0, 0,
                mBitmap.getWidth(),
                mBitmap.getHeight(), 80, 80, mPaint);

        // 取两者相交后画的内容
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // 后画矩形的图片
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 在onDraw 的时候画出画布上的内容，如果注释掉这一行，不管上面做了什么都不显示
        canvas.drawBitmap(mOut, 0, 0, null);
    }
}
