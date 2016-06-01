package me.ewriter.chapter6.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 使用SurfaceView 绘制 正弦曲线不断改变x，y 坐标的值。可以使用一个Path对象来保存正弦函数上的坐标点
 * 在子线程的while 循环中，不断改变坐标值
 * Created by Zubin on 2016/6/1.
 */
public class SinView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private boolean isDraw;
    private int x = 0;
    private int y = 0;
    private Path mPath;
    private Paint mPaint;

    public SinView(Context context) {
        super(context);
        initView();
    }

    public SinView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mHolder = getHolder();
        mHolder.addCallback(this);

        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDraw = true;
        mPath.moveTo(0, 400);
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDraw = false;
    }

    @Override
    public void run() {
        while (isDraw) {
            draw();
            x += 1;
            y = (int) (100*Math.sin(x * 2 * Math.PI / 180) + 400);
            mPath.lineTo(x, y);
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            // SurfaceView 的背景
            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath, mPaint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null)
                mHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}
