package com.fenazola.mxcome.fragment.main.demand;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.DemandEntry;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/4/20.
 * 当前发布
 */

public class CurrentDemandFragment extends BaseFragment {

    private ListView listView;
    private CommonAdapter<DemandEntry> adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_current_demand;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);


        adapter = new CommonAdapter<DemandEntry>(getActivity(), R.layout.listitem_current_demand) {
            @Override
            protected void convert(ViewHolder viewHolder, DemandEntry entry, int i) {
                TextView tv_material = viewHolder.findViewById(R.id.tv_material);
                TextView tv_addr = viewHolder.findViewById(R.id.tv_addr);
                TextView tv_time = viewHolder.findViewById(R.id.tv_time);
                TextView tv_state = viewHolder.findViewById(R.id.tv_state);
                TextView tv_cancel = viewHolder.findViewById(R.id.tv_cancel);
                TextView remain_time = viewHolder.findViewById(R.id.remain_time);
                TextView tv_money = viewHolder.findViewById(R.id.tv_money);
                tv_material.setText(entry.getMaterial());
                tv_addr.setText(entry.getAddr());
                tv_time.setText(entry.getTime());
                tv_state.setText(entry.getState());
                remain_time.setText(entry.getRemain_time());
                tv_money.setText(entry.getMoney());
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        List<DemandEntry> datas = new ArrayList<>();
        DemandEntry item = new DemandEntry();
        item.setMaterial("地板");
        item.setAddr("富湾国际");
        item.setTime("2017-05-06");
        item.setState("状态");
        item.setRemain_time("剩13分钟");
        item.setMoney("0元");
        for (int i = 0; i < 10; i++) {
            datas.add(item);
        }
        adapter.addAll(datas);
    }
}
