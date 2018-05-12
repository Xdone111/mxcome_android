package com.fenazola.mxcome.fragment.me.safe;

import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.EditTextUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.SmsSendHelper;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.fenazola.mxcome.widget.PwdDialog;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.MD5Utils;
import com.zss.library.widget.CommonEditWidget;
import com.zss.library.widget.CommonTextWidget;

import org.json.JSONObject;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * Created by XDONE on 2017/6/2.
 * 设置支付密码
 */

public class SetPasswordFragment extends BaseFragment implements View.OnClickListener {

    private Button confirm;
    private CommonEditWidget eWidget1;
    private CommonEditWidget eWidget2;
    private CommonTextWidget eWidget3;
    private CommonTextWidget eWidget4;
    /**用来遮盖第一、二 个输入框的*/
    private CommonTextWidget eWidgetToast,eWidgetToast1;

    private SmsSendHelper smsHelper;
    private TextView show_view;
    //用来模拟输入框提示
    private TextView toast1,toast2;
    private ScrollView scrollView;

    Handler handler=new Handler();
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
        return R.layout.fragment_set_password;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.white);
        confirm = (Button) findViewById(R.id.confirm);
        eWidget1 = (CommonEditWidget) findViewById(R.id.eWidget1);
        eWidget2 = (CommonEditWidget) findViewById(R.id.eWidget2);
        eWidget3 = (CommonTextWidget) findViewById(R.id.eWidget3);
        eWidget4 = (CommonTextWidget) findViewById(R.id.eWidget4);

        eWidgetToast=(CommonTextWidget)findViewById(R.id.eWidget_toast);
        eWidgetToast1=(CommonTextWidget)findViewById(R.id.eWidget_toast1);

        show_view=(TextView)findViewById(R.id.show_view);
        scrollView=(ScrollView)findViewById(R.id.scrollView);
        toast1=(TextView)findViewById(R.id.toast_1);
        toast2=(TextView)findViewById(R.id.toast_2);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        int dp = DPUtils.dip2px(getContext(), 10);
        eWidget1.setCenterPadding(dp, 0, 0, 0);
        eWidget1.setLeftImageResource(R.mipmap.register_phone);
        eWidget1.setHint(getString(R.string.login_user));
        EditTextUtils.setPhoneAcceptedChars(eWidget1.getEditText());

        eWidget2.setLeftImageResource(R.mipmap.register_vcode);
        eWidget2.setHint(getString(R.string.login_vcode));
        TextView rightView = (TextView) getLayoutInflater(R.layout.layout_send_sms);
        eWidget2.setRightView(rightView);
        eWidget2.setCenterPadding(dp, 0, 0, 0);
        EditTextUtils.setMaxLength(eWidget2.getEditText(), 6);
        eWidget2.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);

        eWidget3.setLeftImageResource(R.mipmap.login_pwd);
        eWidget3.setLeftHintText(getString(R.string.login_pwd));
        eWidget3.setCenterPadding(dp, 0, 0, 0);
        eWidget3.setOnClickListener(this);
        eWidget3.getLeftTextView().setInputType(EditTextUtils.getPasswordType());

        eWidget4.setLeftImageResource(R.mipmap.login_pwd);
        eWidget4.setLeftHintText(getString(R.string.confirm_password));
        eWidget4.setCenterPadding(dp, 0, 0, 0);
        eWidget4.setOnClickListener(this);
        eWidget4.getLeftTextView().setInputType(EditTextUtils.getPasswordType());

        eWidgetToast.setLeftImageResource(R.mipmap.register_phone);
        eWidgetToast.setLeftHintText(getString(R.string.login_user));
        eWidgetToast.setOnClickListener(this);
        eWidgetToast.setCenterPadding(dp, 0, 0, 0);

        eWidgetToast.getLeftTextView().setInputType(EditTextUtils.getTextType());

        eWidgetToast1.setLeftImageResource(R.mipmap.register_vcode);
        eWidgetToast1.setLeftHintText(getString(R.string.login_vcode));
        eWidgetToast1.setOnClickListener(this);
        eWidgetToast1.getLeftTextView().setInputType(EditTextUtils.getTextType());
        eWidgetToast1.setCenterPadding(dp, 0, 0, 0);
        confirm.setOnClickListener(this);

        smsHelper = new SmsSendHelper(rightView, 60000, 1000, new SmsSendHelper.ICallback() {
            @Override
            public boolean onStartTimer() {
                return sendSms(eWidget1.getText());
            }
        });
        initSms();
    }

    PwdDialog dialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.eWidget3:
                show_view.setVisibility(View.VISIBLE);
                toast1.setVisibility(View.VISIBLE);
                eWidget3.setLeftHintText("");
                eWidget1.setVisibility(View.GONE);
                eWidgetToast.setVisibility(View.VISIBLE);
                eWidget2.setVisibility(View.GONE);
                eWidgetToast1.setVisibility(View.VISIBLE);
                eWidgetToast.setLeftText(eWidget1.getText().toString());
                eWidgetToast1.setLeftText(eWidget2.getText().toString());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });

                dialog = new PwdDialog(getActivity(), R.style.TransDialog, new PwdDialog.OnPwdListener() {
                    @Override
                    public void onTextChanged(String mStrPwd) {
                        eWidget3.setLeftText(mStrPwd);
                        if (mStrPwd.length() == 6) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void refreshActivity() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(ScrollView.FOCUS_UP);
                                show_view.setVisibility(View.GONE);
                                eWidget3.setLeftHintText(getString(R.string.login_pwd));
                                toast1.setVisibility(View.GONE);
                                eWidget1.setVisibility(View.VISIBLE);
                                eWidgetToast.setVisibility(View.GONE);
                                eWidget2.setVisibility(View.VISIBLE);
                                eWidgetToast1.setVisibility(View.GONE);

                            }
                        });
                    }
                });
                dialog.show();
                dialog.setDisplayTopEnable(false);
                break;
            case R.id.eWidget4:
                eWidget1.setVisibility(View.GONE);
                eWidgetToast.setVisibility(View.VISIBLE);
                eWidget2.setVisibility(View.GONE);
                eWidgetToast1.setVisibility(View.VISIBLE);
                show_view.setVisibility(View.VISIBLE);
                toast2.setVisibility(View.VISIBLE);
                eWidgetToast.setLeftText(eWidget1.getText().toString());
                eWidgetToast1.setLeftText(eWidget2.getText().toString());
                eWidget4.setLeftHintText("");

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                dialog = new PwdDialog(getActivity(), R.style.TransDialog, new PwdDialog.OnPwdListener() {
                    @Override
                    public void onTextChanged(String mStrPwd) {
                        eWidget4.setLeftText(mStrPwd);
                        if (mStrPwd.length() == 6) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void refreshActivity() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollView.fullScroll(ScrollView.FOCUS_UP);
                                show_view.setVisibility(View.GONE);
                                eWidget4.setLeftHintText(getString(R.string.confirm_password));
                                toast2.setVisibility(View.GONE);

                                eWidget1.setVisibility(View.VISIBLE);
                                eWidgetToast.setVisibility(View.GONE);
                                eWidget2.setVisibility(View.VISIBLE);
                                eWidgetToast1.setVisibility(View.GONE);

                            }
                        });
                    }
                });
                dialog.show();
                dialog.setDisplayTopEnable(false);
                break;
            case R.id.confirm:
                if (TextUtils.isEmpty(eWidget1.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_phone);
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
                if (TextUtils.isEmpty(eWidget3.getLeftText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd);
                    return;
                }
                if (eWidget3.getLeftText().length() < 6) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd_length);
                    return;
                }
                if (TextUtils.isEmpty(eWidget4.getLeftText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.confirm_password);
                    return;
                }
                if (!eWidget3.getLeftText().equals(eWidget4.getLeftText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), "输入的密码不一致");
                    return;
                }
                if (eWidget1.getText().startsWith("1") && eWidget1.getText().length() != 11) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_ok_phone);
                    return;
                }
                doConfirm(eWidget1.getText(), eWidget2.getText(), eWidget3.getLeftText());
                break;
        }
    }

    private void doConfirm(String tel, String verify_code, String pay_pwd) {
        UserEntry entry = Utils.getUserEntry();
        String user_id = entry.getUser_id();
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", user_id);
        map.put("tel", tel);
        map.put("verify_code", verify_code);
        String md5Str = MD5Utils.getMD5String(pay_pwd).toUpperCase();
        String encrypt = EncryptUtil.AesEncrypt(md5Str);
        map.put("pay_pwd", encrypt);
        map.put("os", "android");
        NetWorkUtils.post(getActivity(), "appApi/setPayPwd.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                UserEntry user = Utils.getUserEntry();
                user.setHasPayPwd(true);
                Utils.saveUserEntry(user);
                CommonToastUtils.showInCenterToast(getActivity(), "设置成功");
                getActivity().finish();
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public boolean sendSms(String phone) {
        if (TextUtils.isEmpty(phone)) {
            CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_phone);
            return false;
        }
        SMSSDK.getVerificationCode("+86", phone);
        return true;
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("设置支付密码");
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        smsHelper.onFinish();
    }
}
