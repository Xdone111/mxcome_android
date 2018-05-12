package com.fenazola.mxcome.fragment.me.account;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.me.mepay.AccountProblemFragment;
import com.fenazola.mxcome.fragment.me.mepay.MyPayDialog;
import com.fenazola.mxcome.fragment.sercentre.FaqTermsFragment;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * Created by xuhuixiang on 2017/7/28.
 */

public class ResetPaymentPasswordFragment extends BaseFragment implements View.OnClickListener{
    ImageView iv1,iv2;
    TextView nextTv;
    RadioButton printscreen;
    TextView printscreen_tv;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_reset_password;
    }

    @Override
    public void initView() {
        super.initView();
        iv1=(ImageView)findViewById(R.id.iv_1);
        iv2=(ImageView)findViewById(R.id.iv_2);
        nextTv=(TextView)findViewById(R.id.show_next);
        nextTv.setOnClickListener(this);
        iv1.setOnClickListener(this);
        iv2.setOnClickListener(this);
        printscreen=(RadioButton)findViewById(R.id.printscreen);
        printscreen_tv=(TextView)findViewById(R.id.printscreen_tv);
        printscreen_tv.setOnClickListener(this);
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
        toolbar.setTitle("重置支付密码");
        toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
        toolbar.setTitleColor(Color.WHITE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.printscreen_tv:
                Intent intent= new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqTermsFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.show_next:
                Intent intent1= new Intent(getActivity(), ZFrameActivity.class);
                intent1.putExtra(ZFrameActivity.CLASS_NAME, AccountProblemFragment.class.getName());
                startActivity(intent1);
                 break;
            case R.id.iv_1:
                MyPayDialog dialog = new MyPayDialog(getActivity(),"为了您的资金安全，只能绑定实名用户本人的银行卡",
                        "如需更多帮助，请致电MXCOME客服<font color='#4BB7FD'>400-888-888</font>","持卡人说明");
                dialog.show();
                break;
            case R.id.iv_2:
                MyPayDialog dialog1 = new MyPayDialog(getActivity(),"银行预留手机是你在办理该银行卡的时所填写的手机号。"
                        ,"没有预留、忘记手机号或已停用，可联系银行客服更新处理。","手机号说明");
                dialog1.show();
                break;
        }
    }
}
