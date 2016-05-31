package me.ewriter.chapter6.layer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Zubin on 2016/5/31.
 */
public class MyLayer extends View {

    private Paint mPaint;
    private static final int LAYER_FLAGS =
            Canvas.MATRIX_SAVE_FLAG |
                    Canvas.CLIP_SAVE_FLAG |
                    Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                    Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                    Canvas.CLIP_TO_LAYER_SAVE_FLAG;

    public MyLayer(Context context) {
        super(context);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画布是白色
        canvas.drawColor(Color.WHITE);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(150, 150, 100, mPaint);

        // 将一个layer 入栈，后续的setcolor 和drawcircle 是蓝色圆所在layer 不是同一个
        // 127 是半透明，255 完全不透明
        canvas.saveLayerAlpha(0, 0, 400, 400, 127, LAYER_FLAGS);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(200, 200, 100, mPaint);

        // 出栈，被保存的layer 就在红色圆layer上了
        canvas.restore();
    }
}
