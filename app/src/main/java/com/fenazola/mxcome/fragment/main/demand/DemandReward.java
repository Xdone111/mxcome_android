package com.fenazola.mxcome.fragment.main.demand;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;

/**
 * 发布需求——设计
 */
public class DemandReward extends Dialog implements View.OnClickListener {


    /**
     * 打赏 0.365
     */
    private EditText rewardNumberEt;
    private TextView noReward, yesReWard;
    Activity mContext;
    String hid;
    int width;

    public DemandReward(Activity context, String hId) {
        super(context, com.zss.library.R.style.CommonDialog);
        WindowManager wm = context.getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        setContentView(R.layout.fragment_demand_reward);
       // view = LayoutInflater.from(getOwnerActivity()).inflate(R.layout.fragment_demand_design, null);
        this.mContext=context;
        this.hid=hId;
        int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, width);
        initView();
        initData();
    }
    public void initView() {

        noReward = (TextView) findViewById(R.id.reward_no);
        yesReWard = (TextView) findViewById(R.id.reward_yes);
        rewardNumberEt = (EditText) findViewById(R.id.reward_number_et);
        noReward.setOnClickListener(this);
        yesReWard.setOnClickListener(this);


    }


    public void initData() {
        changeTelH();
    }
    private void changeTelH(){
        LinearLayout show_bg=(LinearLayout)findViewById(R.id.show_bg);
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) show_bg.getLayoutParams();
        linearParams.height = (int)((float)(width-40)*0.365);
        show_bg.setLayoutParams(linearParams);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.reward_no:
                dismiss();
                break;
            case R.id.reward_yes:
                Demandwheel   dialog5 = new Demandwheel(mContext);
                dialog5.show();
                dismiss();

                break;

        }
    }

}
