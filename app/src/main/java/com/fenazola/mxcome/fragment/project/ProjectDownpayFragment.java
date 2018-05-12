package com.fenazola.mxcome.fragment.project;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fenazola.iframe.alipay.AliPayUtils;
import com.fenazola.iframe.alipay.PayResult;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.project.OrderEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.utils.encry.EncryptUtil;
import com.fenazola.mxcome.widget.PayDialog;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.StringUtils;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonWhiteDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zm on 2017/3/3.
 * 支付定金
 */
public class ProjectDownpayFragment extends BaseFragment {

    private TextView floor_name;
    private TextView pay_amt;
    private TextView timeout;
    private TextView discount_amt;
    private TextView total_amt;
    private TextView order_no;
    private TextView pay;
    private OrderEntry order;
    private PayDialog dialog;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_project_downpay;
    }

    @Override
    public void initView() {
        super.initView();
        floor_name = (TextView) findViewById(R.id.floor_name);
        pay_amt = (TextView) findViewById(R.id.pay_amt);
        timeout = (TextView) findViewById(R.id.timeout);
        discount_amt = (TextView) findViewById(R.id.discount_amt);
        total_amt = (TextView) findViewById(R.id.total_amt);
        order_no = (TextView) findViewById(R.id.order_no);
        pay = (TextView) findViewById(R.id.pay);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            order = (OrderEntry) args.getSerializable(Constant.key1);
            floor_name.setText(order.getHouseInfo());
            order_no.setText(getString(R.string.project_order_no, order.getMx_order_no()));
            total_amt.setText("¥" + order.getTotal_amount());
            discount_amt.setText("¥" + order.getDis_amount());
            pay_amt.setText("¥" + "200");
            setTimeout();
        }
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPayDialog();
            }
        });
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
                    dialog.setAccBal(acount);
                    int data = acount.compareTo(order.getTotal_amount());
                    if (data < 0) {
                        dialog.setBgColor(getColor(R.color.text_hint_color));
                    }
                } else {
                    dialog.setAccBal("0");
                }
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public void showPayDialog() {
        dialog = new PayDialog(getActivity(), new PayDialog.OnSelectListener() {
            @Override
            public void onSelect(PayDialog.SelectPayMode mode) {
                LogUtils.i("---zss---", "------mode " + mode);
                if (mode == PayDialog.SelectPayMode.ALIPAY) {
                    getServiceTime("1");
                } else {
                    getServiceTime("2");
                }
            }
        });
        dialog.setMeBao("0");
        reqData();
        dialog.show();
    }

    public void getServiceTime(final String paytype) {
        HashMap<String, String> map = new HashMap<>();
        NetWorkUtils.post(getActivity(), "hrpc/getServerTime.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject obj = resObj.optJSONObject("res");
                orderPrePay("0.01", "支付定金", paytype, obj.optString("timestamp"));
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public void orderPrePay(String amount, String title, String paytype, String timestamp) {
        UserEntry user = Utils.getUserEntry();
        HashMap<String, String> map = new HashMap<>();
        map.put("user_id", user.getUser_id());
        map.put("order_no", order.getMx_order_no());
        map.put("amount", amount);
        map.put("title", title);
        map.put("paytype", paytype); //1支付宝 2微信 3网银
        map.put("timestamp", timestamp);
        String sign = EncryptUtil.AesEncrypt(new String(order.getMx_order_no() + amount + title + paytype + timestamp));
        map.put("sign", sign);
        NetWorkUtils.post(getActivity(), "hrpc/orderOnPrePay.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject obj = resObj.optJSONObject("res");
                String orderInfo = obj.optString("orderInfo");
                AliPayUtils.goPay(getActivity(), orderInfo, new AliPayUtils.OnPayListener() {
                    @Override
                    public void onResult(PayResult result) {
                        String resultStatus = result.getResultStatus();
                        final String memo = result.getMemo();
                        LogUtils.i("---zss---", "------result = " + result.toString());
                        if ("9000".equals(resultStatus)) {
                            LogUtils.i("---zss---", "------result-----支付成功");
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showPaySucDialog();
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

    public void showPaySucDialog() {
        final CommonWhiteDialog dialog = new CommonWhiteDialog(getActivity());
        dialog.setTitle("支付成功");
        dialog.setMiddleView(R.layout.dialog_pay_suc_center);
        dialog.setBootomView(R.layout.dialog_pay_suc_bottom);
        View tv_share = dialog.findViewById(R.id.tv_share);
        tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(order);
                dialog.dismiss();
            }
        });
        View tv_detail = dialog.findViewById(R.id.tv_detail);
        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        View tv_rule = dialog.findViewById(R.id.tv_rule);
        tv_rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getActivity().finish();
            }
        });
        dialog.show();
    }

    public void setTimeout() {
        long time = order.getTimeout() * 60 * 1000;
        String timefromServer = order.getOrdertime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date serverDate = df.parse(timefromServer);
            long duringTime = new Date().getTime() - serverDate.getTime();
            countdownTime = time - duringTime;
            handler.postDelayed(runnable, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long countdownTime;
    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            countdownTime -= 1000;//倒计时总时间减1
            SimpleDateFormat foramt = new SimpleDateFormat("mm:ss");
            String ms = foramt.format(countdownTime);
            String timeStr = getString(R.string.project_timeout, ms);
            timeout.setText(StringUtils.parse(timeStr, 2, timeStr.length() - 5, getColor(R.color.colorRed)));
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle(getString(R.string.project_pay_downpay));
    }

}
