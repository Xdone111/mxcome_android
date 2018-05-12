package com.fenazola.mxcome.fragment.msg;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/3/14.
 */

public class ComplainFragment extends BaseFragment implements View.OnClickListener {

    private TextView complaint;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_complain;
    }

    @Override
    public void initView() {
        super.initView();
        complaint = (TextView) findViewById(R.id.complaint);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        complaint.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ComplainKnowFragment fragment = new ComplainKnowFragment();
        addFragment(fragment);

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("投 诉");
    }
}
