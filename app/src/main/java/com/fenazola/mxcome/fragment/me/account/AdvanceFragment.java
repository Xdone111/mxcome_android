package com.fenazola.mxcome.fragment.me.account;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fenazola.iframe.alipay.AuthResult;
import com.fenazola.iframe.alipay.AuthUtils;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.fragment.me.safe.SetPasswordFragment;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.fenazola.mxcome.widget.PayDialog;
import com.fenazola.mxcome.widget.PwdDialog;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonTextWidget;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by XDONE on 2017/4/12.
 * 提现
 */

public class AdvanceFragment extends BaseFragment {
    private EditText editText;
    private TextView accout_balance;
    private TextView next;
    private CommonTextWidget paytype;
    private CommonTextWidget tWidget;
    private PwdDialog dialog;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_account_advance;
    }

    @Override
    public void initView() {
        super.initView();
        editText = (EditText) findViewById(R.id.editText);
        accout_balance = (TextView) findViewById(R.id.accout_balance);
        next = (TextView) findViewById(R.id.next);
        paytype = (CommonTextWidget) findViewById(R.id.tWidget);
        tWidget = (CommonTextWidget) findViewById(R.id.tWidget);

        Utils.setEditTextSize(getActivity(), editText, "请输入提现的金额");

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        reqData();

        UserEntry entry = Utils.getUserEntry();
        boolean hasPayPwd = entry.isHasPayPwd();
        if (!hasPayPwd) {
            CommonDialog dialog = new CommonDialog(getActivity());
            dialog.setTitle("安全提示");
            dialog.setContentText("为了您的资金安全，请设置支付密码!");

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });
            dialog.setOnClickCancelListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backStackFragment();
                }
            });
            dialog.setOnClickConfirmListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SetPasswordFragment fragment = new SetPasswordFragment();
                    addFragment(fragment);
                }
            });
            dialog.show();
        }


        tWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayDialog();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    next.setTextColor(getColor(R.color.colorGrey));
                    next.setBackgroundResource(R.drawable.rect_shape_grey_btn);
                    next.setText("下一步");
                    next.setOnClickListener(null);
                } else {
                    next.setTextColor(getColor(R.color.white));
                    next.setBackgroundResource(R.drawable.rect_shape_blue_btn);
                    next.setText("两小时内到账，确认提现");
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goaliAuth("1");
            }
        });
    }

    private void goaliAuth(final String paytype) {
        HashMap<String, String> map = new HashMap<>();
        NetWorkUtils.post(getActivity(), "mxpayapi/aliAuth.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject res = resObj.optJSONObject("res");
                final String authInfo = res.optString("authInfo");
                AuthUtils.goAuth(getActivity(), authInfo, new AuthUtils.OnAuthListener() {
                    @Override
                    public void onResult(AuthResult auth) {
                        LogUtils.i("paytype---------", paytype);
                        if (auth.getResultStatus().equals("9000")) {
                            goSuc(auth, paytype);
                        }
                    }
                });
            }

            @Override
            public void onError(String result, String code, String msg) {

            }

        });
    }

    public void goSuc(AuthResult authResult, final String paytype) {
        HashMap<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        map.put("mxcome_no", user.getMxcome_no());
        map.put("auth_code", authResult.getAuthCode());
        NetWorkUtils.post(getActivity(), "mxpayapi/aliAuthGetUserInfo.do", map, false, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject obj = resObj.optJSONObject("res");
                final String user_id = obj.optString("user_id");
                gotx(user_id, paytype);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public void gotx(final String user_id, final String paytype) {
        HashMap<String, String> map = new HashMap<>();
        NetWorkUtils.post(getActivity(), "hrpc/getServerTime.do", map, false, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject obj = resObj.optJSONObject("res");
                final String timestamp = obj.optString("timestamp");
                dialog = new PwdDialog(getActivity(), new PwdDialog.OnPwdListener() {
                    @Override
                    public void onTextChanged(String mStrPwd) {
                        if (mStrPwd.length() == 6) {
                            goAdvance(timestamp, user_id, mStrPwd, paytype);
                            LogUtils.i("mStrPwd---------------------", mStrPwd);
                        }
                    }

                    @Override
                    public void refreshActivity() {

                    }
                });
                dialog.setTitle("转出" + editText.getText().toString() + "元");
                dialog.show();
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    private void goAdvance(String timestamp, String user_id, String mStrPwd, String paytype) {
        HashMap<String, String> map = new HashMap<>();
        UserEntry entry = Utils.getUserEntry();
        String mxcome_no = entry.getMxcome_no();
        String amount = editText.getText().toString();
        map.put("mxcome_no", mxcome_no);
        map.put("amount", amount);
        map.put("pay_account_name", user_id);
        map.put("paytype", paytype);
        map.put("timestamp", timestamp);
        map.put("cash_pwd", mStrPwd);
        String sign = EncryptUtil.AesEncrypt(new String(mxcome_no + amount + user_id + paytype + timestamp + mStrPwd));
        map.put("sign", sign);
        NetWorkUtils.post(getActivity(), "finance/cashForCustomer.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                dialog.dismiss();
                backStackFragment();
                CommonToastUtils.showInCenterToast(getActivity(), "提现成功");
                reqData();
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    private void showPayDialog() {
        PayDialog dialog = new PayDialog(getActivity(), new PayDialog.OnSelectListener() {
            @Override
            public void onSelect(PayDialog.SelectPayMode mode) {
                LogUtils.i("mode--------", mode + "");
                switch (mode) {
                    case ALIPAY:
                        tWidget.setLeftText("支付宝");
                        tWidget.setLeftImageResource(R.mipmap.pay_alipay);
                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!TextUtils.isEmpty(editText.getText().toString())) {
                                    goaliAuth("1");
                                } else {
                                    CommonToastUtils.showInCenterToast(getActivity(), "请输入金额");

                                }
                            }
                        });
                        break;
                    case WECHAT:
                        tWidget.setLeftText("微信");
                        tWidget.setLeftImageResource(R.mipmap.pay_wechat);
                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!TextUtils.isEmpty(editText.getText().toString())) {
                                    goaliAuth("2");
                                } else {
                                    CommonToastUtils.showInCenterToast(getActivity(), "请输入金额");
                                }
                            }
                        });
                        break;
                }
            }
        });
        dialog.setlistRow2Visible();
        dialog.show();
    }

    private void reqData() {
        HashMap<String, String> map = new HashMap<>();
        UserEntry entry = Utils.getUserEntry();
        String mxcome_no = entry.getMxcome_no();
        map.put("mxcome_no", mxcome_no);
        NetWorkUtils.post(getActivity(), "/hrpc/getAppClientRecharge.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject obj = resObj.optJSONObject("res");
                String acount = obj.optString("acount");
                LogUtils.i("------------", acount);
                if (acount != null) {
                    accout_balance.setText("可用余额" + acount + "元");
                } else {
                    accout_balance.setText("可以余额0元");
                }
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setRightText("常见问题   ");
        toolbar.setTitle("提现");
    }
}
