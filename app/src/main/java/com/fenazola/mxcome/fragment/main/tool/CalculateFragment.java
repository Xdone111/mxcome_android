package com.fenazola.mxcome.fragment.main.tool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.main.CustomFragment;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/5/16.
 */

public class CalculateFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout linear_brick, linear_wall, linear_floor;
    private LinearLayout linear_gallery, linear_curtain, linear_coating;
    private LinearLayout linearFirst, linearSecond, linearThird;
    private TextView text_count1, text_count2, text_count3;
    private TextView text_price1, text_price2, text_price3;
    private int index = -1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_calculate;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.white);
        linear_brick = (LinearLayout) findViewById(R.id.linear_brick);
        linear_wall = (LinearLayout) findViewById(R.id.linear_wall);
        linearFirst = (LinearLayout) findViewById(R.id.linear_first);
        linear_floor = (LinearLayout) findViewById(R.id.linear_floor);
        linear_gallery = (LinearLayout) findViewById(R.id.linear_gallery);
        linearSecond = (LinearLayout) findViewById(R.id.linear_second);
        linear_curtain = (LinearLayout) findViewById(R.id.linear_curtain);
        linear_coating = (LinearLayout) findViewById(R.id.linear_coating);
        linearThird = (LinearLayout) findViewById(R.id.linear_third);
        text_count1 = (TextView) findViewById(R.id.text_count1);
        text_count2 = (TextView) findViewById(R.id.text_count2);
        text_count3 = (TextView) findViewById(R.id.text_count3);
        text_price1 = (TextView) findViewById(R.id.text_price1);
        text_price2 = (TextView) findViewById(R.id.text_price2);
        text_price3 = (TextView) findViewById(R.id.text_price3);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        linear_brick.setOnClickListener(this);
        linear_wall.setOnClickListener(this);
        linear_floor.setOnClickListener(this);
        linear_gallery.setOnClickListener(this);
        linear_curtain.setOnClickListener(this);
        linear_coating.setOnClickListener(this);
        linearFirst.setOnClickListener(this);
        linearSecond.setOnClickListener(this);
        linearThird.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;
        switch (id) {
            case R.id.linear_brick:
                linearFirst.setVisibility(View.VISIBLE);
                linearSecond.setVisibility(View.GONE);
                linearThird.setVisibility(View.GONE);
                linearFirst.setBackgroundColor(getColor(R.color.me_color_green));
                text_count1.setText("计算地砖的数量");
                text_price1.setText("计算地砖的价格");
                index = 1;
                // linear_brick.setBackgroundColor(getColor(R.color.item_selected));
                break;
            case R.id.linear_wall:
                linearFirst.setVisibility(View.VISIBLE);
                linearSecond.setVisibility(View.GONE);
                linearThird.setVisibility(View.GONE);
                linearFirst.setBackgroundColor(getColor(R.color.colorBlueDark));
                text_count1.setText("计算墙砖的数量");
                text_price1.setText("计算墙砖的价格");
                index = 2;

                break;
            case R.id.linear_floor:
                linearFirst.setVisibility(View.GONE);
                linearSecond.setVisibility(View.VISIBLE);
                linearThird.setVisibility(View.GONE);
                linearSecond.setBackgroundColor(getColor(R.color.colorBlue));
                text_count2.setText("计算地板的数量");
                text_price2.setText("计算地板的价格");
                index = 3;

                break;
            case R.id.linear_gallery:
                linearFirst.setVisibility(View.GONE);
                linearSecond.setVisibility(View.VISIBLE);
                linearThird.setVisibility(View.GONE);
                linearSecond.setBackgroundColor(getColor(R.color.colorRed));
                text_count2.setText("计算壁纸的数量");
                text_price2.setText("计算壁纸的价格");
                index = 4;

                break;
            case R.id.linear_curtain:
                linearFirst.setVisibility(View.GONE);
                linearSecond.setVisibility(View.GONE);
                linearThird.setVisibility(View.VISIBLE);
                linearThird.setBackgroundColor(getColor(R.color.colorYellow));
                text_count3.setText("计算窗帘的数量");
                text_price3.setText("计算窗帘的价格");
                index = 5;

                break;
            case R.id.linear_coating:
                linearFirst.setVisibility(View.GONE);
                linearSecond.setVisibility(View.GONE);
                linearThird.setVisibility(View.VISIBLE);
                linearThird.setBackgroundColor(getColor(R.color.colorGreen));
                text_count3.setText("计算涂料的数量");
                text_price3.setText("计算涂料的价格");
                index = 6;

                break;
            case R.id.linear_first:
            case R.id.linear_second:
            case R.id.linear_third:
                initToResult();
                break;
        }
    }

    private void initToResult() {
        Intent intent = new Intent(getActivity(), ZFrameActivity.class);
        intent.putExtra(ZFrameActivity.CLASS_NAME, CalculationResultFragment.class.getName());
        intent.putExtra(Constant.key1, index);
        startActivity(intent);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("计算器");
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setBgColor(getColor(R.color.white));
    }
}
