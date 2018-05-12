package com.fenazola.mxcome.fragment.msg;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布名片
 */
@Deprecated
public class QcMoreInfoDialog extends Dialog implements View.OnClickListener {


    /**
     * 派单
     */
    private TextView orderReward, orderFinsh;
    private TextView orderTitle;
    Activity mContext;

    GridView userList;
    private CommonAdapter<String> mAdapter;
    private List<String> names=new ArrayList<String>();

    public QcMoreInfoDialog(Activity context) {
        super(context, com.zss.library.R.style.CommonDialog);
        setContentView(R.layout.fragment_send_business_card);
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
        orderTitle = (TextView)  findViewById(R.id.tab_order_tv);
        orderReward.setOnClickListener(this);
        orderFinsh.setOnClickListener(this);

        userList= (GridView) findViewById(R.id.show_lv);


    }


    public void initData() {
        getDate();

        mAdapter = new CommonAdapter<String>(mContext, R.layout.layout_send_card_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, final int i) {
                TextView city_tv = (TextView) viewHolder.findViewById(R.id.list_item_tv);
                CircleImageView phone=(CircleImageView)viewHolder.findViewById(R.id.list_item_iv);

                city_tv.setText(workerListEntry);
            }
        };
        userList.setAdapter(mAdapter);
        mAdapter.addAll(names);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }
    private void getDate() {

        names.add("段誉");
        names.add("段正淳");
        names.add("张三丰");
        names.add("陈坤");
        names.add("赵子龙");
        names.add("宋江");

    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.order_reward:
                dismiss();
                break;
            case R.id.order_cosos:

                dismiss();
                break;

        }
    }

}
