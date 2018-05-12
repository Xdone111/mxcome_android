package com.fenazola.mxcome.fragment.me.setting;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/3/28.
 * 推送开关
 */

public class PushSwitchFragment extends BaseFragment {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_push_switch;
    }

    @Override
    public void setTopBar() {

        getBaseActivity().setStatusBarColor(R.color.colorBlue);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle(getString(R.string.toolbar_push_switch));
    }
}
