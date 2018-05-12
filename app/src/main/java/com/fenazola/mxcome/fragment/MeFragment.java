package com.fenazola.mxcome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.WebViewCjActivity;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.fragment.me.Discount1Fragment;
import com.fenazola.mxcome.fragment.me.DiscountFragment;
import com.fenazola.mxcome.fragment.me.FeedbackFragment;
import com.fenazola.mxcome.fragment.me.IdentifyFragment;
import com.fenazola.mxcome.fragment.me.LoginFragment;
import com.fenazola.mxcome.fragment.me.LoginReadyFragment;
import com.fenazola.mxcome.fragment.me.MyCollectionFragment;
import com.fenazola.mxcome.fragment.me.PersonInfoFragment;
import com.fenazola.mxcome.fragment.me.account.AccountFragment;
import com.fenazola.mxcome.fragment.me.collaboration.OnlineCollaborationFragment;
import com.fenazola.mxcome.fragment.me.collaboration.OnlineCollaborationStepNextFragment;
import com.fenazola.mxcome.fragment.me.help.HelpFragment;
import com.fenazola.mxcome.fragment.me.loan.LoanFragment;
import com.fenazola.mxcome.fragment.me.mepay.MePayFragment;
import com.fenazola.mxcome.fragment.me.myproject.MyProjectOrderFragment;
import com.fenazola.mxcome.fragment.me.safe.SecurityCenterFragment;
import com.fenazola.mxcome.fragment.project.MaterialFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.widget.CommonTextWidget;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * ME
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private CommonTextWidget widget1, widget2, widget3, widget4, widget5, widget6;

    private CommonTextWidget widget7, widget8, widget9, widget10, widget11, widget12;

    private LinearLayout read_layout;

    private CircleImageView photo;

    private TextView name, sign;
    private TextView identify;
    private ImageView toDiscountIv;
    private ImageView topic;
    private String acount;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView() {
        super.initView();
        toDiscountIv = (ImageView) findViewById(R.id.chat);
        widget1 = (CommonTextWidget) findViewById(R.id.widget1);
        widget2 = (CommonTextWidget) findViewById(R.id.widget2);
        widget3 = (CommonTextWidget) findViewById(R.id.widget3);
        widget4 = (CommonTextWidget) findViewById(R.id.widget4);
        widget5 = (CommonTextWidget) findViewById(R.id.widget5);
        widget6 = (CommonTextWidget) findViewById(R.id.widget6);
        widget7 = (CommonTextWidget) findViewById(R.id.widget7);
        widget8 = (CommonTextWidget) findViewById(R.id.widget8);
        widget9 = (CommonTextWidget) findViewById(R.id.widget9);
        widget10 = (CommonTextWidget) findViewById(R.id.widget10);
        widget11 = (CommonTextWidget) findViewById(R.id.widget11);
        widget12 = (CommonTextWidget) findViewById(R.id.widget12);
        topic = (ImageView) findViewById(R.id.topic);
        //read_layout = (LinearLayout) findViewById(R.id.read_layout);
        photo = (CircleImageView) findViewById(R.id.photo);
        name = (TextView) findViewById(R.id.name);
        sign = (TextView) findViewById(R.id.sign);
        identify = (TextView) findViewById(R.id.identify);
        topic.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        EventBus.getDefault().register(this);

        widget1.setLeftText("账户");
        widget2.setLeftText("ME宝");
        widget3.setLeftText("贷款");
        widget4.setLeftText("优惠");
        widget5.setLeftText("收藏");
        widget6.setLeftText("订单");
        widget7.setLeftText("我的工程");
        widget8.setLeftText("我的售后");
        widget9.setLeftText("我要合作");
        widget10.setLeftText("帮助");
        widget11.setLeftText("安全中心");
        widget12.setLeftText("意见反馈");
        widget12.setRightText("提建议，任性赠券");
        widget12.setRightTextColor(getResources().getColor(R.color.colorGrey));
        widget1.setOnClickListener(this);
        widget2.setOnClickListener(this);
        widget3.setOnClickListener(this);
        widget4.setOnClickListener(this);
        widget5.setOnClickListener(this);
        widget6.setOnClickListener(this);
        widget7.setOnClickListener(this);
        widget8.setOnClickListener(this);
        widget9.setOnClickListener(this);
        widget10.setOnClickListener(this);
        widget11.setOnClickListener(this);
        widget12.setOnClickListener(this);
        identify.setOnClickListener(this);
        toDiscountIv.setOnClickListener(this);
        refreshUI();

    }

    public void refreshUI() {
        if (Utils.isLogin()) {
            UserEntry entry = Utils.getUserEntry();
            name.setText(entry.getUser_name());
            sign.setText(entry.getMxcome_no());
            sign.setTextColor(getColor(R.color.colorBlue));
            sign.setVisibility(View.GONE);
            name.setTextColor(getColor(R.color.grey));
            photo.setOnClickListener(this);
            if (!TextUtils.isEmpty(entry.getPic())) {
                Glide.with(getActivity()).load(Constant.imageUrl + entry.getPic()).into(photo);
            } else {
                photo.setImageResource(R.mipmap.me_photo);
            }
            reqData();
            widget2.setRightText(Html.fromHtml("余<font color='#4BB7FD'>￥" + 10000 + "</font>元"));
        } else {
            name.setText("注册登录");
            name.setTextColor(getColor(R.color.colorBlue));
            name.setOnClickListener(this);
            sign.setText("访客模式");
            sign.setTextColor(getColor(R.color.colorDisabled));
            photo.setImageResource(R.mipmap.me_not_login);
            photo.setOnClickListener(this);
            widget1.setRightText("");
            widget2.setRightText("");
        }
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
                acount = obj.optString("acount");
                if (!TextUtils.isEmpty(acount)) {
                    widget1.setRightText(Html.fromHtml("余<font color='#4BB7FD'>￥" + acount + "</font>元"));
                } else {
                    widget1.setRightText(Html.fromHtml("余<font color='#4BB7FD'>￥" + 0 + "</font>元"));
                }
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.topic:
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), WebViewCjActivity.class);
                    intent.putExtra(Constant.key1, "http://59.110.164.174:9404/cj.html");
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }

                break;
            case R.id.identify:
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, IdentifyFragment.class.getName());
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }
                break;
            case R.id.name:
                if (Utils.isLogin()) {

                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginReadyFragment.class.getName());
                    startActivity(intent);
                }
                break;
            case R.id.photo:
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, PersonInfoFragment.class.getName());
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }
                break;
            case R.id.widget1:
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, AccountFragment.class.getName());
                    intent.putExtra(Constant.key1, acount);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }

                break;
            case R.id.widget2:
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, MePayFragment.class.getName());
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);

                }

                break;
            case R.id.widget3:
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoanFragment.class.getName());
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);

                }

                break;
            case R.id.widget4:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, MaterialFragment.class.getName());
                startActivity(intent);

                break;
            case R.id.widget5:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, MyCollectionFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.widget6:


                break;
            case R.id.widget7:
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, MyProjectOrderFragment.class.getName());
                    startActivity(intent);
                } else {
                    Utils.startNewProject(getActivity());
                }
                break;
            case R.id.widget8:
                break;
            case R.id.widget9:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, OnlineCollaborationStepNextFragment.class.getName());
                startActivity(intent);

                break;
            case R.id.widget10:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, HelpFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.widget11:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, SecurityCenterFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.widget12:
                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, FeedbackFragment.class.getName());
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }

                break;
            case R.id.chat:

                if (Utils.isLogin()) {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, Discount1Fragment.class.getName());
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, LoginFragment.class.getName());
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserEntry event) {
        refreshUI();
    }

}
