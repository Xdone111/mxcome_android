package com.fenazola.mxcome.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.fenazola.mxcome.R;
import com.zss.library.utils.DPUtils;

/**
 * Created by Hades on 16/10/9.
 */
public class GradienterView extends View {
    //定义水平仪仪表盘图片
    private Bitmap bg;
    //定义水平仪中气泡图标
    private Bitmap ball;
    //定义水平仪中气泡的X、Y坐标
    private int ballX, ballY;

    private int centerLeft, centerTop;

    public GradienterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bg = BitmapFactory.decodeResource(getResources(), R.mipmap.tool_gradienter_bg);
        ball = BitmapFactory.decodeResource(getResources(), R.mipmap.tool_gradienter_ball);
        int width = DPUtils.getScreenW(getContext());
        int height = DPUtils.getScreenH(getContext());
        centerLeft = (width - bg.getWidth())/2;
        centerTop = (height - bg.getHeight())/3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制水平仪表盘
        canvas.drawBitmap(bg, centerLeft, centerTop, null);

        //根据气泡坐标绘制气泡
        canvas.drawBitmap(ball, ballX + centerLeft, ballY + centerTop, null);
    }

    public Bitmap getBg() {
        return bg;
    }

    public void setBg(Bitmap bg) {
        this.bg = bg;
    }

    public Bitmap getBall() {
        return ball;
    }

    public void setBall(Bitmap ball) {
        this.ball = ball;
    }

    public int getBallX() {
        return ballX;
    }

    public void setBallX(int ballX) {
        this.ballX = ballX;
    }

    public int getBallY() {
        return ballY;
    }

    public void setBallY(int ballY) {
        this.ballY = ballY;
    }
}
