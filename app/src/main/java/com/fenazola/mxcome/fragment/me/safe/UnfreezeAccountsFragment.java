package com.fenazola.mxcome.fragment.me.safe;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;

import java.util.ArrayList;

/**
 * Created by xuhuixiang on 2017/7/27.
 * 冻结 or解冻账号
 */

public class UnfreezeAccountsFragment extends BaseFragment {
    public static int IN_TYPE_UNFREE = 1;
    public static int IN_TYPE_FREE = 2;
    private int type;
    private TextView tv_tips;
    private EditText phone_et;
    private EditText user_et;
    private TextView error_content;
    private LinearLayout tip_error_ll;
    private TextView next;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_un_accounts;
    }

    @Override
    public void initView() {
        super.initView();
        type = getArguments().getInt(Constant.key1);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        phone_et = (EditText) findViewById(R.id.phone_et);
        user_et = (EditText) findViewById(R.id.user_et);
        error_content = (TextView) findViewById(R.id.error_content);
        TextView rightView = (TextView) getLayoutInflater(R.layout.layout_send_sms);
        tip_error_ll = (LinearLayout) findViewById(R.id.tip_error_ll);
        next = (TextView) findViewById(R.id.next);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(user_et.getText())) {
                    tip_error_ll.setVisibility(View.VISIBLE);
                    error_content.setText("请输入正确的账号");
                    return;
                } else {
                    tip_error_ll.setVisibility(View.GONE);

                }
                if (TextUtils.isEmpty(phone_et.getText())) {
                    tip_error_ll.setVisibility(View.VISIBLE);
                    error_content.setText("请输入正确的手机号码");
                    return;
                } else {
                    tip_error_ll.setVisibility(View.GONE);

                }
            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);
        CommonToolbar toolbar = getToolbar();
        if (type == IN_TYPE_UNFREE) {
            toolbar.setTitle("冻结账号");
            tv_tips.setText("请输入您要冻结的账号");
        } else {
            toolbar.setTitle("解冻账号");
            tv_tips.setText("请输入您要解冻的账号");


        }
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setRightText("安全中心  ");
        toolbar.setRightTextColor(getColor(R.color.colorBlue));
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
    }
}
