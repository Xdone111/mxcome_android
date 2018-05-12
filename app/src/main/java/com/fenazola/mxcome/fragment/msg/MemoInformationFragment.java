package com.fenazola.mxcome.fragment.msg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.collaboration.OnlineCollaborationStepNextFragment;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by xuhuixiang on 2017/7/29.
 * 备注信息
 */
@Deprecated
public class MemoInformationFragment extends BaseFragment implements View.OnClickListener {
    private EditText etv1, etv2, etv3;
    private TextView toNext;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_memo_information;
    }

    @Override
    public void initView() {
        super.initView();
        etv1 = (EditText) findViewById(R.id.et_1);
        etv2 = (EditText) findViewById(R.id.et_2);
        etv3 = (EditText) findViewById(R.id.et_3);
        toNext = (TextView) findViewById(R.id.to_next);
        toNext.setOnClickListener(this);

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
        toolbar.setTitle("备注信息");
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.to_next:

                break;
        }
    }
}
