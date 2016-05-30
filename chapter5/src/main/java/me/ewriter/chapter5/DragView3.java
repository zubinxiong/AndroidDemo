package me.ewriter.chapter5;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by Zubin on 2016/5/30.
 */
public class DragView3 extends View {

    private int lastX;
    private int lastY;
    private Scroller mScroller;

    public DragView3(Context context) {
        super(context);
        initView(context);
    }

    public DragView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DragView3(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setBackgroundColor(Color.GREEN);
        // 初始化Scroller
        mScroller = new Scroller(context);
    }

    // 这个方法是Scroller 类的核心，系统在绘制View 的时候会在draw方法中调用该方法
    @Override
    public void computeScroll() {
        super.computeScroll();
        // 判断Scroller 是否执行完毕
        if (mScroller.computeScrollOffset()) {
            // 返回true 时，动画还没执行完毕, getCurrX 返回的是当前的偏移量，Y同理
            ((View) getParent()).scrollTo(mScroller.getCurrX(),
                    mScroller.getCurrY());

            // 通过重绘来不断调用computeScroll.
            // computeScroll 只会在最初绘制一次，因此需要手动invalidate - onDraw - computeScroll 循环
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int currentX = (int) event.getX();
        int currentY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = currentX;
                lastY = currentY;
                break;

            case MotionEvent.ACTION_MOVE:
                int offsetX = currentX - lastX;
                int offsetY = currentY - lastY;
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;

            case MotionEvent.ACTION_UP:
                // 当手指离开的时候，滚动会之前的位置
                View viewGroup = (View) getParent();
                mScroller.startScroll(viewGroup.getScrollX(), viewGroup.getScrollY(),
                        -viewGroup.getScrollX(), -viewGroup.getScrollY());

                // 这个地方还是需要调用invalidate 方法，来通知view 重绘，然后触发computeScroll 中的重绘方法
                invalidate();
                break;
        }
        return true;
    }
}
