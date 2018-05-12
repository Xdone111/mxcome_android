package com.fenazola.mxcome.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.WindowManager;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.zss.library.utils.DPUtils;

/**
 * Created by Mao Jiqing on 2016/11/4.
 */

public class CustomTransformation extends BitmapTransformation {

    private Paint mPaint; // 画笔
    private Context mContext;
    private int mShapeRes; // 形状的drawable资源

    public CustomTransformation(Context context, int shapeRes) {
        super(context);
        mContext = context;
        mShapeRes = shapeRes;
        // 实例化Paint对象，并设置Xfermode为SRC_IN
        mPaint = new Paint();
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    @Override
    public Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        // 获取到形状资源的Drawable对象
        Drawable shape = ContextCompat.getDrawable(mContext, mShapeRes);
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        if(height != 0){
            double scale = (width * 1.00) / height;
            if (width >= height) {
                width = getBitmapWidth();
                height = (int) (width / scale);
            } else {
                height = getBitmapHeight();
                width = (int) (height * scale);
            }
        }else{
            width = 100;
            height = 100;
        }
        // 居中裁剪图片，调用Glide库中TransformationUtils类的centerCrop()方法完成裁剪，保证图片居中且填满
        final Bitmap toReuse = pool.get(width, height, toTransform.getConfig() != null
                ? toTransform.getConfig() : Bitmap.Config.ARGB_8888);
        Bitmap transformed = TransformationUtils.centerCrop(toReuse, toTransform, width, height);
        if (toReuse != null && toReuse != transformed && !pool.put(toReuse)) {
            toReuse.recycle();
        }

        // 根据算出的宽高新建Bitmap对象并设置到画布上
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        // 设置形状的大小与图片的大小一致
        shape.setBounds(0, 0, width, height);
        // 将图片画到画布上
        shape.draw(canvas);
        // 将裁剪后的图片画得画布上
        canvas.drawBitmap(transformed, 0, 0, mPaint);

        return bitmap;
    }

    @Override
    public String getId() {
        // 用于缓存的唯一标识符
        return "CustomTransformation" + mShapeRes;
    }

    public int getBitmapWidth() {
        return DPUtils.getScreenW(mContext) / 3;
    }

    public int getBitmapHeight() {
        return DPUtils.getScreenH(mContext) / 4;
    }
}