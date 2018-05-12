package com.fenazola.mxcome.fragment.main.demand;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
import com.igexin.sdk.PushManager;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.utils.LogUtils;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 发布需求——防水
 */
public class DemandWaterproof extends Dialog implements View.OnClickListener {


    /**
     * 以下防水的——start
     */
    private TextView tvNumberWaterproof, tvWaterproofAct, tvWaterproofSure, tvWaterproofCancel, tvWaterproofMsg;
    private ImageView ivWaterproofMsg;
    private AlwaysMarqueeTextView messageWaterproofAtv;
    private RadioGroup waterproofGp;
 //   View view;
    /**以上防水的——end*/
    Dialog dialog;
    private int level= TechnicalLevelUtils.LEVEL_TOP;
    Activity mContext;
    String hid;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
    public DemandWaterproof(Activity context, String hId) {
        super(context, com.zss.library.R.style.CommonDialog);
        setContentView(R.layout.fragment_demand_waterproof);
        // view = LayoutInflater.from(getOwnerActivity()).inflate(R.layout.fragment_demand_design, null);
        this.mContext=context;
        this.hid=hId;
        int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, width);
        initView();
        initData();
    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        dialog = new Dialog(getActivity());
//        view = LayoutInflater.from(getActivity()).inflate(
//                R.layout.fragment_demand_waterproof, null);
//        initView();
//        initData(savedInstanceState);
//        dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnim;
//        dialog.getWindow().setBackgroundDrawableResource(R.color.trans);
//
//        dialog.setContentView(view);
//        return dialog;
//    }
    public void initView() {
        tvNumberWaterproof = (TextView) findViewById(R.id.pop_number_waterproof);
        tvWaterproofAct = (TextView) findViewById(R.id.waterproof_huodong);
        tvWaterproofSure = (TextView) findViewById(R.id.waterproof_sumbit);
        tvWaterproofCancel = (TextView) findViewById(R.id.waterproof_cancel);
        tvWaterproofMsg = (TextView) findViewById(R.id.message_name_tv_waterproof);
        ivWaterproofMsg = (ImageView) findViewById(R.id.message_iv_waterproof);
        messageWaterproofAtv = (AlwaysMarqueeTextView) findViewById(R.id.message_tv_waterproof);
        waterproofGp = (RadioGroup) findViewById(R.id.radioGroupID_waterproof);
        tvWaterproofAct.setOnClickListener(this);
        tvWaterproofCancel.setOnClickListener(this);
        tvWaterproofSure.setOnClickListener(this);

    }


    public void initData() {
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                waterproofGp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.waterproof_rd1:
                                level= TechnicalLevelUtils.LEVEL_TOP;
                                break;
                            case R.id.waterproof_rd2:
                                level= TechnicalLevelUtils.LEVEL_SENIOR;
                                break;
                            case R.id.waterproof_rd3:
                                level= TechnicalLevelUtils.LEVEL_ORDINARY;
                                break;
                        }
                    }
                });
                //格式化文本
                initChangeNumber(1000, 678, 10);

            }

        }, 200);


    }

    private void initChangeNumber(int i, int i1, int i2) {

        tvNumberWaterproof.setText(Html.fromHtml("质保期<font color='#FF0000'>" + i2 + "</font>年"));
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            case R.id.waterproof_cancel:
                dismiss();
                break;
            case R.id.waterproof_huodong:
                intent = new Intent(mContext, ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FreeDesignerFragment.class.getName());
                intent.putExtra(Constant.key1, FreeDesignerFragment.INPT_HIGH_ACT_TYPE);
                mContext.startActivity(intent);
                dismiss();
                break;
            case R.id.waterproof_sumbit:
                reqData();
                break;

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
        map.put("skill", SkillUtils.SKILL_FANGSHUI);
        map.put("message", "");
        map.put("voc_level",""+level);
       // map.put("isfree",""+isFree);
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
