package com.fenazola.mxcome.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.utils.DPUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;


public class BubbleImageView extends ImageView {
	private Context context;
	private int resBg;

	public BubbleImageView(Context context) {
		super(context);
		this.context = context;
	}

	public BubbleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public BubbleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public void loadUrl(String url, int res, int placeHolderPic) {
		this.setImageResource(placeHolderPic);
		this.resBg = res;
		Glide.with(context).load(Constant.imageUrl + url).asBitmap().into(new SimpleTarget<Bitmap>() {
			@Override
			public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
				if (bitmap != null) {
					Bitmap bitmap_bg = BitmapFactory.decodeResource(getResources(), resBg);
					Bitmap bp = getRoundCornerImage(bitmap_bg, bitmap);
					setImageBitmap(bp);
				}
			}
		});
	}

	public void loadLocalUrl(String url, int res, int placeHolderPic) {
		this.setImageResource(placeHolderPic);
		this.resBg = res;
		Glide.with(context).load(new File(url)).asBitmap().into(new SimpleTarget<Bitmap>() {
			@Override
			public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
				if (bitmap != null) {
					Bitmap bitmap_bg = BitmapFactory.decodeResource(getResources(), resBg);
					Bitmap bp = getRoundCornerImage(bitmap_bg, bitmap);
					setImageBitmap(bp);
				}
			}
		});
	}

	public Bitmap getRoundCornerImage(Bitmap bitmap_bg, Bitmap bitmap_in) {
		int width = bitmap_in.getWidth();
		int height = bitmap_in.getHeight();
		if(height != 0){
			double scale = (width * 1.00) / height;
			if (width >= height) {
				width = getBitmapWidth();
				height = (int) (width / scale);
			} else {
				height = getBitmapHeight();
				width = (int) (height * scale);
			}
		} else {
			width = 100;
			height = 100;
		}
		Bitmap roundConcerImage = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		Canvas canvas = new Canvas(roundConcerImage);
		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, width, height);
		Rect rectF = new Rect(0, 0, bitmap_in.getWidth(), bitmap_in.getHeight());
		paint.setAntiAlias(true);
		NinePatch patch = new NinePatch(bitmap_bg,
				bitmap_bg.getNinePatchChunk(), null);
		patch.draw(canvas, rect);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap_in, rectF, rect, paint);
		return roundConcerImage;
	}

	public int getBitmapWidth() {
		return DPUtils.getScreenW(context) / 3;
	}

	public int getBitmapHeight() {
		return DPUtils.getScreenH(context) / 4;
	}
}
