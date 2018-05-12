package com.fenazola.mxcome.fragment.me.mepay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.MepayEntry;
import com.fenazola.mxcome.widget.MyListView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonSwitchWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/4/5.
 * me宝
 */

public class MePayFragment extends BaseFragment implements View.OnClickListener {


    private TextView money;
    private TextView foundAdd;
    private CommonSwitchWidget sWidget;
    private TextView bottom1;
    private TextView bottom2;
    private MyListView listView;
    private CommonAdapter<MepayEntry> adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me_pay;
    }

    @Override
    public void initView() {
        super.initView();
        money = (TextView) findViewById(R.id.money);
        foundAdd = (TextView) findViewById(R.id.found_add);
        sWidget = (CommonSwitchWidget) findViewById(R.id.sWidget);
        bottom1 = (TextView) findViewById(R.id.bottom1);
        bottom2 = (TextView) findViewById(R.id.bottom2);
        listView = (MyListView) findViewById(R.id.listView);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        bottom1.setOnClickListener(this);
        bottom2.setOnClickListener(this);
        foundAdd.setOnClickListener(this);

        adapter = new CommonAdapter<MepayEntry>(getActivity(), R.layout.listitem_mepay) {
            @Override
            protected void convert(ViewHolder viewHolder, MepayEntry mepayEntry, int i) {
                TextView tvAddr = viewHolder.findViewById(R.id.tv_addr);
                TextView tvMoney = viewHolder.findViewById(R.id.tv_money);
                tvAddr.setText(mepayEntry.getAddr());
                tvMoney.setText(mepayEntry.getMoney());
            }
        };

        listView.setAdapter(adapter);

        ArrayList<MepayEntry> datas = new ArrayList<MepayEntry>();
        MepayEntry entry = new MepayEntry();
        entry.setAddr("富湾国际拆改工程款");
        entry.setMoney("8000");
        for (int i = 0; i < 4; i++) {
            datas.add(entry);
        }
        adapter.addAll(datas);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.bottom1:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, RollOutFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.bottom2:
                intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, RechargeFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.found_add:
//                intent = new Intent(getActivity(), ZFrameActivity.class);
//                intent.putExtra(ZFrameActivity.CLASS_NAME, FundAddFragment.class.getName());
//                startActivity(intent);
                break;
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("ME宝");
        toolbar.setBgColor(getColor(R.color.colorBlue));

    }
}
