package com.fenazola.mxcome.fragment.main.tool;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by xuhuixiang on 2017/7/27.
 * 材料计算拉
 */

public class CalculationResultFragment extends BaseFragment{
    private int index=-1;
    private String title="";
    private TextView tvLeft1,tvLeft2,tvleftNum,tvRinght1,tvRinght2,tvRinghtNum;
    private LinearLayout topLy;

    //private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12;
    //private TextView tv1Tip,tv2Tip,tv3Tip,tv4Tip,tv5Tip,tv6Tip,tv7Tip,tv8Tip,tv9Tip,tv10Tip,tv11Tip,tv12Tip;
    private EditText etv1,etv2,etv3,etv4,etv5,etv6,etv7,etv8,etv9,etv10,etv11,etv12;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_calculation_result;
    }

    @Override
    public void initView() {
        super.initView();
        index=(int)getArguments().get(Constant.key1);
        topLy=(LinearLayout)findViewById(R.id.top_ly);
        tvLeft1=(TextView)findViewById(R.id.top_tv_left1);
        tvLeft2=(TextView)findViewById(R.id.top_tv_left2);
        tvleftNum=(TextView)findViewById(R.id.top_tv_left);
        tvRinght1=(TextView)findViewById(R.id.top_tv_right1);
        tvRinght2=(TextView)findViewById(R.id.top_tv_right2);
        tvRinghtNum=(TextView)findViewById(R.id.top_tv_right);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        if(index==1){
            title="地砖";

            addView1();
            tvLeft2.setText("块");
        }else  if(index==2){
            title="墙砖";
            addView2();
            tvLeft2.setText("块");

        }else  if(index==3){
            title="地板";
            addView3();
            tvLeft2.setText("块");

        }else  if(index==4){
            title="壁纸";
            addView4();
            tvLeft2.setText("卷");

        }else  if(index==5){
            title="窗帘";
            addView5();
            tvLeft2.setText("米");

        }else  if(index==6){
            title="涂料";
            addView6();
            tvLeft2.setText("桶");

        }
        tvLeft1.setText(title+"数量");
        tvRinght1.setText(title+"价格");
        initChangType(tvleftNum);
        initChangType(tvRinghtNum);


    }

    private void addView1() {
        View view = getLayoutInflater(R.layout.fragment_calculation_result_child1);
        initViewChild(view);
        topLy.addView(view);

    }

    private void initViewChild(View view) {
        etv1=(EditText)view.findViewById(R.id.et_1);
        etv2=(EditText)view.findViewById(R.id.et_2);
        etv3=(EditText)view.findViewById(R.id.et_3);
        etv4=(EditText)view.findViewById(R.id.et_4);
        if(index!=5) {
            etv5 = (EditText) view.findViewById(R.id.et_5);
            if(index!=1&&index!=3&&index!=4) {
                etv6 = (EditText) view.findViewById(R.id.et_6);
                etv7 = (EditText) view.findViewById(R.id.et_7);
                etv8 = (EditText) view.findViewById(R.id.et_8);
                etv9 = (EditText) view.findViewById(R.id.et_9);
                etv10 = (EditText) view.findViewById(R.id.et_10);
                etv11 = (EditText) view.findViewById(R.id.et_11);
                if(index==2) {
                    etv12 = (EditText) view.findViewById(R.id.et_12);
                }
            }
        }



    }

    private void addView2() {
        View view = getLayoutInflater(R.layout.fragment_calculation_result_child2);
        topLy.addView(view);
    }
    private void addView3() {
        View view = getLayoutInflater(R.layout.fragment_calculation_result_child3);
        topLy.addView(view);
    }
    private void addView4() {
        View view = getLayoutInflater(R.layout.fragment_calculation_result_child4);
        topLy.addView(view);
    }
    private void addView5() {
        View view = getLayoutInflater(R.layout.fragment_calculation_result_child5);
        topLy.addView(view);
    }
    private void addView6() {
        View view = getLayoutInflater(R.layout.fragment_calculation_result_child6);
        topLy.addView(view);
    }


    private void initChangType(TextView tv) {
        // Font path
        String fontPath = "fonts/DS-DIGIT.TTF";
        // text view label
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        // Applying font
        tv.setTypeface(tf);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("计算"+title);
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setBgColor(getColor(R.color.white));
    }
}
