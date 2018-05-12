package com.fenazola.mxcome.fragment.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.EditTextUtils;
import com.fenazola.mxcome.utils.SmsSendHelper;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;
import com.zss.library.widget.CommonEditWidget;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 忘记密码
 */
public class ForgetPwdFragment extends BaseFragment implements View.OnClickListener {

    private Button confirm;
    private CommonEditWidget eWidget1;
    private CommonEditWidget eWidget2;
    private CommonEditWidget eWidget3;
    private SmsSendHelper smsHelper;
    private View view1;
    private View view2;
    private View view3;


    public void initSms() {
        SMSSDK.initSDK(getActivity(), Constant.SMS_APPKEY, Constant.SMS_APPSECRET);
        SMSSDK.registerEventHandler(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).getMessage();
                }
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_forget;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.white);
        confirm = (Button) findViewById(R.id.confirm);
        eWidget1 = (CommonEditWidget) findViewById(R.id.eWidget1);
        eWidget2 = (CommonEditWidget) findViewById(R.id.eWidget2);
        eWidget3 = (CommonEditWidget) findViewById(R.id.eWidget3);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        int dp = DPUtils.dip2px(getContext(), 10);

        eWidget1.setLeftImageResource(R.mipmap.register_phone);
        eWidget1.setHint(getString(R.string.login_user));
        eWidget1.setCenterPadding(dp, 0, 0, 0);

        eWidget2.setLeftImageResource(R.mipmap.register_vcode);
        eWidget2.setHint(getString(R.string.login_vcode));
        eWidget2.setCenterPadding(dp, 0, 0, 0);
        TextView rightView = (TextView) getLayoutInflater(R.layout.layout_send_sms);
        eWidget2.setRightView(rightView);
        eWidget2.setInputType(EditTextUtils.getNumberSignedType());
        EditTextUtils.setMaxLength(eWidget2.getEditText(), 6);

        eWidget3.setLeftImageResource(R.mipmap.login_pwd);
        eWidget3.setHint(getString(R.string.login_new_pwd));
        eWidget3.setCenterPadding(dp, 0, 0, 0);

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
        eWidget3.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    view3.setVisibility(View.VISIBLE);
                } else {
                    view3.setVisibility(View.GONE);
                }
            }
        });

        smsHelper = new SmsSendHelper(rightView, 60000, 1000, new SmsSendHelper.ICallback() {
            @Override
            public boolean onStartTimer() {
                return sendSms(eWidget1.getText());
            }
        });

        initSms();
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle(getString(R.string.me_forget_pwd));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.provision:

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
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_vcode);
                    return;
                }
                if (TextUtils.isEmpty(eWidget3.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd);
                    return;
                }
                if (eWidget3.getText().length() < 6) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd_length);
                    return;
                }
                break;
        }
    }

    public boolean sendSms(String phone) {
        if (TextUtils.isEmpty(phone)) {
            CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_phone);
            return false;
        }
        SMSSDK.getVerificationCode("+86", phone);
        return true;
    }
}
