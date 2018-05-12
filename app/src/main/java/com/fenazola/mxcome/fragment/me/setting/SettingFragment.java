package com.fenazola.mxcome.fragment.me.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.MainActivity;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.LocationEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.ShareUtils;
import com.fenazola.mxcome.utils.Utils;
import com.igexin.sdk.PushManager;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by XDONE on 2017/3/8.
 * 设置中心
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout linearLayout1, linearLayout2, linearLayout3, linearLayout4;
    private TextView exitText;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView() {
        super.initView();
        linearLayout1 = (LinearLayout) findViewById(R.id.ll_push_switch);
        linearLayout2 = (LinearLayout) findViewById(R.id.ll_update_clear);
        linearLayout3 = (LinearLayout) findViewById(R.id.ll_share_favorable);
        linearLayout4 = (LinearLayout) findViewById(R.id.ll_about_soft);
        exitText = (TextView) findViewById(R.id.exit);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
        exitText.setOnClickListener(this);
        if (Utils.isLogin()) {
            exitText.setVisibility(View.VISIBLE);
        } else {
            exitText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle(getString(R.string.setting));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_push_switch:
                PushSwitchFragment fragment1 = new PushSwitchFragment();
                addFragment(fragment1);
                break;
            case R.id.ll_update_clear:
                UpdateAndClearFragment fragment2 = new UpdateAndClearFragment();
                addFragment(fragment2);
                break;
            case R.id.ll_share_favorable:
                //ShareUtils.showShareDialog(getActivity(), "分享了", "www.baidu.com","分享领优惠","我刚刚在这里领取到了免费装修，棒棒哒！");
                ShareUtils.showShareDialog(getActivity(), "分享到", "MXCOME TEST!");
                break;

            case R.id.ll_about_soft:
                AboutSoftFragment fragment3 = new AboutSoftFragment();
                addFragment(fragment3);
                break;
            case R.id.exit:
                exit();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void exit() {
        HashMap<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        LocationEntry location = Utils.getLocationEntry();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String mxcome_no = user.getMxcome_no();
        map.put("user_cid", PushManager.getInstance().getClientid(getContext()));
        map.put("mxcome_no", mxcome_no);
        map.put("user_id", user.getUser_id());
        String url = Constant.newBaseUrl + "authmodule/logout.do";
        NetWorkUtils.postUrl(getActivity(), url, map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                LogUtils.i("XHX", "成功：" + result);
                Utils.saveUserEntry(null);
                EventBus.getDefault().post(new UserEntry());
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onError(String result, String code, String msg) {
                LogUtils.i("XHX", "失败：" + result + ";" + code + ";" + msg);

            }
        });
    }
}

