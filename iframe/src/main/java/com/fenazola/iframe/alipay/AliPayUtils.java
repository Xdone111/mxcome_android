package com.fenazola.iframe.alipay;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;
import com.fenazola.iframe.BaseApp;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by zm on 2017/3/3.
 * 支付宝支付调用
 */

public class AliPayUtils {

    public static void goLocalPay(final Activity mContext, Map<String, String> map, final OnPayListener listener){
        boolean rsa2 = (BaseApp.ALIPAY_RSA2_PRIVATE.length() > 0);
        String orderParam = OrderUtils.buildOrderParam(map);
        String privateKey = rsa2 ? BaseApp.ALIPAY_RSA2_PRIVATE : BaseApp.ALIPAY_RSA_PRIVATE;
        String sign = OrderUtils.getSign(map, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                if (listener!=null){
                    listener.onResult(new PayResult(result));
                }
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public static void goPay(final Activity mContext, final String orderInfo, final OnPayListener listener){
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                if (listener!=null){
                    listener.onResult(new PayResult(result));
                }
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    public interface OnPayListener{
        public void onResult(PayResult result);
    }
}
