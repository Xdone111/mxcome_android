package com.fenazola.mxcome.widget.chat;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DateUtils;
import com.fenazola.mxcome.widget.BubbleImageView;
import com.fenazola.mxcome.widget.ListDialog;
import com.fenazola.mxcome.widget.clipView.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2017/3/25.
 */

public class MsgImageLeft extends RelativeLayout {

    private TextView chat_time;
    private CircleImageView user_icon;
    private BubbleImageView msg_image;

    public MsgImageLeft(Context context) {
        super(context);
        initView();
    }

    public MsgImageLeft(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MsgImageLeft(Context context, AttributeSet attrs, int defStyleAtrr) {
        super(context, attrs, defStyleAtrr);
        initView();
    }

    public void initView() {
        inflate(getContext(), R.layout.listitem_msg_image_left, this);
        chat_time = (TextView)findViewById(R.id.chat_time);
        user_icon = (CircleImageView)findViewById(R.id.user_icon);
        msg_image = (BubbleImageView)findViewById(R.id.msg_image);
        msg_image.setOnLongClickListener(new OnLongClickListener() {
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

    public void setMsgImage(String msgUrl, int resid){
        msg_image.loadUrl(msgUrl, resid, resid);
    }

    public ImageView getUserIcon(){
        return user_icon;
    }

    public ImageView getMsgImage(){
        return msg_image;
    }

}
