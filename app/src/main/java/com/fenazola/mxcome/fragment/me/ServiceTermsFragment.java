package com.fenazola.mxcome.fragment.me;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/6/3.
 * 已经用h5页面替换
 */
@Deprecated
public class ServiceTermsFragment extends BaseFragment {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_service_terms;
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);

        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("服务条款");
    }
}
