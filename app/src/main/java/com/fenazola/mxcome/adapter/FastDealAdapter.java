package com.fenazola.mxcome.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.TableChat;
import com.fenazola.mxcome.db.TableTalk;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.chat.MsgImageLeft;
import com.fenazola.mxcome.widget.chat.MsgImageRight;
import com.fenazola.mxcome.widget.chat.MsgTextLeft;
import com.fenazola.mxcome.widget.chat.MsgTextRight;
import com.fenazola.mxcome.widget.chat.MsgVoiceLeft;
import com.fenazola.mxcome.widget.chat.MsgVoiceRight;
import com.zss.library.utils.DPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表适配器
 */
public class FastDealAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int MSG_LEFT_TEXT = 0;
    public final int MSG_LEFT_IMAGE = 1;
    public final int MSG_LEFT_VOICE = 2;

    private Context context;

    private List<TableChat> data = new ArrayList<>();

    private int mMinItemWith;
    private int mMaxItemWith;

    public OnItemClickListener listener;

    private int voicePlayPosition = -1;

    public FastDealAdapter(Context context) {
        this.context = context;
        mMaxItemWith = (int) (DPUtils.getScreenW(context) * 0.4f);
        mMinItemWith = (int) (DPUtils.getScreenW(context) * 0.15f);
    }

    public class MyVoiceClickListener implements View.OnClickListener {
        private int position;
        private int voiceType;

        public MyVoiceClickListener(int position, int voiceType){
            this.position = position;
            this.voiceType = voiceType;
        }

        @Override
        public void onClick(View v) {
            TableChat chat = data.get(position);
            if(listener!=null){
                listener.onClickContent(v, chat, position);
                if(voiceType == MSG_LEFT_VOICE){
                    if(chat.getUnread() == 0){
                        chat.setUnread(1);
                        listener.updateReadStatus(v, chat, position);
                    }
                }
                setVoicePlayPosition(position);
            }
        }
    }

    public void addAll(List<TableChat> elem) {
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public void replaceAll(List<TableChat> elem) {
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case MSG_LEFT_TEXT:
                holder = new MsgTextLeftHolder(new MsgTextLeft(context));
                break;
            case MSG_LEFT_IMAGE:
                holder = new MsgImageLeftHolder(new MsgImageLeft(context));
                break;
            case MSG_LEFT_VOICE:
                holder = new MsgVoiceLeftHolder(new MsgVoiceLeft(context));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TableChat chat = data.get(position);
        int msgType = getItemViewType(position);
        switch (msgType) {
            case MSG_LEFT_TEXT: {
                MsgTextLeft msgText = (MsgTextLeft) holder.itemView;
                String before = null;
                String time = chat.getMsgTime();
                if (position > 0) {
                    before = data.get(position - 1).getMsgTime();
                }
                msgText.setChatTime(time, before);
                msgText.getUserIcon().setVisibility(View.GONE);
                msgText.setContent(data.get(position).getMsgContent());
                msgText.setContentRes(R.mipmap.fast_deal_message);
            }
            break;
            case MSG_LEFT_IMAGE: {
                MsgImageLeft msgText = (MsgImageLeft) holder.itemView;
                String before = null;
                String time = chat.getMsgTime();
                if (position > 0) {
                    before = data.get(position - 1).getMsgTime();
                }
                msgText.setChatTime(time, before);
                msgText.getUserIcon().setVisibility(View.GONE);
                msgText.setMsgImage(data.get(position).getMsgUrl(), R.mipmap.fast_deal_message);
            }
            break;
            case MSG_LEFT_VOICE: {
                MsgVoiceLeft msgText = (MsgVoiceLeft) holder.itemView;
                String before = null;
                String time = chat.getMsgTime();
                if (position > 0) {
                    before = data.get(position - 1).getMsgTime();
                }
                msgText.setChatTime(time, before);
                msgText.getUserIcon().setVisibility(View.GONE);
                msgText.setContentRes(R.mipmap.fast_deal_message);
                msgText.setVoiceTime(chat.getVoiceTime(), mMinItemWith, mMaxItemWith);
                View view = msgText.getVoiceAnim();
                if(voicePlayPosition == position){
                    view.setBackgroundResource(R.drawable.voice_play_receiver);
                    AnimationDrawable drawable = (AnimationDrawable) view.getBackground();
                    drawable.start();
                } else {
                    view.setBackgroundResource(R.mipmap.chat_voice_receiver3);
                }
                msgText.setUnread(chat.getUnreadStatus());
                msgText.getVoice().setOnClickListener(new MyVoiceClickListener(position, MSG_LEFT_VOICE));
            }
            break;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        TableChat chat = data.get(position);
        int msgType = chat.getMsgType();  //消息类型 0：文字，1：图片，2：语音
        if (msgType == 0) {
            return MSG_LEFT_TEXT;
        } else if (msgType == 1) {
            return MSG_LEFT_IMAGE;
        } else {
            return MSG_LEFT_VOICE;
        }
    }


    class MsgTextLeftHolder extends RecyclerView.ViewHolder {
        public MsgTextLeftHolder(MsgTextLeft view) {
            super(view);
        }
    }

    class MsgImageLeftHolder extends RecyclerView.ViewHolder {
        public MsgImageLeftHolder(MsgImageLeft view) {
            super(view);
        }
    }

    class MsgVoiceLeftHolder extends RecyclerView.ViewHolder {
        public MsgVoiceLeftHolder(MsgVoiceLeft view) {
            super(view);
        }
    }

    public interface OnItemClickListener{
        void onClickContent(View view, TableChat msg, int position);
        void updateReadStatus(View view, TableChat msg, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public int getVoicePlayPosition() {
        return voicePlayPosition;
    }

    public void setVoicePlayPosition(int voicePlayPosition) {
        int tempVoicePlayPosition =  this.voicePlayPosition;
        this.voicePlayPosition = voicePlayPosition;
        if(tempVoicePlayPosition == -1){
            notifyItemChanged(this.voicePlayPosition);
        }else{
            notifyItemChanged(tempVoicePlayPosition);
        }
    }

    public List<TableChat> getData() {
        return data;
    }
}
