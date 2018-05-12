package com.fenazola.mxcome.fragment.msg;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.AcquaintanceEntry;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/3/15.
 */

public class AcquaintanceFragment extends BaseFragment {

    private ListView listView;
    private CommonAdapter<AcquaintanceEntry> adapter;
    private List<AcquaintanceEntry> datas;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_msg_acquaintance;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.list_acquaintance);
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        adapter = new CommonAdapter<AcquaintanceEntry>(getActivity(), R.layout.listitem_msg_acquaintance) {
            @Override
            protected void convert(ViewHolder viewHolder, AcquaintanceEntry acquaintanceEntry, int i) {
                ImageView msgPhoto = viewHolder.findViewById(R.id.msg_photo);
                TextView addFriend = viewHolder.findViewById(R.id.add_friend);
                addFriend.setText(acquaintanceEntry.getAdd());
                TextView name = viewHolder.findViewById(R.id.name);
                name.setText(acquaintanceEntry.getName());
                TextView autonym = viewHolder.findViewById(R.id.autonym);
                autonym.setText(acquaintanceEntry.getAutonym());
                TextView friendNumber = viewHolder.findViewById(R.id.friend_number);
                friendNumber.setText(acquaintanceEntry.getNumber());
                TextView content = viewHolder.findViewById(R.id.content);
                LinearLayout line = viewHolder.findViewById(R.id.line);
                if (datas.size()-1 ==i) {
                    line.setVisibility(View.INVISIBLE);
                }

            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getItem(position);
            }
        });

        datas = new ArrayList<>();
        AcquaintanceEntry item = new AcquaintanceEntry();
        item.setNumber("3");
        item.setName("xdone");
        item.setAdd("加好友");
        item.setAutonym("已实名");
        datas.add(item);
        datas.add(item);
        datas.add(item);
        datas.add(item);
        adapter.addAll(datas);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("可能认识的人");
        toolbar.setBgColor(getColor(R.color.colorBlue));
    }
}
