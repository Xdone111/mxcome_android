package com.fenazola.mxcome.fragment.me.safe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.sercentre.FaqFragment;
import com.fenazola.mxcome.fragment.sercentre.FaqTermsFragment;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonTextWidget;

import static com.baidu.location.d.j.G;

/**
 * Created by XDONE on 2017/6/2.
 * 安全中心
 */

public class SecurityCenterFragment extends BaseFragment implements View.OnClickListener {

    private CommonTextWidget textWidget1;
    private CommonTextWidget textWidget2;
    private CommonTextWidget textWidget3;
    private CommonTextWidget textWidget4;
    private CommonTextWidget textWidget5;
    private CommonTextWidget textWidget6;
    private ImageView backIv;
    private TextView titleTv;
    private TextView toFaq;
    int width;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_security_center;
    }

    @Override
    public void initView() {
        super.initView();
        WindowManager wm = getActivity().getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        textWidget1 = (CommonTextWidget) findViewById(R.id.TextWidget1);
        textWidget2 = (CommonTextWidget) findViewById(R.id.TextWidget2);
        textWidget3 = (CommonTextWidget) findViewById(R.id.TextWidget3);
        textWidget4 = (CommonTextWidget) findViewById(R.id.TextWidget4);
        textWidget5 = (CommonTextWidget) findViewById(R.id.TextWidget5);
        textWidget6 = (CommonTextWidget) findViewById(R.id.TextWidget6);
        backIv=(ImageView)findViewById(R.id.show_title_left);
        titleTv=(TextView)findViewById(R.id.show_title_tv);
        toFaq=(TextView)findViewById(R.id.show_faq);
        toFaq.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        textWidget1.setOnClickListener(this);
        textWidget2.setOnClickListener(this);
        textWidget3.setOnClickListener(this);
        textWidget4.setOnClickListener(this);
        textWidget5.setOnClickListener(this);
        textWidget6.setOnClickListener(this);
        titleTv.setText("安全中心");
        titleTv.setTextColor(getColor(R.color.white));
        backIv.setOnClickListener(this);
        changeItemH();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.show_faq:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqSafeFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.show_title_left:
                getActivity().onBackPressed();
                break;
            case R.id.TextWidget1:
                break;
            case R.id.TextWidget2:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, LoginPasswordFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.TextWidget3:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, SetPasswordFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.TextWidget4:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, UnfreezeAccountsStep1Fragment.class.getName());
                intent.putExtra(Constant.key1,UnfreezeAccountsFragment.IN_TYPE_UNFREE);
                startActivity(intent);
                break;
            case R.id.TextWidget5:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, UnfreezeAccountsStep1Fragment.class.getName());
                intent.putExtra(Constant.key1,UnfreezeAccountsFragment.IN_TYPE_FREE);
                startActivity(intent);
                break;
            case R.id.TextWidget6:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, RightsFragment.class.getName());
                startActivity(intent);
                break;
        }
    }
    private void changeItemH(){
        LinearLayout linearLayout=(LinearLayout)findViewById(R.id.show_top_ly);
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        linearParams.height = (int)((float)width*0.59);
        linearLayout.setLayoutParams(linearParams);
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("安全中心");
        toolbar.setBgColor(getColor(R.color.trans));
        toolbar.setVisibility(View.GONE);
    }
}
