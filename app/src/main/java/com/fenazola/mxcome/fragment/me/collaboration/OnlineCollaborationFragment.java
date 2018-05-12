package com.fenazola.mxcome.fragment.me.collaboration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.loan.LoanFragment;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by xuhuixiang on 2017/7/29.
 * 在线合作
 */

public class OnlineCollaborationFragment extends BaseFragment implements View.OnClickListener{
    private EditText etv1,etv2,etv3;
    private TextView toNext;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_onlin_collaboration;
    }

    @Override
    public void initView() {
        super.initView();
        etv1=(EditText)findViewById(R.id.et_1);
        etv2=(EditText)findViewById(R.id.et_2);
        etv3=(EditText)findViewById(R.id.et_3);
        toNext=(TextView)findViewById(R.id.to_next);
        toNext.setOnClickListener(this);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("在线加盟");
        toolbar.setBgColor(getColor(R.color.trans));
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setLeftImage(getResources().getDrawable(R.mipmap.icon_grey_back));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_next:
                break;
        }
    }
}
