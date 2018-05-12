package com.fenazola.mxcome.fragment.main.demand;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.LocationEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.SkillUtils;
import com.fenazola.mxcome.utils.TechnicalLevelUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.AlwaysMarqueeTextView;
import com.fenazola.mxcome.widget.AutoTextView;
import com.igexin.sdk.PushManager;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.utils.LogUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 发布需求——设计
 */
public class DemandDesign extends Dialog implements View.OnClickListener {


    /**
     * 以下设计的——start
     */
    private Handler mHandler = new Handler();
    private int mTextCount;
    private List<String> mList;
    private AutoTextView mTextView, mTextView1;
    private LinearLayout tab1, tab2;
    private TextView tv1, tvsub1, tv2, tvsub2, tvNub1, tvNub2, tvCancel, tvAct, tvSure, tvMsg;
    private ImageView iv1, iv2, ivMsg;
    private AlwaysMarqueeTextView messageAtv;
    private RadioGroup radioGp;
   // View view;
    Dialog dialog;
    Activity mContext;
    String hid;
    /**以上设计的——end*/
    private int isFree=2;
    private int level= TechnicalLevelUtils.LEVEL_SENIOR;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
    public DemandDesign(Activity context, String hId) {
        super(context, com.zss.library.R.style.CommonDialog);
        setContentView(R.layout.fragment_demand_design);
       // view = LayoutInflater.from(getOwnerActivity()).inflate(R.layout.fragment_demand_design, null);
        this.mContext=context;
        this.hid=hId;
        int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, width);
        initData();
    }
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        dialog = new Dialog(getActivity());
//        view = LayoutInflater.from(getActivity()).inflate(
//                R.layout.fragment_demand_design, null);
//        initView();
//        initData(savedInstanceState);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
//        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
//        dialog.setContentView(view);
//        return dialog;
//    }
    public void initView() {
//        getActivity().getWindow().setBackgroundDrawable(new
//                ColorDrawable(Color.TRANSPARENT));


    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mTextView.next();
            mTextCount++;
            if (mTextCount >= Integer.MAX_VALUE) {
                mTextCount = mList.size();
            }
            mTextView.setText(mList.get(mTextCount % (mList.size())));
            mTextView1.setText(mList.get(mTextCount % (mList.size())));
            if (mList.size() > 1) {
                mHandler.postDelayed(this, 2000);
            }

        }
    };

    public void initData() {
//        mHandler.postDelayed(new Runnable(){
//
//            @Override
//            public void run() {
                mTextView = (AutoTextView) findViewById(R.id.show_atv);
                mTextView1 = (AutoTextView)findViewById(R.id.show_atv1);
                tab1 = (LinearLayout) findViewById(R.id.tab_d_1_ly);
                tab2 = (LinearLayout) findViewById(R.id.tab_d_2_ly);
                tv1 = (TextView) findViewById(R.id.tab_d_1_tv);
                tvsub1 = (TextView) findViewById(R.id.tab_d_1_1_tv);
                tv2 = (TextView)findViewById(R.id.tab_d_2_tv);
                tvsub2 = (TextView) findViewById(R.id.tab_d_2_1_tv);
                tvNub1 = (TextView) findViewById(R.id.pop_number_1);
                tvNub2 = (TextView) findViewById(R.id.pop_number_2);
                tvCancel = (TextView) findViewById(R.id.pop_cancel);
                tvAct = (TextView) findViewById(R.id.pop_huodong);
                tvSure = (TextView) findViewById(R.id.pop_sure);
                iv1 = (ImageView) findViewById(R.id.tab_d_1_iv);
                iv2 = (ImageView) findViewById(R.id.tab_d_2_iv);
                radioGp = (RadioGroup) findViewById(R.id.radioGroupID);
                messageAtv = (AlwaysMarqueeTextView)findViewById(R.id.message_tv_pop);
                tvMsg = (TextView) findViewById(R.id.message_name_tv_pop);
                ivMsg = (ImageView) findViewById(R.id.message_iv_pop);
                tab1.setOnClickListener(DemandDesign.this);
                tab2.setOnClickListener(DemandDesign.this);
                tv1.setOnClickListener(DemandDesign.this);
                tvsub1.setOnClickListener(DemandDesign.this);
                tv2.setOnClickListener(DemandDesign.this);
                tvsub2.setOnClickListener(DemandDesign.this);
                tvCancel.setOnClickListener(DemandDesign.this);
                tvAct.setOnClickListener(DemandDesign.this);
                tvSure.setOnClickListener(DemandDesign.this);
                mList = new ArrayList<String>();
                //TODO: 2017/6/26
                mList.add("垂直滚动TextView测试1");
                mList.add("垂直滚动TextView测试2");
                mList.add("垂直滚动TextView测试3");
                mList.add("垂直滚动TextView测试4");
                mList.add("滚动TextView测试1");
                mList.add("滚动TextView测试2");
                mList.add("滚动TextView测试3");
                mList.add("滚动TextView测试4");
                mTextView.setText(mList.get(0));
                mTextView1.setText(mList.get(0));
                mTextCount = mList.size();
                mHandler.postDelayed(runnable, 2000);

                radioGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.femaleGroupID:
                                level=TechnicalLevelUtils.LEVEL_SENIOR;
                                break;
                            case R.id.maleGroupID:
                                level=TechnicalLevelUtils.LEVEL_ORDINARY;
                                break;
                        }
                    }
                });

                //格式化文本
                initChangeNumber(1000, 678, 10);

