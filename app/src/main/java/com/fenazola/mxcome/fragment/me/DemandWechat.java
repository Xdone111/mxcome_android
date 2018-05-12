package com.fenazola.mxcome.fragment.me;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.fenazola.iframe.BaseApp;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.main.demand.DemandReward;
import com.fenazola.mxcome.wxapi.WXEntryActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 重新授权微信
 */
public class DemandWechat extends Dialog implements View.OnClickListener {

    private TextView orderReward, orderFinsh;
    Activity mContext;

    public DemandWechat(Activity context) {
        super(context, com.zss.library.R.style.CommonDialog);
        setContentView(R.layout.dialog_textview);
       // view = LayoutInflater.from(getOwnerActivity()).inflate(R.layout.fragment_demand_design, null);
        this.mContext=context;
        int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, width);
        initView();
        initData();
    }
    public void initView() {
        orderReward = (TextView)  findViewById(R.id.order_reward);
        orderFinsh = (TextView)  findViewById(R.id.order_cosos);
        orderReward.setOnClickListener(this);
        orderFinsh.setOnClickListener(this);

    }


    public void initData() {


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_reward:
                dismiss();
                break;
            case R.id.order_cosos:
                WXLogin();
                dismiss();
                break;

        }
    }
    /**
     * 登录微信
     */
    public void WXLogin() {
        IWXAPI api = WXAPIFactory.createWXAPI(mContext, BaseApp.WX_SHARE_APPID, true);
        api.registerApp(BaseApp.WX_SHARE_APPID);
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo";
        api.sendReq(req);
    }

}
