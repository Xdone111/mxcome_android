package com.fenazola.iframe.umeng;

import android.app.Activity;
import android.graphics.Bitmap;
import android.provider.SyncStateContract;
import android.widget.Toast;

import com.fenazola.iframe.BaseApp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by zm on 2017/3/3.
 * 友盟分享调用
 */

public class UmengUtils {

    public static void shareText(Activity mContext, String text, SHARE_MEDIA media, UMShareListener listener) {
        new ShareAction(mContext).setPlatform(media).setCallback(listener).withText(text).share();
    }

    public static void shareImage(Activity mContext, String image, String thumb, String text, SHARE_MEDIA media, UMShareListener listener){
        UMImage pic = new UMImage(mContext,image);
        pic.setThumb(new UMImage(mContext,thumb));
        new ShareAction(mContext).withMedia(pic).setPlatform(media).setCallback(listener).withText(text).share();
    }

    public static void shareWeb(Activity mContext, int resid, String text, SHARE_MEDIA media, UMShareListener listener){
        UMImage thumb = new UMImage(mContext, resid);
        UMWeb web = new UMWeb(text);
        web.setThumb(thumb);
        web.setDescription("测试分享");
        web.setTitle("分享");
        new ShareAction(mContext).withMedia(web).setPlatform(media).setCallback(listener).withText("dd").share();
    }

    public static void shareWeb(Activity mContext, int resid,String webTitle,String webDesc,  String text, SHARE_MEDIA media, UMShareListener listener){

        if(media.equals(SHARE_MEDIA.WEIXIN)){
            weiChat(mContext,0,webTitle,webDesc,text);
        }else if(media.equals(SHARE_MEDIA.WEIXIN_CIRCLE)){
            weiChat(mContext,1,webTitle,webDesc,text);
        }else{
            UMImage thumb = new UMImage(mContext, resid);
            UMWeb web = new UMWeb(text);
            web.setThumb(thumb);
            web.setDescription(webDesc);
            web.setTitle(webTitle);
            new ShareAction(mContext).withMedia(web).setPlatform(media).setCallback(listener).withText("dd").share();
        }

    }
    // 0-分享给朋友  1-分享到朋友圈
    public static  void weiChat(Activity mContext,int flag,String webTitle,String webDesc,  String text) {

        IWXAPI WXWXapi = WXAPIFactory.createWXAPI(mContext, BaseApp.WX_SHARE_APPID, true);
        WXWXapi.registerApp(BaseApp.WX_SHARE_APPID);
        if (!WXWXapi.isWXAppInstalled()) {
            Toast.makeText(mContext,"您还未安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        //创建一个WXWebPageObject对象，用于封装要发送的Url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = text;
        //创建一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = webTitle;
        msg.description = webDesc;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());//transaction字段用于唯一标识一个请求，这个必须有，否则会出错
        req.message = msg;
        //表示发送给朋友圈  WXSceneTimeline  表示发送给朋友  WXSceneSession
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        WXWXapi.sendReq(req);
    }
}
