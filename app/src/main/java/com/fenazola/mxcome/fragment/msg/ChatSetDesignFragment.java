package com.fenazola.mxcome.fragment.msg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

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
public class ChatSetDesignFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout widget1;
    private CommonSwitchWidget widget2;
    private CommonSwitchWidget widget3;
    private CommonTextWidget widget4;
    //private CommonTextWidget widget5;
    //private CommonTextWidget widget6;
    private CommonTextWidget widget7;
    //private CommonSwitchWidget widget8;
    private CommonSwitchWidget widget9;
    private CommonTextWidget widget10;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_chat_set_design;
    }

    @Override
    public void initView() {
        super.initView();
        widget1 = (RelativeLayout) findViewById(R.id.widget1);
        widget2 = (CommonSwitchWidget) findViewById(R.id.widget2);
        widget3 = (CommonSwitchWidget) findViewById(R.id.widget3);
        widget4 = (CommonTextWidget) findViewById(R.id.widget4);
        //widget5 = (CommonTextWidget)findViewById(R.id.widget5);
        //widget6 = (CommonTextWidget)findViewById(R.id.widget6);
        widget7 = (CommonTextWidget) findViewById(R.id.widget7);
        //widget8 = (CommonSwitchWidget)findViewById(R.id.widget8);
        widget9 = (CommonSwitchWidget) findViewById(R.id.widget9);
        widget10 = (CommonTextWidget) findViewById(R.id.widget10);
        widget1.setOnClickListener(this);
        widget10.setOnClickListener(this);
        widget7.setOnClickListener(this);
        widget4.setOnClickListener(this);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
//        widget1.setLeftText("李云龙");
//        widget1.setSummaryText("我的好友");
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle(getString(R.string.msg_chat_set));
//        toolbar.setRightImage(R.mipmap.w_add_find);
//        toolbar.setOnRightListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initDialog("发送好友申请");
//            }
//        });
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
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, RemarkInfoFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.widget10:
                initDialog("举报成功");
                break;
            case R.id.widget7:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, SetHelloFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.widget4:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FirdChatLogFragment.class.getName());
                startActivity(intent);
                break;
        }
    }

}
