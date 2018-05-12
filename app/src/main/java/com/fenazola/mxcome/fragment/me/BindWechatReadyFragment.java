package com.fenazola.mxcome.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.iframe.BaseApp;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.wechat.WechatEntry;
import com.fenazola.mxcome.fragment.sercentre.FaqTermsFragment;
import com.fenazola.mxcome.utils.Constant;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

/**
 * 微信绑定过渡页
 */
public class BindWechatReadyFragment extends BaseFragment implements View.OnClickListener {

    private TextView login;
    private TextView register;
    private TextView forget_pwd;
    private TextView provision;
    private ImageView wechatIv;
    private WechatEntry entry;
    private TextView wechat_login_tips;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_login_ready;
    }

    @Override
    public void initView() {
        super.initView();
        entry=(WechatEntry) getArguments().get(Constant.key1);
        login = (TextView) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        forget_pwd = (TextView) findViewById(R.id.forget_pwd);
        provision = (TextView) findViewById(R.id.provision);
        View root = findViewById(R.id.root);
        root.setBackgroundResource(R.mipmap.login_bg);
        wechatIv = (ImageView) findViewById(R.id.wechat_login);
        wechatIv.setOnClickListener(this);
        wechatIv.setVisibility(View.INVISIBLE);
        register.setText("已有账号");
        login.setText("没有账号");
        wechat_login_tips=(TextView)findViewById(R.id.wechat_login_tips);
        wechat_login_tips.setVisibility(View.INVISIBLE);
        provision.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        forget_pwd.setOnClickListener(this);
        provision.setOnClickListener(this);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE);
        getBaseActivity().setStatusBarColor(R.color.ready_state_bg);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.login://没有账号
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(Constant.key1,false);
                intent.putExtra(Constant.key2,entry);
                intent.putExtra(ZFrameActivity.CLASS_NAME, BindWechatFragment.class.getName());
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.register://有账号
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(Constant.key1,true);
                intent.putExtra(Constant.key2,entry);
                intent.putExtra(ZFrameActivity.CLASS_NAME, BindWechatFragment.class.getName());
                startActivity(intent);
                getActivity().finish();

                break;

        }
    }

}
