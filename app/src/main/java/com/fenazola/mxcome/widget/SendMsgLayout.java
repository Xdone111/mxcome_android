package com.fenazola.mxcome.widget;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.utils.LogUtils;

import java.util.Formatter;
import java.util.Locale;


/**
 * 公用发送消息布局
 *
 * @author zm
 */
public class SendMsgLayout extends LinearLayout implements GestureDetector.OnGestureListener {

    private View text_layout; //文字布局
    private TextView text1, text2, text3, text4, text5, text6;
    private ImageView msg_chat_voice; //切换语音
    private EditText msg_et; //文字
    private TextView msg_voice; //语音
    private ImageView msg_chat_ex; //文字消息发送
    private ImageView msg_chat_add; //图片消息发送
    private ImageView msg_send_text; //发送按钮
    private OnMsgSendListener listener;
    private boolean isShow = false;
    private boolean isText = false;
    private GestureDetector detector;
    private float y1 = 0;
    private float y2 = 0;
    protected static final float FLIP_DISTANCE = 50;

    public SendMsgLayout(Context context) {
        super(context);
        initView(context);
    }

    public SendMsgLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.layout_send_msg, this);
        text_layout = findViewById(R.id.text_layout);
        text1 = (TextView)findViewById(R.id.text1);
        text2 = (TextView)findViewById(R.id.text2);
        text3 = (TextView)findViewById(R.id.text3);
        text4 = (TextView)findViewById(R.id.text4);
        text5 = (TextView)findViewById(R.id.text5);
        text6 = (TextView)findViewById(R.id.text6);
        msg_chat_voice = (ImageView)findViewById(R.id.msg_chat_voice);
        msg_et = (EditText) findViewById(R.id.msg_et);
        msg_voice = (TextView) findViewById(R.id.msg_voice);
        msg_chat_ex = (ImageView) findViewById(R.id.msg_chat_ex);
        msg_chat_add = (ImageView) findViewById(R.id.msg_chat_add);
        msg_send_text = (ImageView) findViewById(R.id.msg_send_text);
        text1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickImage(true);
                }
            }
        });
        text2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickImage(false);
                }
            }
        });
        text3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        text4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        text5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        text6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        msg_chat_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    text_layout.setVisibility(GONE);
                } else {
                    text_layout.setVisibility(VISIBLE);
                }
                isShow = !isShow;
            }
        });
        msg_chat_voice.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isText){
                    msg_chat_voice.setImageResource(R.mipmap.msg_chat_text);
                    msg_et.setVisibility(GONE);
                    msg_voice.setVisibility(VISIBLE);
                }else{
                    msg_chat_voice.setImageResource(R.mipmap.msg_chat_voice);
                    msg_et.setVisibility(VISIBLE);
                    msg_voice.setVisibility(GONE);
                }
                isText = !isText;
            }
        });
        msg_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    msg_send_text.setVisibility(VISIBLE);
                    msg_chat_add.setVisibility(GONE);
                }else{
                    msg_send_text.setVisibility(GONE);
                    msg_chat_add.setVisibility(VISIBLE);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        msg_voice.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = detector.onTouchEvent(event);
                if (!ret) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        msg_voice.setText("松开 结束");
                        msg_voice.setTextColor(getResources().getColor(R.color.colorBlue));
                        msg_voice.setBackgroundResource(R.drawable.corner_msg_voice_sel);
                        y2 = event.getY();
                        if (y1 - y2 > FLIP_DISTANCE) {
                            countDownTimer.cancel();
                            if (listener != null) {
                                listener.onVoiceCancel(true);
                            }
                            msg_voice.setText("按住 说话");
                            msg_voice.setTextColor(getResources().getColor(R.color.colorGrey));
                            msg_voice.setBackgroundResource(R.drawable.corner_msg_voice_def);
                            voiceTime = 0;
                        } else {
                            if (voiceTime * 1000 < maxTime) {
                                countDownTimer.cancel();
                                if(listener != null) {
                                    listener.onVoiceSend(voiceTime);
                                }
                                voiceTime = 0;
                            }else{
                                voiceTime = 0;
                            }
                            msg_voice.setText("按住 说话");
                            msg_voice.setTextColor(getResources().getColor(R.color.colorGrey));
                            msg_voice.setBackgroundResource(R.drawable.corner_msg_voice_def);
                        }
                    }
                }
                return true;
            }
        });
        msg_send_text.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTextSend(msg_et.getText().toString());
                    msg_et.setText("");
                }
            }
        });
        msg_et.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    msg_et.setBackgroundResource(R.drawable.shape_msg_send_ed_fouce_bg);
                }else{
                    msg_et.setBackgroundResource(R.drawable.shape_msg_send_ed_bg);

                }
            }
        });
    }

    public void setOnMsgSendListener(OnMsgSendListener listener) {
        this.listener = listener;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        detector = new GestureDetector(getContext(), this);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        y1 = e.getY();
        msg_voice.setText("松开 结束");
        msg_voice.setTextColor(getResources().getColor(R.color.colorBlue));
        msg_voice.setBackgroundResource(R.drawable.corner_msg_voice_sel);
        if (listener != null) {
            countDownTimer.start();
            listener.onVoiceStart();
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        msg_voice.setText("按住 说话");
        msg_voice.setTextColor(getResources().getColor(R.color.colorGrey));
        msg_voice.setBackgroundResource(R.drawable.corner_msg_voice_def);
        if (listener != null) {
            countDownTimer.cancel();
            listener.onVoiceCancel(false);
            voiceTime = 0;
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public interface OnMsgSendListener {
        public void onTextSend(String text);

        public void onClickImage(boolean isCamera);

        public void onVoiceStart();

        public void onVoiceing(int voiceTime);

        public void onVoiceCancel(boolean isUpFling);

        public void onVoiceSend(int voiceTime);
    }

    private int millis = 1000;
    private int maxTime = 10000;
    private int voiceTime = 0;

    private CountDownTimer countDownTimer = new CountDownTimer(maxTime, millis) {
        @Override
        public void onTick(long millisUntilFinished) {
            voiceTime++;
            LogUtils.i("test", "-----------------voiceTime = " + voiceTime);
            if (listener != null) {
                listener.onVoiceing(voiceTime);
            }
        }

        @Override
        public void onFinish() {
            voiceTime++;
            LogUtils.i("test", "-----------------onFinish = " + voiceTime);
            if (listener != null) {
                listener.onVoiceing(voiceTime);
                listener.onVoiceSend(voiceTime);
            }
        }
    };
}