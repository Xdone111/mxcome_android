package com.fenazola.mxcome.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.fenazola.iframe.BaseApp;
import com.fenazola.mxcome.MainActivity;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.wechat.WechatEntry;
import com.fenazola.mxcome.fragment.me.BindWechatReadyFragment;
import com.fenazola.mxcome.fragment.me.DemandWechat;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.UMShareAPI;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.widget.CommonDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zm on 2017/3/3.
 * 处理微信回调
 */
public class WXEntryActivity extends Activity /**WXCallbackActivity*/ implements IWXAPIEventHandler {
    CommonDialog mDialog;
    private IWXAPI api;
    private BaseResp resp = null;
    private String WX_APP_ID = BaseApp.WX_SHARE_APPID;
    // 获取第一步的code后，请求以下链接获取access_token
    private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 获取用户个人信息
    private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
    private String WX_APP_SECRET = BaseApp.WX_SHARE_SECRET;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);//完成回调
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WX_APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        String result = "";
        if (resp != null) {
            resp = resp;
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "发送成功";
                //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                String code = ((SendAuth.Resp) resp).code;

            /*
             * 将你前面得到的AppID、AppSecret、code，拼接成URL 获取access_token等等的信息(微信)
             * 因为微信返回的和后台的返回的不一样  然后 也懒得重写公有方法了
             * 所以这里面的授权认证 把她写在onError中接收 授权返回字段和解析文本匹配则继续
             * 否则 也解析不了
             */
                String get_access_token = getCodeRequest(code);
                NetWorkUtils.getWechatUrl(this, get_access_token,  false, new NetWorkUtils.IListener() {
                    @Override
                    public void onSuccess(String result, JSONObject response) {
                        LogUtils.i("XHX","get_user_info_url111:"+result);
                        try {//获取token正常的时候  开头是 access_token  就可以进行一下步骤了 其他 对不起 重新授权
                            if (!result.equals("")&&result.startsWith("{\"access_token\"")) {
                                //JSONObject response=new JSONObject(result);
                                String access_token = response
                                        .getString("access_token");
                                String openid = response.getString("openid");
                                //一下部分代码是否需要？根据openid获取详情的？我是觉得不需要  获取到 openid就行了
                                //机智如我
                                String get_user_info_url = getUserInfo(
                                        access_token, openid);
                                isLoginOrRegr(access_token,openid,get_user_info_url);
                                //getUserInfo(get_user_info_url);

                            }else{
                                initAngen();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(String result, String code, String msg) {

                        initAngen();

                    }
                });
                //finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //result = "发送取消";
                //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //result = "发送被拒绝";
                //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                //result = "发送返回";
                //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
    /**重新授权 当然 你也可以选择放弃*/
    private void initAngen() {
        finish();

        Toast.makeText(WXEntryActivity.this,"微信授权失败，请重新授权！",Toast.LENGTH_SHORT).show();
//        DemandWechat dialog5 = new DemandWechat(WXEntryActivity.this);
//        dialog5.show();
    }

    /**
     * 通过拼接的用户信息url获取用户信息
     * 因为微信返回的和后台的返回的不一样  然后 也懒得重写公有方法了
     * 所以这里面的授权认证 把她写在onError中接收 授权返回字段和解析文本匹配则继续
     * 否则 也解析不了
     * @param user_info_url
     */
    private void getUserInfo(String user_info_url) {
        NetWorkUtils.getWechatUrl(this, user_info_url,  false, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject response) {
                System.out.println("获取用户信息:" + result);
                if (!result.equals("")&&result.startsWith("{\"openid\"")) {//如果返回了正常信息 简单呀 直接去让用户绑定微信呀
                    WechatEntry entry=GsonUtils.getObjFromJSON(result,WechatEntry.class);
                    Intent intent = new Intent(WXEntryActivity.this, ZFrameActivity.class);
                    intent.putExtra(Constant.key1,entry);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, BindWechatReadyFragment.class.getName());
                    startActivity(intent);
                    finish();

                }else{
                    initAngen();
                }

            }

            @Override
            public void onError(String result, String code, String msg) {
                initAngen();
            }
        });

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    /**
     * 获取access_token的URL（微信）
     *
     * @param code
     *            授权时，微信回调给的
     * @return URL
     */
    private String getCodeRequest(String code) {
        String result = null;
        GetCodeRequest = GetCodeRequest.replace("APPID",
                urlEnodeUTF8(WX_APP_ID));
        GetCodeRequest = GetCodeRequest.replace("SECRET",
                urlEnodeUTF8(WX_APP_SECRET));
        GetCodeRequest = GetCodeRequest.replace("CODE", urlEnodeUTF8(code));
        result = GetCodeRequest;
        return result;
    }

    /**
     * 获取用户个人信息的URL（微信）
     *
     * @param access_token
     *            获取access_token时给的
     * @param openid
     *            获取access_token时给的
     * @return URL
     */
    private String getUserInfo(String access_token, String openid) {
        String result = null;
        GetUserInfo = GetUserInfo.replace("ACCESS_TOKEN",
                urlEnodeUTF8(access_token));
        GetUserInfo = GetUserInfo.replace("OPENID", urlEnodeUTF8(openid));
        result = GetUserInfo;
        return result;
    }

    private String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 判定是走微信登录呢
     * 还是微信绑定
     * 绑定了微信 直接登录
     * @param get_user_info_url 请求用户详情
     * @param access_token 校验码
     * @param openid 微信唯一ID
     */
    public void isLoginOrRegr(final String access_token,final String openid,final String get_user_info_url) {
        Map<String, String> map = new HashMap<>();
        map.put("wx_openid",openid);
        //TODO Constant.newBaseUrl
        //String urls="http://119.23.44.84:9685/decora/";
        NetWorkUtils.postUrl(this, Constant.newBaseUrl+"weixin/checkBinding",  map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject response) {//直接登录
                SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
                prefUtils.put("isLogin", true);
                String res = response.optString("res");
                List<UserEntry> list = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<UserEntry>>() {
                }.getType());
                UserEntry entry = list.get(0);
                prefUtils.put("user", entry);
                EventBus.getDefault().post(entry);
//                Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String result, String code, String msg) {
                if(!(result==null)&&result.startsWith("{\"return_code\":0")) {
                    getUserInfo(get_user_info_url);
                }

            }
        });


    }




    @Override
    protected void onDestroy() {
        if(mDialog != null) {
            mDialog.dismiss();
        }
        super.onDestroy();
    }
}
