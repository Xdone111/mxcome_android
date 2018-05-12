package com.fenazola.mxcome.fragment.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fenazola.mxcome.MainActivity;
import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * 模块装修
 */
public class ProjectModuleFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private RelativeLayout relativeLayout4;
    private RelativeLayout relativeLayout5;
    private RelativeLayout relativeLayout6;
    private RelativeLayout relativeLayout7;
    private RelativeLayout relativeLayout8;
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private CheckBox cb5;
    private CheckBox cb6;
    private CheckBox cb7;
    private CheckBox cb8;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_project_module;
    }

    @Override
    public void initView() {
        super.initView();
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.relativeLayout3);
        relativeLayout4 = (RelativeLayout) findViewById(R.id.relativeLayout4);
        relativeLayout5 = (RelativeLayout) findViewById(R.id.relativeLayout5);
        relativeLayout6 = (RelativeLayout) findViewById(R.id.relativeLayout6);
        relativeLayout7 = (RelativeLayout) findViewById(R.id.relativeLayout7);
        relativeLayout8 = (RelativeLayout) findViewById(R.id.relativeLayout8);

        cb1 = (CheckBox) findViewById(R.id.cb1);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        cb3 = (CheckBox) findViewById(R.id.cb3);
        cb4 = (CheckBox) findViewById(R.id.cb4);
        cb5 = (CheckBox) findViewById(R.id.cb5);
        cb6 = (CheckBox) findViewById(R.id.cb6);
        cb7 = (CheckBox) findViewById(R.id.cb7);
        cb8 = (CheckBox) findViewById(R.id.cb8);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        relativeLayout1.setOnClickListener(this);
        relativeLayout2.setOnClickListener(this);
        relativeLayout3.setOnClickListener(this);
        relativeLayout4.setOnClickListener(this);
        relativeLayout5.setOnClickListener(this);
        relativeLayout6.setOnClickListener(this);
        relativeLayout7.setOnClickListener(this);
        relativeLayout8.setOnClickListener(this);

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgRes(R.color.trans);
        toolbar.setRightText("首页");
        toolbar.setRightTextColor(getColor(R.color.white));
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        toolbar.setTitle(getString(R.string.project_module));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeLayout1:
                break;
            case R.id.relativeLayout2:
                break;
            case R.id.relativeLayout3:
                break;
            case R.id.relativeLayout4:
                break;
            case R.id.relativeLayout5:
                break;
            case R.id.relativeLayout6:
                break;
            case R.id.relativeLayout7:
                break;
            case R.id.relativeLayout8:
                break;
        }
    }


}
