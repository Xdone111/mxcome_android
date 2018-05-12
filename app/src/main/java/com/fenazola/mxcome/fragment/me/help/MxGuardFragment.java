package com.fenazola.mxcome.fragment.me.help;

import android.text.Html;
import android.view.View;
import android.widget.RadioButton;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/5/3.
 * 安全盾
 */

public class MxGuardFragment extends BaseFragment {
    RadioButton printscreen;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mx_guard;
    }

    @Override
    public void initView() {
        super.initView();
        printscreen=(RadioButton)findViewById(R.id.printscreen);
        printscreen.setText(Html.fromHtml("&nbsp;&nbsp;我已阅读并接受<font color='#4BB7FD'>《MXCOME装修盾服务条款》</font>"));
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("装修盾");
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }
}
