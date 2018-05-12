package com.fenazola.mxcome.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息列表适配器
 */
public class MsgRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int MSG_LEFT_TEXT = 0;
    public final int MSG_RIGHT_TEXT = 1;
    public final int MSG_LEFT_IMAGE = 2;
    public final int MSG_RIGHT_IMAGE = 3;
    public final int MSG_LEFT_VOICE = 4;
    public final int MSG_RIGHT_VOICE = 5;

    private Context context;

    private List<TableChat> data = new ArrayList<>();

    private int mMinItemWith;
    private int mMaxItemWith;

    public OnItemClickListener listener;

    private String destPhotoUrl;
    private String srcPhotoUrl;

    private int voicePlayPosition = -1;

    public MsgRecyclerAdapter(Context context, TableTalk talk) {
        this.context = context;
        destPhotoUrl = talk.getIcon();
        UserEntry user = Utils.getUserEntry();
        if(user != null){
            srcPhotoUrl = user.getPic();
        }
        mMaxItemWith = (int) (DPUtils.getScreenW(context) * 0.4f);
        mMinItemWith = (int) (DPUtils.getScreenW(context) * 0.15f);
    }

    public class MyUserIconClickListener implements View.OnClickListener {
        private int position;

        public MyUserIconClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                listener.onClickPhoto(v, data.get(position), position);
            }
        }
    }

    public class MySendFailClickListener implements View.OnClickListener {
        private int position;

        public MySendFailClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                TableChat chat = data.get(position);
                chat.setSendStatus(0);
                notifyItemChanged(position);
                listener.onClickReSend(v, chat, position);
            }
        }
    }

    public class MyImageClickListener implements View.OnClickListener {
        private int position;

        public MyImageClickListener(int position){
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){
                listener.onClickContent(v, data.get(position), position);
            }
        }
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
                        notifyDataSetChanged();
                    }
                }
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

    public void insertMsg(TableChat item) {
        data.add(0, item);
        notifyItemChanged(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case MSG_LEFT_TEXT:
                holder = new MsgTextLeftHolder(new MsgTextLeft(context));
                break;
            case MSG_RIGHT_TEXT:
                holder = new MsgTextRightHolder(new MsgTextRight(context));
                break;
            case MSG_LEFT_IMAGE:
                holder = new MsgImageLeftHolder(new MsgImageLeft(context));
                break;
            case MSG_RIGHT_IMAGE:
                holder = new MsgImageRightHolder(new MsgImageRight(context));
                break;
            case MSG_LEFT_VOICE:
                holder = new MsgVoiceLeftHolder(new MsgVoiceLeft(context));
                break;
            case MSG_RIGHT_VOICE:
                holder = new MsgVoiceRightHolder(new MsgVoiceRight(context));
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
                msgText.setUserIcon(destPhotoUrl);
                msgText.setContent(data.get(position).getMsgContent());
                msgText.getUserIcon().setOnClickListener(new MyUserIconClickListener(position));
            }
            break;
            case MSG_RIGHT_TEXT: {
                MsgTextRight msgText = (MsgTextRight) holder.itemView;
                String before = null;
                String time = chat.getMsgTime();
                if (position > 0) {
                    before = data.get(position - 1).getMsgTime();
                }
                msgText.setChatTime(time, before);
                msgText.setUserIcon(srcPhotoUrl);
                msgText.setContent(data.get(position).getMsgContent());
                msgText.getUserIcon().setOnClickListener(new MyUserIconClickListener(position));
                if(chat.getSendStatus() == 0){
                    msgText.getSendStatusImage().setVisibility(View.VISIBLE);
                    msgText.getSendStatusImage().setBackgroundResource(R.drawable.msg_sending);
                    msgText.getSendStatusImage().setOnClickListener(null);
                    Drawable drawable =  msgText.getSendStatusImage().getBackground();
                    if(drawable!=null){
                        Animatable animatable = (Animatable) drawable;
                        animatable.start();
                    }
                } else if (chat.getSendStatus() == 1){
                    msgText.getSendStatusImage().setVisibility(View.GONE);
                    msgText.getSendStatusImage().setBackgroundResource(0);
                } else{
                    msgText.getSendStatusImage().setVisibility(View.VISIBLE);
                    msgText.getSendStatusImage().setBackgroundResource(R.mipmap.msg_send_fail);
                    msgText.getSendStatusImage().setOnClickListener(new MySendFailClickListener(position));
                }
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
                msgText.setUserIcon(destPhotoUrl);
                msgText.setMsgImage(data.get(position).getMsgUrl(), R.mipmap.msg_chat_left);
                msgText.getUserIcon().setOnClickListener(new MyUserIconClickListener(position));
                msgText.getMsgImage().setOnClickListener(new MyImageClickListener(position));
            }
            break;
            case MSG_RIGHT_IMAGE: {
                MsgImageRight msgText = (MsgImageRight) holder.itemView;
                String before = null;
                String time = chat.getMsgTime();
                if (position > 0) {
                    before = data.get(position - 1).getMsgTime();
                }
                msgText.setChatTime(time, before);
                msgText.setUserIcon(srcPhotoUrl);
                String msgUrl = data.get(position).getMsgUrl();
                if(msgUrl != null){
                    msgText.setMsgImage(msgUrl, R.mipmap.msg_chat_right);
                }else{
                    msgText.setMsgLocalImage(data.get(position).getLocalUrl(), R.mipmap.msg_chat_right);
                }
                msgText.getUserIcon().setOnClickListener(new MyUserIconClickListener(position));
                msgText.getMsgImage().setOnClickListener(new MyImageClickListener(position));
                if(chat.getSendStatus() == 0){
                    msgText.getSendStatusImage().setVisibility(View.VISIBLE);
                    msgText.getSendStatusImage().setBackgroundResource(R.drawable.msg_sending);
                    msgText.getSendStatusImage().setOnClickListener(null);
                    Drawable drawable =  msgText.getSendStatusImage().getBackground();
                    if(drawable!=null){
                        Animatable animatable = (Animatable) drawable;
                        animatable.start();
                    }
                } else if (chat.getSendStatus() == 1){
                    msgText.getSendStatusImage().setVisibility(View.GONE);
                    msgText.getSendStatusImage().setBackgroundResource(0);
                } else{
                    msgText.getSendStatusImage().setVisibility(View.VISIBLE);
                    msgText.getSendStatusImage().setBackgroundResource(R.mipmap.msg_send_fail);
                    msgText.getSendStatusImage().setOnClickListener(new MySendFailClickListener(position));
                }
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
                msgText.setUserIcon(destPhotoUrl);
                msgText.setVoiceTime(chat.getVoiceTime(), mMinItemWith, mMaxItemWith);
                msgText.getUserIcon().setOnClickListener(new MyUserIconClickListener(position));
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
            case MSG_RIGHT_VOICE: {
                MsgVoiceRight msgText = (MsgVoiceRight) holder.itemView;
                String before = null;
                String time = chat.getMsgTime();
                if (position > 0) {
                    before = data.get(position - 1).getMsgTime();
                }
                msgText.setChatTime(time, before);
                msgText.setUserIcon(srcPhotoUrl);
                msgText.setVoiceTime(chat.getVoiceTime(), mMinItemWith, mMaxItemWith);
                msgText.getUserIcon().setOnClickListener(new MyUserIconClickListener(position));
                View view = msgText.getVoiceAnim();
                if(voicePlayPosition == position){
                    view.setBackgroundResource(R.drawable.voice_play_sender);
                    AnimationDrawable drawable = (AnimationDrawable) view
                            .getBackground();
                    drawable.start();
                } else {
                    view.setBackgroundResource(R.mipmap.chat_voice_sender3);
                }
                msgText.getVoice().setOnClickListener(new MyVoiceClickListener(position, MSG_RIGHT_VOICE));
                if(chat.getSendStatus() == 0){
                    msgText.getSendStatusImage().setVisibility(View.VISIBLE);
                    msgText.getSendStatusImage().setBackgroundResource(R.drawable.msg_sending);
                    msgText.getSendStatusImage().setOnClickListener(null);
                    Drawable drawable =  msgText.getSendStatusImage().getBackground();
                    if(drawable!=null){
                        Animatable animatable = (Animatable) drawable;
                        animatable.start();
                    }
                } else if (chat.getSendStatus() == 1){
                    msgText.getSendStatusImage().setVisibility(View.GONE);
                    msgText.getSendStatusImage().setBackgroundResource(0);
                } else{
                    msgText.getSendStatusImage().setVisibility(View.VISIBLE);
                    msgText.getSendStatusImage().setBackgroundResource(R.mipmap.msg_send_fail);
                    msgText.getSendStatusImage().setOnClickListener(new MySendFailClickListener(position));
                }
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
            int type = chat.getUserType(); //文字
            if (type == 0) {
                return MSG_LEFT_TEXT;
            } else {
                return MSG_RIGHT_TEXT;
            }
        } else if (msgType == 1) {
            int type = chat.getUserType(); //图片
            if (type == 0) {
                return MSG_LEFT_IMAGE;
            } else {
                return MSG_RIGHT_IMAGE;
            }
        } else {
            int type = chat.getUserType(); //语音
            if (type == 0) {
                return MSG_LEFT_VOICE;
            } else {
                return MSG_RIGHT_VOICE;
            }
        }
    }


    class MsgTextLeftHolder extends RecyclerView.ViewHolder {
        public MsgTextLeftHolder(MsgTextLeft view) {
            super(view);
        }
    }

    class MsgTextRightHolder extends RecyclerView.ViewHolder {
        public MsgTextRightHolder(MsgTextRight view) {
            super(view);
        }
    }

    class MsgImageLeftHolder extends RecyclerView.ViewHolder {
        public MsgImageLeftHolder(MsgImageLeft view) {
            super(view);
        }
    }

    class MsgImageRightHolder extends RecyclerView.ViewHolder {
        public MsgImageRightHolder(MsgImageRight view) {
            super(view);
        }
    }

    class MsgVoiceLeftHolder extends RecyclerView.ViewHolder {
        public MsgVoiceLeftHolder(MsgVoiceLeft view) {
            super(view);
        }
    }

    class MsgVoiceRightHolder extends RecyclerView.ViewHolder {
        public MsgVoiceRightHolder(MsgVoiceRight view) {
            super(view);
        }
    }

    public interface OnItemClickListener{
        void onClickReSend(View view, TableChat msg, int position);
        void onClickPhoto(View view, TableChat msg, int position);
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
        this.voicePlayPosition = voicePlayPosition;
    }

    public List<TableChat> getData() {
        return data;
    }
}
