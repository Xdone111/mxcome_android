package com.fenazola.mxcome.fragment.main.demand;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
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
public class DemandOrder extends Dialog implements View.OnClickListener {


    /**
     * 派单
     */
    private TextView orderReward, orderFinsh;
    private TextView orderTitle;
    private TextView timerTv;
    Activity mContext;
    String hid;

    public DemandOrder(Activity context, String hId) {
        super(context, com.zss.library.R.style.CommonDialog);
        setContentView(R.layout.fragment_demand_order);
       // view = LayoutInflater.from(getOwnerActivity()).inflate(R.layout.fragment_demand_design, null);
        this.mContext=context;
        this.hid=hId;
        int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, width);
        initView();
        initData();
    }
    public void initView() {

        orderReward = (TextView)  findViewById(R.id.order_reward);
        orderFinsh = (TextView)  findViewById(R.id.order_cosos);
        orderTitle = (TextView)  findViewById(R.id.tab_order_tv);
        timerTv = (TextView)  findViewById(R.id.message_tv_order);
        orderReward.setOnClickListener(this);
        orderFinsh.setOnClickListener(this);


    }


    public void initData() {


    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.order_reward:
                DemandReward   dialog5 = new DemandReward(mContext, hid);
                dialog5.show();
                dismiss();
                break;
            case R.id.order_cosos:

                dismiss();
                break;

        }
    }

}
