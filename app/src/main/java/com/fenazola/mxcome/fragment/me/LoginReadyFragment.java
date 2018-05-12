package com.fenazola.mxcome.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.iframe.BaseApp;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.sercentre.FaqTermsFragment;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.Map;

/**
 * 登录过度页
 */
public class LoginReadyFragment extends BaseFragment implements View.OnClickListener {

    private TextView login;
    private TextView register;
    private TextView forget_pwd;
    private TextView provision;
    // 微信登录
    private static IWXAPI WXapi;
    private ImageView wechatIv;
    private UMShareAPI mShareAPI = null;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_login_ready;
    }

    @Override
    public void initView() {
        super.initView();
        mShareAPI = UMShareAPI.get(getActivity());
        login = (TextView) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        forget_pwd = (TextView) findViewById(R.id.forget_pwd);
        provision = (TextView) findViewById(R.id.provision);
        View root = findViewById(R.id.root);
        root.setBackgroundResource(R.mipmap.login_bg);
        wechatIv = (ImageView) findViewById(R.id.wechat_login);
        wechatIv.setOnClickListener(this);
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
            case R.id.login:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.register:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, RegisterFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.forget_pwd:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, ForgetPwdFragment.class.getName());
                startActivity(intent);
                break;

            case R.id.wechat_login:
                WXLogin();
                break;
            case R.id.provision:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqTermsFragment.class.getName());
                startActivity(intent);
                break;
        }
    }

    /**
     * 登录微信
     */
    private void WXLogin() {
        WXapi = WXAPIFactory.createWXAPI(getActivity(), BaseApp.WX_SHARE_APPID, true);
        WXapi.registerApp(BaseApp.WX_SHARE_APPID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        WXapi.sendReq(req);
        getActivity().finish();


    }

}
