package com.fenazola.mxcome.fragment.msg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/3/16.
 */

public class NewFriendFragment extends BaseFragment {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_new_friends;
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("新的好友");
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }
}
