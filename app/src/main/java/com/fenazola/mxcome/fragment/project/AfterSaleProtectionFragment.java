package com.fenazola.mxcome.fragment.project;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.animator.Techniques;
import com.zss.library.animator.YoYo;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by xuhuixiang on 2017/7/28.
 * 售后保障
 */

public class AfterSaleProtectionFragment extends BaseFragment implements View.OnClickListener {
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private boolean auto = true;
    private TextView tv1, tv2, tv3;
    private int index = 1;
    private TextView time;

    @Override
    public int getLayoutResId() {
        return R.layout.after_sale_protection_fragment;
    }

    @Override
    public void initView() {
        super.initView();
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        time = (TextView) findViewById(R.id.time);


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        startAnim1();
        time.setText(Html.fromHtml("终身质保<font color='#ff6666'>" + 2 + "</font> 年免费"));
    }

    public void startAnim1() {
        YoYo.with(Techniques.Pulse).onStart(new YoYo.AnimatorCallback() {
            @Override
            public void call(Animator animator) {
                YoYo.with(Techniques.Pulse).onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        if (auto) {
                            startAnim1();
                        }
                    }
                }).playOn(image2);
            }
        }).playOn(image3);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("售后保障");
        toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
        toolbar.setTitleColor(Color.WHITE);
        //toolbar.setLeftImage(getResources().getDrawable(R.mipmap.project_left));
        toolbar.setRightImage(R.mipmap.w_share);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                initChange(1);
                break;
            case R.id.tv2:
                initChange(2);
                break;
            case R.id.tv3:
                initChange(3);
                break;
        }
    }

    private void initChange(int i) {
        index = i;
        tv1.setBackgroundResource(R.drawable.corner_after_def);
        tv2.setBackgroundResource(R.drawable.corner_after_def);
        tv3.setBackgroundResource(R.drawable.corner_after_def);
        tv1.setTextColor(getColor(R.color.colorGrey));
        tv2.setTextColor(getColor(R.color.colorGrey));
        tv3.setTextColor(getColor(R.color.colorGrey));

        if (i == 1) {
            tv1.setBackgroundResource(R.drawable.corner_after_sel);
            tv1.setTextColor(getColor(R.color.colorBlue));
        } else if (i == 2) {
            tv2.setBackgroundResource(R.drawable.corner_after_sel);
            tv2.setTextColor(getColor(R.color.colorBlue));
        } else {
            tv3.setBackgroundResource(R.drawable.corner_after_sel);
            tv3.setTextColor(getColor(R.color.colorBlue));
        }
    }
}
