package com.fenazola.mxcome.fragment.me.help;

import android.os.Bundle;
import android.widget.Button;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.EditTextUtils;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonEditWidget;

/**
 * Created by XDONE on 2017/4/11.
 * 新建工程
 */

public class NewProjectFragment extends BaseFragment {

    private CommonEditWidget eWidget1;
    private CommonEditWidget eWidget2;
    private Button apply;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_new_project;
    }

    @Override
    public void initView() {
        super.initView();
        eWidget1 = (CommonEditWidget) findViewById(R.id.eWidget1);
        eWidget2 = (CommonEditWidget) findViewById(R.id.eWidget2);
        apply = (Button) findViewById(R.id.apply);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        eWidget1.setHint("请教您的尊姓大名");
        eWidget2.setHint("请教您的电话号码");
        EditTextUtils.setPhoneAcceptedChars(eWidget1.getEditText());

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("新建工程");
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setTitleColor(getColor(R.color.grey));
    }
}
