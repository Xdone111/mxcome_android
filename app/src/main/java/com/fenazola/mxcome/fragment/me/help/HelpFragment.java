package com.fenazola.mxcome.fragment.me.help;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/4/10.
 * 帮助
 */

public class HelpFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout liner1, liner2, liner3, liner4;
    private ImageView backIv,rightIv;
    private TextView titleTv;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_help;
    }

    @Override
    public void initView() {
        super.initView();
        liner1 = (LinearLayout) findViewById(R.id.liner1);
        liner2 = (LinearLayout) findViewById(R.id.liner2);
        liner3 = (LinearLayout) findViewById(R.id.liner3);
        liner4 = (LinearLayout) findViewById(R.id.liner4);
        backIv=(ImageView)findViewById(R.id.show_title_left);
        titleTv=(TextView)findViewById(R.id.show_title_tv);
        rightIv=(ImageView)findViewById(R.id.show_title_right);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        liner1.setOnClickListener(this);
        liner3.setOnClickListener(this);
        titleTv.setText("帮 助");
        titleTv.setTextColor(getColor(R.color.white));
        rightIv.setImageResource(R.mipmap.me_help);
        rightIv.setVisibility(View.VISIBLE);
        backIv.setOnClickListener(this);


    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle(getString(R.string.help));
        toolbar.setRightImage(R.mipmap.me_help);
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_title_left:
                getActivity().onBackPressed();
                break;
            case R.id.liner1:
                MxGuardFragment fragment1 = new MxGuardFragment();
                addFragment(fragment1);
                break;
            case R.id.liner2:
                break;
            case R.id.liner3:
                DecoreFlowFragment fragment2 = new DecoreFlowFragment();
                addFragment(fragment2);
                break;
            case R.id.liner4:
                break;
        }

    }
}
