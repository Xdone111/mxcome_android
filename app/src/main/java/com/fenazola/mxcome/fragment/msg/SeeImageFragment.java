package com.fenazola.mxcome.fragment.msg;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.project.PremiseEntry;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonDialog;

/**
 * Created by XDONE on 2017/8/1.
 */

public class SeeImageFragment extends BaseFragment {

    private ImageView imageView;
    private String stringUrl;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_see_image;
    }

    @Override
    public void initView() {
        super.initView();
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            stringUrl = (String) args.getSerializable(Constant.key1);
        }
        Glide.with(getActivity()).load(stringUrl).error(R.mipmap.module_bookroom).into(imageView);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        getBaseActivity().setStatusBarColor(R.color.trans_b);
        toolbar.setBgColor(getColor(R.color.trans_b));
        toolbar.setTitle("查看图片");
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setRightImage(R.mipmap.my_project_no_delete);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog dialog = new CommonDialog(getActivity());
                dialog.setTitle("删除图片");
                dialog.setDisplayMiddleEnable(false);
                dialog.setOnClickConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                dialog.setOnClickCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                dialog.show();
            }
        });
    }
}
