package com.fenazola.mxcome.fragment.main.demand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.widget.LocationItemLayout;
import com.fenazola.mxcome.widget.RadioGroupLayout;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.viewpager.AdViewPager;

/**
 * 发布需求右边
 */
public class DemandRightFragment extends BaseFragment {

    private RadioGroupLayout radioLayout1, radioLayout2;

    @Override
    public int getLayoutResId() {
        return R.layout.layout_drawerlayout;
    }


    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.white);
        radioLayout1 = (RadioGroupLayout) findViewById(R.id.radio_layout1);
        radioLayout2 = (RadioGroupLayout) findViewById(R.id.radio_layout2);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        radioLayout1.setOnCheckedChangeListener(new RadioGroupLayout.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupLayout group, int checkedId) {
                if (checkedId != -1) {
                    radioLayout2.clearCheck();
                    radioLayout1.check(checkedId);
                }
                switch (checkedId) {

                }
            }
        });
        radioLayout2.setOnCheckedChangeListener(new RadioGroupLayout.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroupLayout group, int checkedId) {
                if (checkedId != -1) {
                    radioLayout1.clearCheck();
                    radioLayout2.check(checkedId);
                }
                switch (checkedId) {

                }
            }
        });

    }

}
