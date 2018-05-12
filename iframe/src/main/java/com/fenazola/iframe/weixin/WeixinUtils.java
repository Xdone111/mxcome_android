package com.fenazola.iframe.weixin;

import android.app.Activity;

import com.fenazola.iframe.BaseApp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONObject;

/**
 * Created by zm on 2017/3/3.
 * 微信支付调用
 */

public class WeixinUtils {

    public static void goPay(Activity mContext, JSONObject json){
        IWXAPI api = WXAPIFactory.createWXAPI(mContext, BaseApp.WX_PAY_APPID);
        PayReq req = new PayReq();
        req.appId = json.optString("appid");
        req.partnerId = json.optString("partnerid");
        req.prepayId = json.optString("prepayid");
        req.nonceStr = json.optString("noncestr");
        req.timeStamp = json.optString("timestamp");
        req.packageValue = json.optString("package");
        req.sign = json.optString("sign");
        req.extData	= "app data"; // optional
        api.sendReq(req);
    }
    public static void toLogin(Activity mContext, JSONObject json){
        IWXAPI api = WXAPIFactory.createWXAPI(mContext, BaseApp.WX_PAY_APPID);
        api.registerApp(BaseApp.WX_PAY_APPID);
        SendAuth.Req req = new SendAuth.Req();
        req. scope = "snsapi_userinfo";
        req. state = "wechat_sdk_demo_test";
        api.sendReq(req);
    }
}
