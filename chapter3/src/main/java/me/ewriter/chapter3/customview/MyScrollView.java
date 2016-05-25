package me.ewriter.chapter3.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by Zubin on 2016/5/24.
 */
public class MyScrollView extends ViewGroup {

    private int mScreenHeight;
    private Scroller mScroller;
    private int mLastY;
    private int mStart;
    private int mEnd;

    public MyScrollView(Context context) {
        super(context);
        initView(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
        mScroller = new Scroller(context);
    }

    // onLayout 确定子View的位置
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 2. 接着对子View 进行放置位置的设定，每个子View 能够显示一屏，
        // 因此整个ViewGroup 的高度 = 子view 的个数 x 屏幕的高度
        int childCount = getChildCount();
        // 设置ViewGroup 的高度
        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        mlp.height = mScreenHeight * childCount;
        setLayoutParams(mlp);
        // 设置完整个ViewGroup 的高度后，就开始对子View的位置进行设置，通过调用layout方法设置
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
            }
        }
    }

    // onMeasure 测量子View 的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 1.在ViewGroup 能滚动前，先测量好每个子 View 的大小
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    // 在ViewGroup 中添加滑动事件，通常可使用scrollBy方法来辅助滑动，在action_move 中使用scrollBy(0 ,dy)
    // 让手指滑动的时候让ViewGroup中的所有子View 也跟着滚动dy即可
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //  触摸点相对于其所在组件原点的y坐标
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                // Return the scrolled top position of this view.
                // 坐标系原点在Y轴上的偏移量，算上了滚动的部分
                mStart = getScrollY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                if (getScrollY() < 0) {
                    dy = 0;
                }
                if (getScrollY() > getHeight() - mScreenHeight) {
                    dy = 0;
                }

                scrollBy(0, dy);
                mLastY = y;

                break;

            case MotionEvent.ACTION_UP:
                // 在action_up 中判断手指移动的距离，判断是否要移动到上一个或下一个view
                mEnd = getScrollY();
                int dScrollY = mEnd - mStart;
                if (dScrollY > 0) {
                    if (dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(0 , getScrollY(), 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    }
                } else {
                    if (-dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY);
                    }
                }
                break;

        }
        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        // 父类方法中是空的
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }
}
