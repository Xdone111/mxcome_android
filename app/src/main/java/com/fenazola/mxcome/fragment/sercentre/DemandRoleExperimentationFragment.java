package com.fenazola.mxcome.fragment.sercentre;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.mxcome.R;

/**
 * 服务中心  角色体验
 */
public class DemandRoleExperimentationFragment extends DialogFragment implements View.OnClickListener {
    private ImageView backIv;
    private TextView step1Tv,step2Tv,step3Tv;
    private TextView tv1,tv2,tv3,tv4;
    private ImageView coloseIv;
    private TextView roleTitleTv,roleSubbTv;
    View view;
    private int index=1;
    private String name;
    /**以上派单——end corner_btn_role*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_role_experience, container);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);

        initView();
        initData(savedInstanceState);
        return view;
    }

    public void initView() {
        getActivity().getWindow().setBackgroundDrawable(new
                ColorDrawable(Color.TRANSPARENT));
        coloseIv=(ImageView)view.findViewById(R.id.fy_colose);
        roleTitleTv=(TextView) view.findViewById(R.id.title_role_tv);
        roleSubbTv=(TextView) view.findViewById(R.id.title_role_tv1);

        backIv=(ImageView)view.findViewById(R.id.show_back);
        step1Tv=(TextView) view.findViewById(R.id.step_1);
        step2Tv=(TextView) view.findViewById(R.id.step_2);
        step3Tv=(TextView) view.findViewById(R.id.step_3);
        tv1=(TextView) view.findViewById(R.id.tv1);
        tv2=(TextView) view.findViewById(R.id.tv2);
        tv3=(TextView) view.findViewById(R.id.tv3);
        tv4=(TextView) view.findViewById(R.id.tv4);
        backIv.setOnClickListener(this);
        step1Tv.setOnClickListener(this);
        step2Tv.setOnClickListener(this);
        step3Tv.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        coloseIv.setOnClickListener(this);


    }


    public void initData(Bundle savedInstanceState) {
        initRellStep(1);

    }

    private void initRellStep(int i) {
        if(i==1){
            step2Tv.setBackgroundResource(R.drawable.push_tv_white);
            roleTitleTv.setText(Html.fromHtml("快速体验MXCOME世界角色"));
            roleSubbTv.setVisibility(View.GONE);
            tv1.setTextColor(Color.WHITE);
            tv1.setText("我是用户");
            tv1.setBackgroundResource(R.drawable.corner_btn_role_b);

            tv2.setTextColor(Color.WHITE);
            tv2.setText("我是设计");
            tv2.setBackgroundResource(R.drawable.corner_btn_role_y);

            tv3.setTextColor(Color.WHITE);
            tv3.setText("我是工人");
            tv3.setBackgroundResource(R.drawable.corner_btn_role_b1);

            tv4.setTextColor(Color.WHITE);
            tv4.setText("我是监理");
            tv4.setBackgroundResource(R.drawable.corner_btn_role_r);
        }else  if(i==2){
            step2Tv.setBackgroundResource(R.drawable.push_tv_white_st);
            roleSubbTv.setVisibility(View.VISIBLE);
            roleTitleTv.setText(Html.fromHtml("快速体验<font color='#4BB7FD'>"+name+"</font>功能及需求"));
            roleSubbTv.setText(Html.fromHtml("点击后即将观看<font color='#4BB7FD'>30</font>秒短视频，建议在wifi环境下使用"));

            tv1.setTextColor(getResources().getColor(R.color.colorGrey));
            tv1.setText("关于整体装修及开始");
            tv1.setBackgroundResource(R.drawable.corner_btn_role);

            tv2.setTextColor(getResources().getColor(R.color.colorGrey));
            tv2.setText("关于模块装修及开始");
            tv2.setBackgroundResource(R.drawable.corner_btn_role);

            tv3.setTextColor(getResources().getColor(R.color.colorGrey));
            tv3.setText("关于发布需求及开始");
            tv3.setBackgroundResource(R.drawable.corner_btn_role);

            tv4.setTextColor(getResources().getColor(R.color.colorGrey));
            tv4.setText("关于售后及上门服务");
            tv4.setBackgroundResource(R.drawable.corner_btn_role);
        }else{

        }
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.fy_colose:
                dismiss();
                break;
            case R.id.show_back:
                if(index==2){
                    index=1;
                    initRellStep(1);
                }
                break;
            case R.id.step_1:
//                index=1;
//                initRellStep(1);
                break;
            case R.id.step_2:
                break;
            case R.id.step_3:
                break;
            case R.id.tv1:
                if(index==1){
                    index=2;
                    name="用户";
                    initRellStep(2);
                }else{

                }
                break;
            case R.id.tv2:
                if(index==1){
                    index=2;
                    name="设计";
                    initRellStep(2);
                }else{

                }
                break;
            case R.id.tv3:
                if(index==1){
                    index=2;
                    name="工人";
                    initRellStep(2);
                }else{

                }
                break;
            case R.id.tv4:
                if(index==1){
                    index=2;
                    name="监理";
                    initRellStep(2);
                }else{

                }
                break;


        }
    }

}
