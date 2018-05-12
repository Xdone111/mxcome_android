package com.fenazola.mxcome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.project.Mdec2Entry;
import com.fenazola.mxcome.entry.project.PicsEntry;
import com.fenazola.mxcome.utils.Constant;

import java.util.List;


public class ProjectStep1 extends LinearLayout {

    private TextView tv_title;
    private TextView tv_status;
    private TextView tv_progress;

    public ProjectStep1(Context context) {
        super(context);
        initView(context);
    }

    public ProjectStep1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.layout_project_step1, this);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_status = (TextView)findViewById(R.id.tv_status);
        tv_progress = (TextView)findViewById(R.id.tv_progress);
    }

    public void setMdec2Entry(Mdec2Entry entry){
        tv_title.setText(entry.getStage());
        tv_status.setText(entry.getStateName());
        tv_progress.setText(entry.getBfb()+"%");
    }
}