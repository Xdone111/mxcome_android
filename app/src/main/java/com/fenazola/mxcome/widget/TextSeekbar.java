package com.fenazola.mxcome.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.fenazola.mxcome.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义TextSeekbar
 *
 * @author zm
 */
public class TextSeekbar extends View {
    private int width;
    private int height;
    private int downX = 0;
    private int downY = 0;
    private int upX = 0;
    private int upY = 0;
    private int moveX = 0;
    private int moveY = 0;
    private int itemWidth = 0;
    private Paint mPaint;
    private Bitmap thumb;
    private int section = 0;
    private OnSectionListener onSectionListener;
    private int bitmapHeight = 0;
    private List<String> datas = new ArrayList<>();
    private int fgColor = 0xff4bb7fd;
    private int bgColor = 0xffe2e5e5;
    private int textColor = 0xff8E8E92;
    private Paint mTextPaint;


    public TextSeekbar(Context context) {
        super(context);
    }

    public TextSeekbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        section = 0;
        thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.project_whole_yuan);
        bitmapHeight = thumb.getHeight();
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);//无锯齿
        mPaint.setStrokeWidth(10);
        mTextPaint = new Paint(Paint.DITHER_FLAG);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()));
    }

    public void setDatas(List<String> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.datas = datas;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        setMeasuredDimension(width, height);
        itemWidth = width / (datas.size() - 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha(0);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setAlpha(255);
        mPaint.setColor(bgColor);
        canvas.drawLine(0,
                height * 2 / 3,
                width,
                height * 2 / 3,
                mPaint);

        if (section > 0) {
            mPaint.setColor(fgColor);
            canvas.drawLine(0,
                    height * 2 / 3,
                    width - itemWidth * ((datas.size() - 1) - section) - 2
                    , height * 2 / 3,
                    mPaint);
        }

        if (section == datas.size() - 1) {
            canvas.drawBitmap(
                    thumb,
                    width - bitmapHeight,
                    height * 2 / 3 - bitmapHeight / 2
                    , mPaint);
        } else if (section == 0) {
            canvas.drawBitmap(
                    thumb,
                    0,
                    height * 2 / 3 - bitmapHeight / 2
                    , mPaint);
        } else {
            canvas.drawBitmap(
                    thumb,
                    width - itemWidth * ((datas.size() - 1) - section) - bitmapHeight,
                    height * 2 / 3 - bitmapHeight / 2
                    , mPaint);
        }
        for (int i = 0; i < datas.size(); i++) {
            String item = datas.get(i);
            if (i == section) {
                mTextPaint.setColor(fgColor);
            } else {
                mTextPaint.setColor(textColor);
            }
            if (i == datas.size() - 1) {
                float textWidth = mTextPaint.measureText(item);
                canvas.drawText(item,
                        width - textWidth,
                        height * 2 / 10,
                        mTextPaint);
            } else if (i == 0) {
                canvas.drawText(item,
                        0,
                        height * 2 / 10, mTextPaint);
            } else {
                canvas.drawText(item,
                        width - itemWidth * ((datas.size() - 1) - i) - bitmapHeight,
                        height * 2 / 10, mTextPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                downY = (int) event.getY();
                calcSection(downX, downY);
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = (int) event.getX();
                moveY = (int) event.getY();
                calcSection(moveX, moveY);
                break;
            case MotionEvent.ACTION_UP:
                upX = (int) event.getX();
                upY = (int) event.getY();
                calcSection(upX, upY);
                if (onSectionListener != null) {
                    onSectionListener.onSection(section);
                }
                break;
        }
        return true;
    }

    private void calcSection(int x, int y) {
        if (x <= width) {
            section = (x + itemWidth / 3) / itemWidth;
        } else {
            section = datas.size() - 1;
        }
        invalidate();
    }

    public void setOnSectionListener(OnSectionListener onSectionListener) {
        this.onSectionListener = onSectionListener;
    }

    public void setSection(int section) {
        this.section = section;
        invalidate();
    }

    public int getSection() {
        return this.section;
    }

    public interface OnSectionListener {
        public void onSection(int section);
    }

    public void setSeekBarColor(int fgColor, int bgColor, int textColor) {
        this.fgColor = fgColor;
        this.bgColor = bgColor;
        this.textColor = textColor;
    }
}