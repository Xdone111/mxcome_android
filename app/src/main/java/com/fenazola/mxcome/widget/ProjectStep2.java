package com.fenazola.mxcome.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.project.Mdec2Entry;
import com.fenazola.mxcome.entry.project.PicsEntry;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.utils.DPUtils;

import java.util.List;


public class ProjectStep2 extends LinearLayout {

    private TextView build_title;
    private SeekBar seekBar;
    private TextView moveText;
    private TextView step1;
    private TextView step2;
    private TextView step_desc1;
    private TextView step_desc2;
    private TextView time;
    private TextView total_price;
    private TextView work_time;
    private TextView total_step;
    private ImageView build_before;
    private ImageView building;
    private ImageView build_after;
    private ImageView tel_left;
    private EditText et_tel;
    private ImageView tel_right;
    private ImageView voice_left;
    private EditText et_voice;
    private ImageView voice_right;
    private int rate = 0;
    private double fDensity;



    public ProjectStep2(Context context) {
        super(context);
        initView(context);
    }

    public ProjectStep2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.layout_project_step2, this);
        build_title = (TextView)findViewById(R.id.build_title);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        moveText = (TextView)findViewById(R.id.moveText);
        step1 = (TextView)findViewById(R.id.step1);
        step2 = (TextView)findViewById(R.id.step2);
        step_desc1 = (TextView)findViewById(R.id.step_desc1);
        step_desc2 = (TextView)findViewById(R.id.step_desc2);
        time = (TextView)findViewById(R.id.time);
        total_price = (TextView)findViewById(R.id.total_price);
        work_time = (TextView)findViewById(R.id.work_time);
        total_step = (TextView)findViewById(R.id.total_step);
        build_before = (ImageView) findViewById(R.id.build_before);
        building = (ImageView)findViewById(R.id.building);
        build_after = (ImageView)findViewById(R.id.build_after);
        tel_left = (ImageView)findViewById(R.id.tel_left);
        et_tel = (EditText)findViewById(R.id.et_tel);
        tel_right = (ImageView)findViewById(R.id.tel_right);
        voice_left = (ImageView)findViewById(R.id.voice_left);
        et_voice = (EditText)findViewById(R.id.et_voice);
        voice_right = (ImageView)findViewById(R.id.voice_right);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImpl());
        int px = DPUtils.dip2px(getContext(), 270);
        fDensity = px / 100;
    }

    private class OnSeekBarChangeListenerImpl implements
            SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            rate = progress;
            LayoutParams paramsStrength = new LayoutParams(
                    LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            if(rate > 90){
                paramsStrength.leftMargin = (int) (90 * fDensity);
            }else{
                paramsStrength.leftMargin = (int) (rate * fDensity);
            }
            moveText.setLayoutParams(paramsStrength);
            moveText.setText(rate+"%");
        }

        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }


    public void setMdec2Entry(Mdec2Entry entry){
        build_title.setText(entry.getStage());
        step1.setText(getContext().getString(R.string.project_step, entry.getAstepNo()));
        step2.setText(getContext().getString(R.string.project_step, entry.getBstepNo()));
        step_desc1.setText(entry.getAstepName());
        step_desc2.setText(entry.getBstepName());
        time.setText(entry.getSg_gs());
//        total_price.setText(entry.getSg_gj());
        work_time.setText(entry.getSg_gj());
        total_step.setText(entry.getBstepNo());
        List<PicsEntry> pics = entry.getPics();
        if (pics != null && pics.size() > 0){
            for (PicsEntry item : pics){
                if(item.getTag() == 1){
                    Glide.with(getContext()).load(Constant.imageUrl + item.getPath()).placeholder(R.mipmap.msg_default_icon).into(build_before);
                }else if(item.getTag() == 2){
                    Glide.with(getContext()).load(Constant.imageUrl + item.getPath()).placeholder(R.mipmap.msg_default_icon).into(build_before);
                }else{
                    Glide.with(getContext()).load(Constant.imageUrl + item.getPath()).placeholder(R.mipmap.msg_default_icon).into(build_before);
                }
            }
        }
    }

}