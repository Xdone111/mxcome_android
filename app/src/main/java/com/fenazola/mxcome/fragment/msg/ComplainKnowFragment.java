package com.fenazola.mxcome.fragment.msg;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/3/14.
 */

public class ComplainKnowFragment extends BaseFragment {

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_complain_know;
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("投诉须知");
    }
}
