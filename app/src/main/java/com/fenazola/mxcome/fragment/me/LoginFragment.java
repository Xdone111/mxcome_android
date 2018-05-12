package com.fenazola.mxcome.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.iframe.BaseApp;
import com.fenazola.mxcome.MainActivity;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.fragment.sercentre.FaqTermsFragment;
import com.fenazola.mxcome.utils.EditTextUtils;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushManager;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.MD5Utils;
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.widget.CommonEditWidget;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener {


    private TextView register;
    private TextView forget_pwd;
    private TextView provision;
    private Button confirm;
    private CommonEditWidget eWidget1;
    private CommonEditWidget eWidget2;
    private View view1, view2;
    // 微信登录
    private static IWXAPI WXapi;
    private ImageView wechatIv;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView() {
        super.initView();
        register = (TextView) findViewById(R.id.register);
        forget_pwd = (TextView) findViewById(R.id.forget_pwd);
        provision = (TextView) findViewById(R.id.provision);
        confirm = (Button) findViewById(R.id.confirm);
        eWidget1 = (CommonEditWidget) findViewById(R.id.eWidget1);
        eWidget2 = (CommonEditWidget) findViewById(R.id.eWidget2);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        wechatIv = (ImageView) findViewById(R.id.wechat_login);
        wechatIv.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setBgColor(R.color.white);
        int dp = DPUtils.dip2px(getContext(), 10);
        eWidget1.setLeftImageResource(R.mipmap.project_person);
        eWidget1.setHint(getString(R.string.login_user));
        eWidget1.setCenterPadding(dp, 0, 0, 0);
        EditTextUtils.setPhoneAcceptedChars(eWidget1.getEditText());

        eWidget2.setLeftImageResource(R.mipmap.project_password);
        eWidget2.setHint(getString(R.string.login_pwd));
        eWidget2.setCenterPadding(dp, 0, 0, 0);
        EditTextUtils.setPasswordAcceptedChars(eWidget2.getEditText());

        eWidget1.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    view1.setVisibility(View.VISIBLE);
                } else {
                    view1.setVisibility(View.GONE);
                }
            }
        });

        eWidget2.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    view2.setVisibility(View.VISIBLE);
                } else {
                    view2.setVisibility(View.GONE);
                }
            }
        });

        register.setOnClickListener(this);
        forget_pwd.setOnClickListener(this);
        provision.setOnClickListener(this);
        confirm.setOnClickListener(this);

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRegisterSuccess(String phone) {
        CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_reg_success);
        eWidget1.setText(phone);
        eWidget2.setText("");
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle(getString(R.string.me_login));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.wechat_login:
                WXLogin();
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
            case R.id.provision:
//                ServiceTermsFragment fragment = new ServiceTermsFragment();
//                addFragment(fragment);
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqTermsFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.confirm:
                if (TextUtils.isEmpty(eWidget1.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_phone);
                    return;
                }
                if (eWidget1.getText().startsWith("1") && eWidget1.getText().length() != 11) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_ok_phone);
                    return;
                }
                if (eWidget1.getText().startsWith("+") && eWidget1.getText().length() != 14) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_ok_phone);
                    return;
                }
                if (TextUtils.isEmpty(eWidget2.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd);
                    return;
                }
                if (eWidget2.getText().length() < 6) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd_length);
                    return;
                }
                goLogin();
                break;
        }
    }

    public void goLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("user_or_tel", eWidget1.getText());
        String md5Str = MD5Utils.getMD5String(eWidget2.getText()).toUpperCase();
        map.put("pwd", md5Str);
        map.put("clientid", PushManager.getInstance().getClientid(getActivity()));
        NetWorkUtils.post(getActivity(), "appApi/appLogin.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
                prefUtils.put("isLogin", true);
                String res = resObj.optString("res");
                List<UserEntry> list = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<UserEntry>>() {
                }.getType());
                UserEntry entry = list.get(0);
                prefUtils.put("user", entry);
                EventBus.getDefault().post(entry);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
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
