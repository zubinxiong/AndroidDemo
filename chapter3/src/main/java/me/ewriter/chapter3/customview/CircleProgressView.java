package me.ewriter.chapter3.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Zubin on 2016/5/24.
 */
public class CircleProgressView extends View {

    private int mMeasureHeight;
    private int mMeasureWidth;

    private Paint mCirclePaint;
    private float mCircleXY;
    private float mRadius;

    private Paint mArcPaint;
    private RectF mArcRectF;
    private float mSweepAngle;
    private float mSweepValue = 66;

    private Paint mTextPaint;
    private String mShowText;
    private float mShowTextSize;

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMeasureHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mMeasureWidth, mMeasureHeight);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制圆, 圆心x，圆心y， 半径，画笔
        canvas.drawCircle(mCircleXY, mCircleXY, mRadius, mCirclePaint);
        // 绘制弧线
        // 圆弧所在的椭圆对象， 圆弧的起始角度， 圆弧的角度， 是否显示半径连线， paint
        // 以时钟3点的方向为0°，逆时针为正方向
        canvas.drawArc(mArcRectF, 270, mSweepAngle, false,mArcPaint);
        // 绘制文字
        canvas.drawText(mShowText, 0, mShowText.length(), mCircleXY,
                mCircleXY + (mShowTextSize / 4), mTextPaint);
    }

    private void initView() {
        float length = 0;
        // 取长宽中较小的做一个正方形
        if (mMeasureHeight >= mMeasureWidth) {
            length = mMeasureWidth;
        } else {
            length = mMeasureHeight;
        }

        mCircleXY = length / 2;

        mRadius = (float) (length * 0.5 / 2);
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));

        // RectF 包含一个矩形的四个单精度浮点坐标
        // left，top 是左上角的坐标，right，bottom 是右下角的坐标
        mArcRectF = new RectF(
                (float) (length * 0.1),
                (float) (length * 0.1),
                (float) (length * 0.9),
                (float) (length * 0.9));
        mSweepAngle = (mSweepValue / 100f) * 360f;
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setColor(getResources().getColor(
                android.R.color.holo_blue_bright));
        // 设置弧线的宽度,而不是我想的两个圆叠加的效果
        mArcPaint.setStrokeWidth((float) (length * 0.1));
        mArcPaint.setStyle(Paint.Style.STROKE);

        mShowText = setShowText();
        mShowTextSize = setShowTextSize();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mShowTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private float setShowTextSize() {
        this.invalidate();
        return 50;
    }

    private String setShowText() {
        this.invalidate();
        return "Android Skill";
    }

    public void forceInvalidate() {
        this.invalidate();
    }

    public void setSweepValue(float sweepValue) {
        if (sweepValue != 0) {
            mSweepValue = sweepValue;
        } else {
            mSweepValue = 25;
        }
        this.invalidate();
    }
}
