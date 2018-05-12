package com.fenazola.mxcome.fragment.me.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.fenazola.mxcome.GuideActivity;
import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/3/10.
 * 关于本软件
 */

public class AboutCopyFragment extends BaseFragment implements View.OnClickListener {


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_about_copy;
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
        toolbar.setTitle("FENAZOLA");
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_about_back_leadpage:
                Intent intent = new Intent(getContext(), GuideActivity.class);
                startActivity(intent);
                break;

        }

    }
}
