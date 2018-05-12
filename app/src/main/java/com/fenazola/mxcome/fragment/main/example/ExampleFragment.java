package com.fenazola.mxcome.fragment.main.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.WebViewActivity;
import com.fenazola.mxcome.entry.ExampleEntry;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/6/3.
 */

public class ExampleFragment extends BaseFragment {

    private ListView listView;
    private CommonAdapter<ExampleEntry> adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_example;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        adapter = new CommonAdapter<ExampleEntry>(getActivity(), R.layout.listitem_example) {
            @Override
            protected void convert(ViewHolder viewHolder, ExampleEntry entry, int i) {
                ImageView image = viewHolder.findViewById(R.id.image);
                TextView text1 = viewHolder.findViewById(R.id.text1);
                TextView text2 = viewHolder.findViewById(R.id.text2);
                text1.setText(entry.getAddress());
                text2.setText(entry.getHouseType());
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra(Constant.key1, "http://59.110.164.174:9404/?pid=3");
                startActivity(intent);
            }
        });

        listView.setAdapter(adapter);

        ArrayList<ExampleEntry> datas = new ArrayList<>();
        ExampleEntry entry = new ExampleEntry();
        entry.setAddress("长沙 富湾国际 李先生家");
        entry.setHouseType("1-5万/83平米/欧美");
        for (int i = 0; i < 6; i++) {
            datas.add(entry);
        }
        adapter.addAll(datas);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("平台装修实例");
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }
}
