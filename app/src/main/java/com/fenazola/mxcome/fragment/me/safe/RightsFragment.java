package com.fenazola.mxcome.fragment.me.safe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.sercentre.FaqHelpFragment;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * Created by xuhuixiang on 2017/7/27.
 */

public class RightsFragment extends BaseFragment {
    private ListView listView;

    private CommonAdapter<String> mAdapter;
    ArrayList<String> names=new ArrayList<String>();
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_rights;
    }
    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.show_lv);

    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        //TODO
        initDateList();
        //names=names1;
        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_history_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                name.setTextColor(getColor(R.color.black));
                name.setText(workerListEntry);
            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
//                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqHelpFragment.class.getName());
//                startActivity(intent);
            }
        });
    }

    private void initDateList() {
        names.add("不当内容骚扰");
        names.add("欺诈骗钱行为");
        names.add("私人发布广告");
        names.add("商品质量信息");
        names.add("存在侵权行为");

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("投诉维权");
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
    }
}
