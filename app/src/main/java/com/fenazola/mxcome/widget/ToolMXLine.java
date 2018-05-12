package com.fenazola.mxcome.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


/**
 * 自定义线
 *
 * @author zm
 */
public class ToolMXLine extends View {
    private int width;
    private int height;
    private Paint mPaint;
    private int lineX = -1;


    public ToolMXLine(Context context) {
        super(context);
    }

    public ToolMXLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolMXLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);//无锯齿
        mPaint.setStrokeWidth(1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        if(lineX!=-1){
            mPaint.setAlpha(255);
            mPaint.setColor(0xFF4BB7FD);
            canvas.drawLine(width/2, height/2, width-lineX, lineX, mPaint);
        }
    }

    public void setLineX(int lineX){
        this.lineX = lineX;
        invalidate();
    }
}