package com.fenazola.mxcome.fragment.me.mepay;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.main.demand.DemandReward;

/**
 * 转出对话框
 */
public class MyPayDialog extends Dialog implements View.OnClickListener {


    /**
     * 派单
     */
    private TextView diess;
    Activity mContext;
    TextView tv1,tv2,titleTv,line;

    public MyPayDialog(Activity context) {
        super(context, com.zss.library.R.style.CommonDialog);
        setContentView(R.layout.layout_me_pay_dialog);
       // view = LayoutInflater.from(getOwnerActivity()).inflate(R.layout.fragment_demand_design, null);
        this.mContext=context;
        int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, width);
        initView();
        initData();
    }
    public MyPayDialog(Activity context,String txt1,String txt2,String title) {
        super(context, com.zss.library.R.style.CommonDialog);
        setContentView(R.layout.layout_me_pay_dialog);
        // view = LayoutInflater.from(getOwnerActivity()).inflate(R.layout.fragment_demand_design, null);
        this.mContext=context;
        int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, width);
        tv1=(TextView)findViewById(R.id.text1);

        tv2=(TextView)findViewById(R.id.text2);
        if(txt2.equals("")){
            tv2.setVisibility(View.GONE);
        }
        titleTv=(TextView)findViewById(R.id.text0);
        line=(TextView)findViewById(R.id.line);
        titleTv.setVisibility(View.VISIBLE);
        line.setVisibility(View.VISIBLE);
        titleTv.setText(title);
        tv1.setText(txt1);
        tv2.setText(Html.fromHtml(txt2));
        initView();
        initData();
    }
    public void initView() {
        diess = (TextView)  findViewById(R.id.text3);

        diess.setOnClickListener(this);


    }


    public void initData() {


    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.text3:

                dismiss();
                break;

        }
    }

}
