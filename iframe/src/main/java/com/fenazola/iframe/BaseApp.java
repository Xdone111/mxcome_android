package com.fenazola.iframe;

import android.app.Application;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.fenazola.iframe.getui.DemoIntentService;
import com.fenazola.iframe.getui.DemoPushService;
import com.igexin.sdk.PushManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zm on 2017/3/3.
 * 初始化集成框架中的数据
 */

public class BaseApp extends Application {

    public static String WX_SHARE_APPID = "wxc415969498bfa246";
    public static String WX_SHARE_SECRET = "1dd28c26f5f6e59083764e126e337d5d";

    public static String WX_PAY_APPID = "wxc415969498bfa246";

    public static String QQ_APPID = "1105959951";
    public static String QQ_APPKEY = "xlmPfOeQmgABqPJi";

    public static String ALIPAY_PID = "";
    public static String ALIPAY_APPID = "";
    public static String ALIPAY_TARGET_ID = "";
    public static String ALIPAY_RSA2_PRIVATE = "";
    public static String ALIPAY_RSA_PRIVATE = "";

    public static String SINA_APP_KEY = "1711853421";
    public static String SINA_APP_SECRET = "c431ed18fe2f9ebf535bcfbcde81b46f";


    @Override
    public void onCreate() {
        super.onCreate();

        //百度
        SDKInitializer.initialize(this);

        //友盟分享
        Config.DEBUG = true;
        PlatformConfig.setWeixin(WX_SHARE_APPID, WX_SHARE_SECRET);
        PlatformConfig.setQQZone(QQ_APPID, QQ_APPKEY);
        PlatformConfig.setSinaWeibo(SINA_APP_KEY, SINA_APP_SECRET, "http://www.mxcome.com");

        //极光推送
//        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
//        JPushInterface.init(this);     		// 初始化 JPush

        //微信支付
        IWXAPI msgApi = WXAPIFactory.createWXAPI(this, WX_PAY_APPID);
        msgApi.registerApp(WX_PAY_APPID);

        //个推
        PushManager.getInstance().initialize(this, DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this, DemoIntentService.class);

        Log.i("test", "--------getui-------" + PushManager.getInstance().getClientid(this));
    }
}
