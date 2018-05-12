package com.fenazola.mxcome.fragment.project.monitor;

import android.view.View;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/7/28.
 * 工地监控
 */

public class SiteMonitorFragment extends BaseFragment {
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_site_monitor;
    }

    @Override
    public void initView() {
        super.initView();
        View root = findViewById(R.id.root);
        root.setBackgroundResource(R.mipmap.project_site_monitor_bg);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.trans_a));
        toolbar.setTitle("工地监控");
        toolbar.setTitleColor(getColor(R.color.white));
    }
}
