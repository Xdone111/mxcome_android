package com.fenazola.mxcome.fragment.me;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.widget.RadioGroupLayout;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/4/8.
 * 意见反馈
 */

public class FeedbackFragment extends BaseFragment {

    private RadioGroupLayout radioLayout;
    private EditText editText;
    private RadioButton printScreen;
    private TextView more;
    private TextView priavcy;
    private Button send;
    private Button cancel;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_feedback;
    }

    @Override
    public void initView() {
        super.initView();
        radioLayout = (RadioGroupLayout) findViewById(R.id.radio_layout);
        editText = (EditText) findViewById(R.id.editText);
        printScreen = (RadioButton) findViewById(R.id.printscreen);
        more = (TextView) findViewById(R.id.tv_more);
        priavcy = (TextView) findViewById(R.id.tv_privacy);
        send = (Button) findViewById(R.id.send);
        cancel = (Button) findViewById(R.id.cancel);
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
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("意见反馈");
    }
}
