package com.fenazola.mxcome.fragment.msg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonSwitchWidget;
import com.zss.library.widget.CommonTextWidget;

/**
 * 聊天设置
 */
public class ChatSetDpecialAttentionFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout widget1;
    private CommonSwitchWidget widget2;
    private CommonTextWidget widget4;
    private CommonSwitchWidget widget9;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_chat_set_dpecial_attention;
    }

    @Override
    public void initView() {
        super.initView();
        widget1 = (RelativeLayout) findViewById(R.id.widget1);
        widget2 = (CommonSwitchWidget) findViewById(R.id.widget2);
        widget4 = (CommonTextWidget) findViewById(R.id.widget4);
        widget9 = (CommonSwitchWidget) findViewById(R.id.widget9);
        View v=getLayoutInflater(R.layout.layout_right_attention);
        TextView tv=(TextView) v.findViewById(R.id.tv_detail);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/8/10
            }
        });
        widget4.setRightView(v);
        widget1.setOnClickListener(this);
        widget4.setOnClickListener(this);

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
        toolbar.setTitle("特别关注设置");

    }

    private void initDialog(String title) {
        CommonDialog dialog = new CommonDialog(getActivity());
        dialog.setTitle(title);
        //dialog.setMiddleView(view);
        dialog.setDisplayMiddleEnable(false);
        dialog.setOnClickCancelListener("取 消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initSection();

            }
        });
        dialog.setOnClickConfirmListener("确 定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.widget1:


                break;

            case R.id.widget7:

                break;
            case R.id.widget4:
                // TODO: 2017/8/10
                break;
        }
    }

}
