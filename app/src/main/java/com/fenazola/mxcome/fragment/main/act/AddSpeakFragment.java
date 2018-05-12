package com.fenazola.mxcome.fragment.main.act;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.j256.ormlite.stmt.query.In;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * Created by xuhuixiang on 2017/7/27.
 * 账户问题
 */

public class AddSpeakFragment extends BaseFragment implements View.OnClickListener {
    private TextView toEdit;
    private EditText edit;
    @Override
    public int getLayoutResId() {
        return R.layout.speak_add_new_fragment;
    }

    @Override
    public void initView() {
        super.initView();
        toEdit=(TextView) findViewById(R.id.show_to_edit);
        edit=(EditText) findViewById(R.id.comment_et);
        toEdit.setOnClickListener(this);

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
        toolbar.setTitle("我要为MXCOME代言");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.show_to_edit:
                toEdit.setVisibility(View.INVISIBLE);
                edit.setVisibility(View.VISIBLE);
                break;
        }

    }
}
