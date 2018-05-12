package com.fenazola.mxcome.fragment.me.mepay;

import android.os.Bundle;
import android.view.View;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/4/7.
 * 资金增值
 */

@Deprecated
public class FundAddFragment extends BaseFragment {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_fund_add;
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
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("资金增值");
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setTitleColor(getColor(R.color.black));
        View view = getLayoutInflater(R.layout.layout_tab_fund_add);
        toolbar.setRightView(view);
    }
}
