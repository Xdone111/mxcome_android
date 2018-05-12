package com.fenazola.mxcome.fragment.main.act;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.main.example.ExampleFragment;
import com.fenazola.mxcome.fragment.sercentre.DemandRoleExperimentation;
import com.fenazola.mxcome.fragment.sercentre.FaqFragment;
import com.fenazola.mxcome.fragment.sercentre.FaqTermsFragment;
import com.fenazola.mxcome.fragment.sercentre.ManualFragment;
import com.fenazola.mxcome.fragment.sercentre.WeChatServerFragment;
import com.fenazola.mxcome.widget.AutoTextView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.animator.Techniques;
import com.zss.library.animator.YoYo;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务中心
 * @author xhx
 */
public class BelieveSpeakFragment extends BaseFragment implements View.OnClickListener{
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private TextView tv1,tv2;

    private ImageView bg;
    int width;
    private Handler mHandler = new Handler();
    private int mTextCount;
    private List<String> mList;
    private AutoTextView mTextView, mTextView1;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_believe_speak;
    }

    @Override
    public void initView() {
        super.initView();
        WindowManager wm = getActivity().getWindowManager();
        width = wm.getDefaultDisplay().getWidth();

        bg=(ImageView)findViewById(R.id.show_bg);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        mTextView = (AutoTextView) findViewById(R.id.show_atv);
        mTextView1 = (AutoTextView)findViewById(R.id.show_atv1);
        tv1=(TextView)findViewById(R.id.tv_to_1);
        tv2=(TextView)findViewById(R.id.tv_to_2);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);


    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        changeItemH(bg);
        startAnim1();
        startAnim2();
        mList = new ArrayList<String>();
        //TODO: 2017/6/26
        mList.add("垂直滚动TextView测试1");
        mList.add("垂直滚动TextView测试2");
        mList.add("垂直滚动TextView测试3");
        mList.add("垂直滚动TextView测试4");
        mList.add("滚动TextView测试1");
        mList.add("滚动TextView测试2");
        mList.add("滚动TextView测试3");
        mList.add("滚动TextView测试4");
        mTextView.setText(mList.get(0));
        mTextView1.setText(mList.get(0));
        mTextCount = mList.size();
        mHandler.postDelayed(runnable, 2000);

    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mTextView.next();
            mTextCount++;
            if (mTextCount >= Integer.MAX_VALUE) {
                mTextCount = mList.size();
            }
            mTextView.setText(mList.get(mTextCount % (mList.size())));
            mTextView1.setText(mList.get(mTextCount % (mList.size())));
            if (mList.size() > 1) {
                mHandler.postDelayed(this, 2000);
            }

        }
    };
    public void startAnim1() {
        YoYo.with(Techniques.Pulse).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                YoYo.with(Techniques.Pulse).onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        if (true) {
                            startAnim1();
                        }
                    }
                }).playOn(image1);
            }
        }).playOn(image2);
    }
    public void startAnim2() {
        YoYo.with(Techniques.Pulse).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                YoYo.with(Techniques.Pulse).onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        if (true) {
                            startAnim2();
                        }
                    }
                }).playOn(image3);
            }
        }).playOn(image4);
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_to_1:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, SpeakListFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.tv_to_2:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, AddSpeakFragment.class.getName());
                startActivity(intent);
                break;

        }
    }
    private void changeItemH(ImageView iv){
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) iv.getLayoutParams();
        linearParams.height = (int)((float)width*0.53);
        iv.setLayoutParams(linearParams);
    }


    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.VISIBLE);
        ImageView imgView = new ImageView(getActivity());
        imgView.setImageResource(R.mipmap.believe_title);
        toolbar.setMiddleView(imgView);
        toolbar.setBgColor(Color.TRANSPARENT);
        toolbar.setTitleColor(Color.WHITE);
        toolbar.setRightImage(R.mipmap.share_white);
        toolbar.setLeftImage(getResources().getDrawable(R.mipmap.project_left));
    }
}
