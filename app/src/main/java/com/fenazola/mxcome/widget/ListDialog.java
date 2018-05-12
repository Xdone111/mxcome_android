package com.fenazola.mxcome.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.main.demand.DemandReward;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.utils.DPUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 列表
 * @author zm
 */
public class ListDialog extends Dialog implements View.OnClickListener {


    private ListView listView;
    List<String> names=new ArrayList<String>();
    Activity mContext;
    private CommonAdapter<String> mAdapter;

    public ListDialog(Activity context, List<String> names) {
        super(context, com.zss.library.R.style.CommonDialog);
        setContentView(R.layout.dialog_list);
        // view = LayoutInflater.from(getOwnerActivity()).inflate(R.layout.fragment_demand_design, null);
        this.mContext=context;
        this.names=names;
        int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, width);
        initView();
        initData();
    }
    public void initView() {
        listView=(ListView)findViewById(R.id.listView);
    }


    public void initData() {
        mAdapter = new CommonAdapter<String>(mContext, R.layout.layout_list_item_dialog) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                name.setText(workerListEntry);
            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
            }
        });

    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.order_reward:
                break;
            case R.id.order_cosos:

                dismiss();
                break;

        }
    }

}
