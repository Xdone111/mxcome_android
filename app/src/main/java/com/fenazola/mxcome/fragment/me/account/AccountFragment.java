package com.fenazola.mxcome.fragment.me.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.mepay.RechargeFragment;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by XDONE on 2017/4/12.
 * 账户
 */

public class AccountFragment extends BaseFragment implements View.OnClickListener {

    private TextView advance;
    private TextView recharge;
    private LinearLayout rollToMePay;
    private LinearLayout liner1;
    private LinearLayout liner2;
    private LinearLayout addCard;
    private ImageView creditCard;
    private ImageView unionpay;
    private ImageView wechat;
    private ImageView alipay;
    private TextView money;
    private String account;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_account;
    }

    @Override
    public void initView() {
        super.initView();
        advance = (TextView) findViewById(R.id.tv_advance);
        recharge = (TextView) findViewById(R.id.tv_recharge);
        rollToMePay = (LinearLayout) findViewById(R.id.roll_to_mepay);
        liner1 = (LinearLayout) findViewById(R.id.liner1);
        liner2 = (LinearLayout) findViewById(R.id.liner2);
        addCard = (LinearLayout) findViewById(R.id.add_card);
        creditCard = (ImageView) findViewById(R.id.credit_card);
        unionpay = (ImageView) findViewById(R.id.unionpay);
        wechat = (ImageView) findViewById(R.id.wechat);
        alipay = (ImageView) findViewById(R.id.alipay);
        money = (TextView) findViewById(R.id.money);
        rollToMePay.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        recharge.setOnClickListener(this);
        advance.setOnClickListener(this);
        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            account = args.getString(Constant.key1);
            if (!TextUtils.isEmpty(account)) {
                money.setText(account);
            } else {
                money.setText("0");
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_advance:
                AdvanceFragment fragment1 = new AdvanceFragment();
                addFragment(fragment1);
                break;
            case R.id.tv_recharge:
                RechargeFragment fragment2 = new RechargeFragment();
                addFragment(fragment2);
                break;
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("账户");
        toolbar.setRightText("账单");
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FundDetailsFragment fragment1 = new FundDetailsFragment();
                addFragment(fragment1);
            }
        });
    }
}
