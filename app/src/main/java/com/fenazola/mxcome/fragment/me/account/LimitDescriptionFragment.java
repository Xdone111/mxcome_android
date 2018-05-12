package com.fenazola.mxcome.fragment.me.account;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.BankCardEntry;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * Created by xuhuixiang on 2017/7/27.
 * 限额说明
 */

public class LimitDescriptionFragment extends BaseFragment {
    private ListView listView;

    private CommonAdapter<BankCardEntry> mAdapter;
    private ArrayList<BankCardEntry> names=new ArrayList<BankCardEntry>();
    private TextView textView1,textView2;
    @Override
    public int getLayoutResId() {
        return R.layout.limit_description_fragment;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.show_lv);
        textView1=(TextView) findViewById(R.id.tv1);
        textView2=(TextView) findViewById(R.id.tv2);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //TODO
        initDateList();
        //names=names1;
        mAdapter = new CommonAdapter<BankCardEntry>(getActivity(), R.layout.layout_bank_list_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final BankCardEntry workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                TextView name1 = viewHolder.findViewById(R.id.list_item_tv2);
                name.setText(workerListEntry.getName()+"("+workerListEntry.getNumber()+")");
                name1.setText("单笔"+workerListEntry.getOnlyOne()
                        +"元  单日"+workerListEntry.getOnlyDay()
                        +"元  单月"+workerListEntry.getOnlyMonth()+"元");

            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        textView1.setText(Html.fromHtml("<font color='#4BB7FD'>余额支付限额</font>：根据账户认证的信用等级、收款卡是否为同名卡等因素共同确定，转账信息提交后确定是否超限。"));
        textView2.setText(Html.fromHtml("<font color='#4BB7FD'>银行卡的支付限额</font>：根据银行设定，购物支付、转账、还款缴费等场景限额共享，无法提升。若提示超限，建议更换其他付款方式。"));

    }
    //模拟数据

    private void initDateList() {
        for (int i=0;i<4;i++){
            BankCardEntry entry=new BankCardEntry();
            entry.setName("中国工商银行储蓄卡");
            entry.setNumber("200"+i);
            entry.setOnlyOne("1000"+i);
            entry.setOnlyDay("10000"+i);
            entry.setOnlyMonth("100000"+i);
            names.add(entry);

        }

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("限额说明");
        toolbar.setRightImage(R.mipmap.question_w);
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
