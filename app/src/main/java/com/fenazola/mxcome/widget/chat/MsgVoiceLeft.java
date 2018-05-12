package com.fenazola.mxcome.widget.chat;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DateUtils;
import com.fenazola.mxcome.widget.ListDialog;
import com.fenazola.mxcome.widget.clipView.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2017/3/25.
 */

public class MsgVoiceLeft extends RelativeLayout {

    private TextView chat_time;
    private CircleImageView user_icon;
    private LinearLayout voice_group;
    private FrameLayout voice_bg;
    private ImageView recorder_anim;
    private TextView voice_time;
    private ImageView unread;

    public MsgVoiceLeft(Context context) {
        super(context);
        initView();
    }

    public MsgVoiceLeft(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MsgVoiceLeft(Context context, AttributeSet attrs, int defStyleAtrr) {
        super(context, attrs, defStyleAtrr);
        initView();
    }

    public void initView() {
        inflate(getContext(), R.layout.listitem_msg_voice_left, this);
        chat_time = (TextView)findViewById(R.id.chat_time);
        user_icon = (CircleImageView)findViewById(R.id.user_icon);
        voice_group = (LinearLayout)findViewById(R.id.voice_group);
        voice_bg = (FrameLayout)findViewById(R.id.voice_bg);
        recorder_anim = (ImageView)findViewById(R.id.recorder_anim);
        voice_time = (TextView)findViewById(R.id.voice_time);
        unread = (ImageView)findViewById(R.id.unread);
        voice_bg.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO: 2017/8/10
                List<String> names=new ArrayList<String>();
                names.add("听筒播放");
                names.add("扬声器播放");
                names.add("删除");
                names.add("多选删除");
                ListDialog dialog1 = new ListDialog( (Activity) getContext(), names);
                dialog1.show();
                return false;
            }
        });
    }

    public void setChatTime(String time, String before){
        if(before != null){
            String showTime = DateUtils.getTime(time, before);
            if(showTime != null){
                chat_time.setVisibility(VISIBLE);
                chat_time.setText(showTime);
            }else{
                chat_time.setVisibility(GONE);
            }
        }else{
            String showTime = DateUtils.getTime(time, null);
            chat_time.setVisibility(View.VISIBLE);
            chat_time.setText(showTime);
        }
    }

    public void setUserIcon(String photoUrl){
        Glide.with(getContext()).load(Constant.imageUrl + photoUrl).placeholder(R.mipmap.msg_default_icon).into(user_icon);
    }

    public void setVoiceTime(int voiceTime, int mMinItemWith, int mMaxItemWith){
        ViewGroup.LayoutParams lParams = voice_bg.getLayoutParams();
        lParams.width = (int) (mMinItemWith + mMaxItemWith / 60f
                * voiceTime);
        voice_time.setText(voiceTime+"\"");
        voice_bg.setLayoutParams(lParams);
    }

    public void setUnread(boolean show){
        if (show){
            unread.setVisibility(VISIBLE);
        }else {
            unread.setVisibility(GONE);
        }
    }

    public ImageView getUserIcon(){
        return user_icon;
    }

    public View getVoice(){
        return voice_bg;
    }

    public ImageView getVoiceAnim(){
        return recorder_anim;
    }

    public void setContentRes(int resid){
        voice_bg.setBackgroundResource(resid);
    }
}
