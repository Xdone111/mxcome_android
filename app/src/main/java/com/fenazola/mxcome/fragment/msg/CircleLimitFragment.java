package com.fenazola.mxcome.fragment.msg;

import android.os.Bundle;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/7/31.
 * 设圈子权限
 */

public class CircleLimitFragment extends BaseFragment {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_circle_limit;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("设圈子权限");
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }
}
