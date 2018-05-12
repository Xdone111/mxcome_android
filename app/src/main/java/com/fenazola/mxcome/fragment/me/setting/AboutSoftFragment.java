package com.fenazola.mxcome.fragment.me.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.fenazola.mxcome.GuideActivity;
import com.fenazola.mxcome.MainActivity;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.LoginFragment;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/3/10.
 * 关于本软件
 */

public class AboutSoftFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout fenazola, leadPage, law, praise;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_about_soft;
    }

    @Override
    public void initView() {
        super.initView();
        fenazola = (LinearLayout) findViewById(R.id.ll_about_fenazona);
        leadPage = (LinearLayout) findViewById(R.id.ll_about_back_leadpage);
        law = (LinearLayout) findViewById(R.id.ll_about_law);
        praise = (LinearLayout) findViewById(R.id.ll_about_praise);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        leadPage.setOnClickListener(this);
        fenazola.setOnClickListener(this);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle(getString(R.string.toolbar_about_soft));
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.ll_about_back_leadpage:
                intent = new Intent(getContext(), GuideActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_about_fenazona:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, AboutCopyFragment.class.getName());
                startActivity(intent);
                break;

        }

    }
}
