package com.fenazola.mxcome.widget;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.animator.Techniques;
import com.zss.library.animator.YoYo;


public class ProjectStep3 extends LinearLayout {

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private TextView pause;
    private boolean auto = true;

    public ProjectStep3(Context context) {
        super(context);
        initView(context);
    }

    public ProjectStep3(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.layout_project_step3, this);
        image1 = (ImageView)findViewById(R.id.image1);
        image2 = (ImageView)findViewById(R.id.image2);
        image3 = (ImageView)findViewById(R.id.image3);
        pause = (TextView)findViewById(R.id.pause);
    }

    public void startAnim(){
        YoYo.with(Techniques.Pulse).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                YoYo.with(Techniques.Pulse).onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        if(auto){
                            startAnim();
                        }

                    }
                }).playOn(image2);
            }
        }).playOn(image3);
    }


    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus){
            startAnim();
        }else {
            auto = false;
        }
    }

    public void setPauseText(String text, OnClickListener listener){
        pause.setText(text);
        pause.setOnClickListener(listener);
    }
}