package com.fenazola.mxcome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fenazola.mxcome.widget.clipView.ClipViewLayout;
import com.zss.library.activity.BaseActivity;
import com.zss.library.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 头像裁剪Activity
 */
public class ClipImageActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ClipImageActivity";
    private ClipViewLayout clipViewLayout1;
    private ClipViewLayout clipViewLayout2;
    private TextView btnCancel;
    private TextView btnOk;
    private ClipType type = ClipType.CORNER;


    public enum ClipType{
        CORNER, RECT
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_clip_image;
    }

    /**
     * 初始化组件
     */
    public void initView() {
        clipViewLayout1 = (ClipViewLayout) findViewById(R.id.clipViewLayout1);
        clipViewLayout2 = (ClipViewLayout) findViewById(R.id.clipViewLayout2);
        btnCancel = (TextView) findViewById(R.id.btn_cancel);
        btnOk = (TextView) findViewById(R.id.bt_ok);
        //设置点击事件监听器
        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        type = (ClipType)getIntent().getSerializableExtra("type");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.i(TAG, "image uri: "+getIntent().getData());
        if (type == ClipType.CORNER) {
            clipViewLayout1.setVisibility(View.VISIBLE);
            clipViewLayout2.setVisibility(View.GONE);
            //设置图片资源
            clipViewLayout1.setImageSrc(getIntent().getData());
        } else {
            clipViewLayout2.setVisibility(View.VISIBLE);
            clipViewLayout1.setVisibility(View.GONE);
            //设置图片资源
            clipViewLayout2.setImageSrc(getIntent().getData());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.bt_ok:
                generateUriAndReturn();
                break;
        }
    }

    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap;
        if (type == ClipType.CORNER) {
            zoomedCropBitmap = clipViewLayout1.clip();
        } else {
            zoomedCropBitmap = clipViewLayout2.clip();
        }
        if (zoomedCropBitmap == null) {
            LogUtils.e(TAG, "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(new File(getCacheDir(), "cropped_" + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                }
            } catch (IOException ex) {
                LogUtils.e(TAG, "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}