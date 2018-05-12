package com.fenazola.mxcome.widget;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.fenazola.mxcome.R;

/**
 * Created by Administrator on 2014/9/9.
 */
@SuppressLint("AppCompatCustomView")
public class RotateTextView extends TextView {

    private static final int DEFAULT_DEGREES = 315;

    private int mDegrees;

    public RotateTextView(Context context) {
        super(context, null);
    }

    public RotateTextView(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.textViewStyle);

        this.setGravity(Gravity.CENTER);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RotateTextView);

        mDegrees = a.getDimensionPixelSize(R.styleable.RotateTextView_degree, DEFAULT_DEGREES);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
        canvas.rotate(mDegrees, this.getWidth() / 4f, this.getHeight()*2/ 3f);
        super.onDraw(canvas);
        canvas.restore();
    }

    public void setDegrees(int degrees) {
        mDegrees = degrees;
    }

}
