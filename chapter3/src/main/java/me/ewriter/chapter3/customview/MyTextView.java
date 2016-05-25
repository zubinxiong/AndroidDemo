package me.ewriter.chapter3.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Zubin on 2016/5/24.
 */
public class MyTextView extends TextView {

    private Paint mPaint1, mPaint2;

    public MyTextView(Context context) {
        super(context);
        initView();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**初始化画笔，内部及外部*/
    private void initView() {
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        mPaint1.setStyle(Paint.Style.FILL);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 绘制外层矩形
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);

        // 绘制内层矩形
        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, mPaint2);
        canvas.save();

        // 绘制文字前平移10像素
        canvas.translate(10 , 0);

        super.onDraw(canvas);
    }
}
