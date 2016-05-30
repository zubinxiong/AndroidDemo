package me.ewriter.chapter5;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Zubin on 2016/5/30.
 */
public class DragView1 extends View {

    private int lastX;
    private int lastY;

    public DragView1(Context context) {
        super(context);
        initView();
    }

    public DragView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        // 给View设置背景颜色，便于观察
        setBackgroundColor(Color.BLUE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录触摸点坐标
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                // 计算偏移量
                int offsetX = x - lastX;
                int offsetY = y - lastY;

                // 在当前left、top、right、bottom的基础上加上偏移量
//                layout(getLeft() + offsetX, getTop() + offsetY,
//                        getRight() + offsetX, getBottom() + offsetY);

                // 这两个方法相当于是对偏移量的一个封装，效果与上面用layout相同
//                offsetLeftAndRight(offsetX);
//                offsetTopAndBottom(offsetY);

                // scrollBy 移动的是content， 所以不会移动，因此需要先getParent ，这样获得的才是这个View
                // 运行后发现View 的位置会乱动，可以理解为当前可显示的部分移动了，View 并没有移动
                // 因此要让View 跟手指需要将增量设置为负即反方向
                ((View)getParent()).scrollBy(-offsetX, -offsetY);
                break;
        }

        return true;
    }
}
