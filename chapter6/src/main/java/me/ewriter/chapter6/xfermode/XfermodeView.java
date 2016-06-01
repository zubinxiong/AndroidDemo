package me.ewriter.chapter6.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import me.ewriter.chapter6.R;

/**
 * Created by Zubin on 2016/6/1.
 * 实现刮刮卡的效果，大致思路就是上下两个View ，使用DST_IN 显示， 用Path 保存手指滑动的路径
 */
public class XfermodeView extends View {

    // 背景和前景bitmap
    private Bitmap mBgBitmap, mFgBitmap;
    private Paint mPaint;
    private Canvas mCanvas;
    private Path mPath;

    public XfermodeView(Context context) {
        super(context);
        initView();
    }

    public XfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        // 画笔的透明度为0， 这样才会出现擦除的效果
        mPaint.setAlpha(0);
        // 取相交部分开始画的内容,在这里取的是背景
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(50);
        mPaint.setStrokeCap(Paint.Cap.ROUND);

        mPath = new Path();
        mBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);

        mFgBitmap = Bitmap.createBitmap(mBgBitmap.getWidth(), mBgBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(mFgBitmap);
        mCanvas.drawColor(Color.GRAY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                // 不会绘制，只是移动画笔到手指刚刚触碰的位置
                mPath.moveTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE:
                // 两点连城一条线
                mPath.lineTo(event.getX(), event.getY());
                break;
        }
        // 在前景行画path，因为paint 是透明的，所以会看到背景图片
        mCanvas.drawPath(mPath, mPaint);
        // 画完刚才的路径后，调用invalidate，重绘ondraw
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
        canvas.drawBitmap(mFgBitmap, 0, 0, null);
    }
}
