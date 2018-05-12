package com.fenazola.iframe.alipay;

import android.app.Activity;

import com.alipay.sdk.app.AuthTask;
import com.fenazola.iframe.BaseApp;

import java.util.Map;

/**
 * Created by zm on 2017/5/25.
 */

public class AuthUtils {

    public static void goAuth(final Activity mContext, final OnAuthListener listener){
        boolean rsa2 = false;
        Map<String, String> authInfoMap = OrderUtils.buildAuthInfoMap(BaseApp.ALIPAY_PID, BaseApp.ALIPAY_APPID, BaseApp.ALIPAY_TARGET_ID, rsa2);
        String info = OrderUtils.buildOrderParam(authInfoMap);
        String privateKey = rsa2 ? BaseApp.ALIPAY_RSA2_PRIVATE: BaseApp.ALIPAY_RSA_PRIVATE;
        String sign = OrderUtils.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(mContext);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);
                if(listener!=null){
                    listener.onResult(new AuthResult(result, true));
                }
            }
        };
        Thread payThread = new Thread(authRunnable);
        payThread.start();
    }

    public static void goAuth(final Activity mContext, final String authInfo, final OnAuthListener listener){
        Runnable authRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(mContext);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);
                if(listener!=null){
                    listener.onResult(new AuthResult(result, true));
                }
            }
        };
        Thread payThread = new Thread(authRunnable);
        payThread.start();
    }


    public interface OnAuthListener{
        public void onResult(AuthResult auth);
    }
}
