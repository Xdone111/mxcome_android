package com.fenazola.mxcome.fragment.sercentre;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.widget.MyGridView;
import com.fenazola.mxcome.widget.MyListView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * 常见问题
 * @author xhx
 */
public class FaqFragment extends BaseFragment implements View.OnClickListener{
    private MyListView listView;
    private MyGridView listView1;
    private CommonAdapter<String> mAdapter,mAdapter1;
    ArrayList<String> names=new ArrayList<String>();
    ArrayList<String> names1=new ArrayList<String>();
    @Override
    public int getLayoutResId() {
        return R.layout.activity_faq;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(MyListView)findViewById(R.id.listView);
        listView1=(MyGridView) findViewById(R.id.listView_1);

    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        //TODO
        names.add("如何开始装修？");
        names.add("什么是局部装修？");
        names.add("怎么发布单项业务？");
        names.add("资金收益如何提现？");
        names.add("如何支付工程款？");

        names1.add("录入信息");
        names1.add("发布需求");
        names1.add("工程施工");
        names1.add("材料补货");
        names1.add("监理验收");
        names1.add("授权支付");
        names1.add("保障团队");
        names1.add("信用体系");
        names1.add("售后保障");
        names1.add("传递分享");
        names1.add("关于好友");
        names1.add("加入合作");
        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_history_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                name.setText(workerListEntry);
            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);

        mAdapter1 = new CommonAdapter<String>(getActivity(), R.layout.layout_history_item_gv) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                name.setText(workerListEntry);
            }
        };
        listView1.setAdapter(mAdapter1);
        mAdapter1.addAll(names1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqInputFragment.class.getName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("常见问题");
        toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
        toolbar.setTitleColor(Color.WHITE);
        //toolbar.setLeftImage(getResources().getDrawable(R.mipmap.project_left));
        toolbar.setRightImage(R.mipmap.seach_right_img);
    }
}
