package com.fenazola.mxcome.fragment.me.mepay;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.BankCardEntry;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/4/6.
 * 选择银行卡
 */

public class SelectBankCardFragment extends BaseFragment {
    private ListView listView;
    private CommonAdapter<BankCardEntry> adapter;
    private int pos = -1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_select_card;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        adapter = new CommonAdapter<BankCardEntry>(getActivity(), R.layout.layout_card_listitem) {
            @Override
            protected void convert(ViewHolder viewHolder, BankCardEntry bankCardEntry, int i) {
                ImageView image = viewHolder.findViewById(R.id.image);
                TextView text1 = viewHolder.findViewById(R.id.text1);
                TextView text2 = viewHolder.findViewById(R.id.text2);
                ImageView icon = viewHolder.findViewById(R.id.icon);

                text1.setText(bankCardEntry.getName());
                text2.setText(bankCardEntry.getNumber());
                icon.setImageResource(bankCardEntry.getIcon());
                if (pos == i) {
                    image.setVisibility(View.VISIBLE);
                } else {
                    image.setVisibility(View.INVISIBLE);
                }
            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                EventBus.getDefault().post(adapter.getItem(position));
                adapter.notifyDataSetChanged();
                backStackFragment();
            }
        });

        List<BankCardEntry> datas = new ArrayList<>();
        BankCardEntry item1 = new BankCardEntry();
        item1.setIcon(R.mipmap.ccb);
        item1.setName("中国建设银行");
        item1.setNumber("尾号8888 快捷");
        BankCardEntry item2 = new BankCardEntry();
        item2.setIcon(R.mipmap.boc);
        item2.setName("中国银行");
        item2.setNumber("尾号8888 快捷");
        datas.add(item1);
        datas.add(item2);
        datas.add(item1);
        datas.add(item2);
        adapter.addAll(datas);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("选择银行卡");
        toolbar.setRightImage(R.mipmap.add_group_img);
    }
}