//            }
//
//        }, 1000);


    }

    private void initChangeNumber(int i, int i1, int i2) {
        tvNub1.setText(Html.fromHtml("今日<font color='#FF0000'>" + i + "</font>申请"));
        tvNub2.setText(Html.fromHtml("还剩<font color='#FF0000'>" + i1 + "</font>个名额"));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.tab_d_1_ly:
            case R.id.tab_d_1_tv:
            case R.id.tab_d_1_1_tv:
                initChangTaColor(1);
                break;
            case R.id.tab_d_2_ly:
            case R.id.tab_d_2_tv:
            case R.id.tab_d_2_1_tv:
                initChangTaColor(2);
                break;
            case R.id.pop_cancel:
                dismiss();
                break;
            case R.id.pop_huodong:
                intent = new Intent(mContext, ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FreeDesignerFragment.class.getName());
                intent.putExtra(Constant.key1, FreeDesignerFragment.INPT_ACT_TYPE);
                mContext.startActivity(intent);
                dismiss();
                break;
            case R.id.pop_sure:
                reqData();
                break;
        }
    }

    private void initChangTaColor(int i) {
        tv1.setTextColor( mContext.getResources().getColor(R.color.circle_toolbar_color));
        tvsub1.setTextColor(mContext.getResources().getColor(R.color.circle_toolbar_color));
        tv2.setTextColor(mContext.getResources().getColor(R.color.circle_toolbar_color));
        tvsub2.setTextColor(mContext.getResources().getColor(R.color.circle_toolbar_color));
        iv1.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        iv2.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        if (i == 1) {
            isFree=2;
            tv1.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
            tvsub1.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
            iv1.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlue));

        } else {
            isFree=1;
            tv2.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
            tvsub2.setTextColor(mContext.getResources().getColor(R.color.colorBlue));
            iv2.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlue));
        }
    }
    private void reqData() {
        HashMap<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        LocationEntry location = Utils.getLocationEntry();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String mxcome_no = user.getMxcome_no();
        map.put("user_cid", PushManager.getInstance().getClientid(getContext()));
        map.put("mxcome_no", mxcome_no);
        map.put("user_id", user.getUser_id());
        map.put("asset_id", hid);
        map.put("longitude", longitude + "");
        map.put("latitude", latitude + "");
        map.put("skill", SkillUtils.SKILL_SHEJI);
        map.put("message", "");
        map.put("voc_level",""+level);
        map.put("isfree",""+isFree);
        String url = Constant.newBaseUrl + "mainFunc/pushSingleBiz.do";
        NetWorkUtils.postUrl(mContext, url, map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                LogUtils.i("XHX", "成功：" + result);
                dismiss();
            }

            @Override
            public void onError(String result, String code, String msg) {
                LogUtils.i("XHX", "失败：" + result + ";" + code + ";" + msg);

            }
        });
    }
}
