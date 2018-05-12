package com.fenazola.mxcome.fragment.me.help;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/4/10.
 * 装修流程
 */

public class DecoreFlowFragment extends BaseFragment implements View.OnClickListener {

    private ImageView photo;
    private TextView before;
    private TextView middle;
    private TextView after;
    private ImageView add;
    private LinearLayout item1;
    private LinearLayout item2;
    private LinearLayout item3;
    private Button begin;

    private TextView name;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_decore_flow;
    }

    @Override
    public void initView() {
        super.initView();
        photo = (ImageView) findViewById(R.id.photo);
        name=(TextView)findViewById(R.id.name);
        before = (TextView) findViewById(R.id.tv_before);
        middle = (TextView) findViewById(R.id.tv_middle);
        after = (TextView) findViewById(R.id.tv_after);
        add = (ImageView) findViewById(R.id.iv_add);
        item1 = (LinearLayout) findViewById(R.id.item1);
        item2 = (LinearLayout) findViewById(R.id.item2);
        item3 = (LinearLayout) findViewById(R.id.item3);
        begin = (Button) findViewById(R.id.begin);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        before.setOnClickListener(this);
        middle.setOnClickListener(this);
        after.setOnClickListener(this);
        add.setOnClickListener(this);
        UserEntry entry= Utils.getUserEntry();
//        if(entry!=null){
//            Glide.with(getActivity()).load(Constant.imageUrl + entry.getPic()).into(photo);
//            name.setText(entry.getUser_name());
//        }

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle(getString(R.string.me_help_text3));
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setRightImage(R.mipmap.me_help);

        toolbar.setBgColor(getColor(R.color.colorBlue));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_after:
                item1.setVisibility(View.VISIBLE);
                item2.setVisibility(View.INVISIBLE);
                item3.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_middle:
                item1.setVisibility(View.INVISIBLE);
                item2.setVisibility(View.VISIBLE);
                item3.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_before:
                item1.setVisibility(View.INVISIBLE);
                item2.setVisibility(View.INVISIBLE);
                item3.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_add:
                NewProjectFragment fragment = new NewProjectFragment();
                addFragment(fragment);
                break;

            case R.id.begin:

                break;
        }
    }
}
