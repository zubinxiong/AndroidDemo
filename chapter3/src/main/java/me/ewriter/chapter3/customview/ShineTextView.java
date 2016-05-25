package me.ewriter.chapter3.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Zubin on 2016/5/24.
 */
public class ShineTextView extends TextView {

    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private Paint mPaint;
    private int mViewWidth = 0;
    private int mTranslate = 0;

    /** 直接在代码中new 这个对象时会调用这个方法 */
    public ShineTextView(Context context) {
        super(context);
    }

    /**
     * 在xml 布局中调用ShineTextView 的时候会调用这个构造函数
     * 最开始我并没有写这个方法，因此在setContentView 的时候报错
     */
    public ShineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 带有自定义标签的时候调用
     * 通常系统默认只会调用前两个构造函数，第三个构造函数通常是我们自己主动调用的
     * <br>参考： http://blog.csdn.net/wzy_1988/article/details/49619773
     */
    public ShineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                // 使用getPaint()方法获取当前绘制TextView 的Paint 对象, 并设置线性渐变效果
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(0 , 0, mViewWidth, 0,
                        new int[]{Color.BLUE, 0xffffff, Color.BLUE}, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth /5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = - mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }
}
