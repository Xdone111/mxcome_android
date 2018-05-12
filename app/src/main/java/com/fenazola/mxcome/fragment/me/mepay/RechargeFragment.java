package com.fenazola.mxcome.fragment.me.mepay;

import android.content.Intent;
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
import android.widget.Toast;

import com.fenazola.iframe.alipay.AliPayUtils;
import com.fenazola.iframe.alipay.PayResult;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.fragment.me.IdentifyFragment;
import com.fenazola.mxcome.fragment.me.account.LimitDescriptionFragment;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.fenazola.mxcome.widget.PayDialog;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.widget.CommonTextWidget;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by XDONE on 2017/4/7.
 * 充值
 */

public class RechargeFragment extends BaseFragment {

    private EditText editText;
    private TextView next;
    private CommonTextWidget tWidget;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mepay_recharge;
    }

    @Override
    public void initView() {
        super.initView();
        editText = (EditText) findViewById(R.id.editText);
        next = (TextView) findViewById(R.id.next);
        tWidget = (CommonTextWidget) findViewById(R.id.tWidget);

        Utils.setEditTextSize(getActivity(), editText, "请输入充值金额");
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    getServiceTime("1");
                } else {
                    Toast.makeText(getActivity(), "请输入金额", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                    next.setOnClickListener(null);
                } else {
                    next.setTextColor(getColor(R.color.white));
                    next.setBackgroundResource(R.drawable.rect_shape_blue_btn);
                }
            }
        });
    }

    private void getServiceTime(final String paytype) {
        HashMap<String, String> map = new HashMap<>();
        NetWorkUtils.post(getActivity(), "hrpc/getServerTime.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject obj = resObj.optJSONObject("res");
                String timestamp = obj.optString("timestamp");
                if (!TextUtils.isEmpty(editText.getText().toString())) {
                    goPay(timestamp, paytype);
                }
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
                                    getServiceTime("1");
                                } else {
                                    Toast.makeText(getActivity(), "请输入金额", Toast.LENGTH_SHORT).show();
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
                                    getServiceTime("1");
                                } else {
                                    Toast.makeText(getActivity(), "请输入金额", Toast.LENGTH_SHORT).show();
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

    public void goPay(String timestamp, String paytype) {
        HashMap<String, String> map = new HashMap<String, String>();
        UserEntry entry = Utils.getUserEntry();
        String mxcome_no = entry.getMxcome_no();
        String title = "充值";
        String amount = editText.getText().toString();
        map.put("mxcome_no", mxcome_no);
        map.put("amount", amount);
        map.put("title", title);
        map.put("paytype", paytype);
        map.put("timestamp", timestamp);
        String sign = EncryptUtil.AesEncrypt(new String(mxcome_no + amount + title + paytype + timestamp));
        map.put("sign", sign);
        NetWorkUtils.post(getActivity(), "finance/rechargeForCustomer.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject obj = resObj.optJSONObject("res");
                String orderInfo = obj.optString("orderInfo");
                AliPayUtils.goPay(getActivity(), orderInfo, new AliPayUtils.OnPayListener() {
                    @Override
                    public void onResult(PayResult result) {
                        LogUtils.i("---zss---", "------result = " + result.toString());
                        String resultStatus = result.getResultStatus();
                        final String memo = result.getMemo();
                        if ("9000".equals(resultStatus)) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CommonToastUtils.showInCenterToast(getActivity(), "支付成功");
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    CommonToastUtils.showInCenterToast(getActivity(), memo);
                                }
                            });
                        }
                    }
                });
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
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("充值");
        toolbar.setRightText("限额说明   ");
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, LimitDescriptionFragment.class.getName());
                startActivity(intent);
            }
        });
    }
}
