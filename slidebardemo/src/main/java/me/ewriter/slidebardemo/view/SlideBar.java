package me.ewriter.slidebardemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import me.ewriter.slidebardemo.R;

/**
 * Created by Zubin on 2016/8/22.
 */
public class SlideBar extends View {

    private static final String TAG = SlideBar.class.getSimpleName();

    OnTouchLetterListener mListener;
    String[] letterArray = getResources().getStringArray(R.array.letter);

    private Paint mPaint;
    private int mTextColor;

    private int mTouchSlop;
    private float mDensity;

    private float mY;
    // slide bar 的 宽度,高度
    private float mHalfWidth, mHalfHeight;
    // 单个字母的高度
    private float mLetterHeight;

    private RectF mIsDownRect = new RectF();


    public SlideBar(Context context) {
        super(context);
        this.initView(context, null);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context, attrs);
    }

    public SlideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mPaint = new Paint();
        mTextColor = Color.GRAY;
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(mTextColor);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mDensity = getContext().getResources().getDisplayMetrics().density;

        setPadding(0, dip2px(20), 0, dip2px(20));
    }

    private int getLettersSize() {
        return letterArray.length;
    }

    private int dip2px(int dipValue) {
        return (int) (dipValue * mDensity + 0.5f);
    }

    // 在 onMeasure 后调用, 这里实际上 SlideBar 是占据了整个屏幕，只是在最右边画了一个 rect
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHalfWidth = w - dip2px(16);
        mHalfHeight = h - getPaddingBottom() - getPaddingBottom();

        float lettersLen = getLettersSize();

        mLetterHeight = mHalfHeight / lettersLen;
        int textSize = (int) (mHalfHeight * 0.7 / lettersLen);
        mPaint.setTextSize(textSize);

        // 设置 slide 矩形区域，四个参数分别对应着 left, top, right, bottom
        mIsDownRect.set(w - dip2px(16 * 2), 0, w, h);
    }

    // 没有处理 wrap_content ,应该要修改
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpec = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpec = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < getLettersSize(); i++) {
            float letterPosY = mLetterHeight * (i + 1) + getPaddingTop();
            canvas.save();
            canvas.drawText(letterArray[i], mHalfWidth, letterPosY, mPaint);
            canvas.restore();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                setBackgroundColor(getResources().getColor(R.color.trans_black));
                Log.d(TAG, "ACTION_DOWN");
                showFloatView(eventY);
                return true;

            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(getResources().getColor(R.color.trans_black));
                Log.d(TAG, "ACTION_MOVE");
                showFloatView(eventY);
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                this.setBackgroundColor(Color.TRANSPARENT);
                return true;

            default:
                return super.onTouchEvent(event);
        }

    }

    public void setOnTouchLetterListener(OnTouchLetterListener listener) {
        mListener = listener;
    }

    private void showFloatView(float eventY) {
        Log.d(TAG, "eventY = " + eventY);
        int sectionIndex = getSectionIndex(eventY);
        mListener.onTouchLetterChange(letterArray[sectionIndex]);

        Log.d(TAG, "current Selected = " + letterArray[sectionIndex]);
    }

    private int getSectionIndex(float eventY) {
        int index = (int) (eventY / mLetterHeight);
        if (index <= 0) {
            return 0;
        }
        if (index >= letterArray.length) {
            index = letterArray.length - 1;
        }
        return index;
    }
}
