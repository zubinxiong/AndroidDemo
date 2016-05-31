package me.ewriter.chapter6.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zubin on 2016/5/31.
 */
public class Clock extends View {

    private int mHeight, mWidth;

    public Clock(Context context) {
        super(context);
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取宽高参数
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //画外圆
        Paint paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(5);
        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, paintCircle);

        // 画刻度
        Paint paintDegree = new Paint();
        paintCircle.setStrokeWidth(3);

        for (int i = 0; i < 24; i++) {
            // 区分整点和非整点
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                // 竖线粗一些
                paintDegree.setStrokeWidth(5);
                paintDegree.setTextSize(30);
                // 高度的一半 - 半径 == 起点位置y, 划线
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2, mWidth / 2,
                        mHeight / 2 - mWidth / 2 + 60, paintDegree);
                String degree = String.valueOf(i);
                // 画数字，0 ，6， 12, 18
                canvas.drawText(degree, mWidth / 2 - paintDegree.measureText(degree) / 2,
                        mHeight / 2 - mWidth / 2 + 90, paintDegree);

            } else {
                paintDegree.setStrokeWidth(3);
                paintDegree.setTextSize(15);
                // 画线
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2,
                        mWidth / 2, mHeight / 2 - mWidth / 2 + 30, paintDegree);
                String degree = String.valueOf(i);
                // 数字
                canvas.drawText(degree, mWidth / 2 - paintDegree.measureText(degree) / 2,
                        mHeight / 2 - mWidth / 2 + 60, paintDegree);
            }
            // 通过旋转换不简化坐标运算, 每次逆时针旋转旋转15度,旋转的是坐标轴 原点移动到圆心
            canvas.rotate(15, mWidth / 2, mHeight /2);
        }

        // 圆心
        Paint paintPoint = new Paint();
        paintPoint.setStrokeWidth(30);
        canvas.drawPoint(mWidth / 2, mHeight /2, paintPoint);
        //指针
        Paint paintHour = new Paint();
        paintHour.setStrokeWidth(20);
        Paint paintMinute = new Paint();
        paintMinute.setStrokeWidth(10);
        canvas.save();
        // 坐标轴平移到圆心
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawLine(0, 0, 100, 100, paintHour);
        canvas.drawLine(0, 0, 100, 200, paintMinute);
        canvas.restore();

    }
}
