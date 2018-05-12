package com.fenazola.mxcome.fragment.sercentre;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.sercentre.ManualEntry;
import com.fenazola.mxcome.entry.sercentre.ManualTopEntry;
import com.fenazola.mxcome.widget.MyGridView;
import com.fenazola.mxcome.widget.MyListView;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * 使用手册
 * @author xhx
 */
public class ManualFragment extends BaseFragment implements View.OnClickListener{
    private MyListView listView;
    private MyGridView listView1;
    private CommonAdapter<ManualEntry> mAdapter;
    private CommonAdapter<ManualTopEntry>mAdapter1;
    ArrayList<ManualEntry> names=new ArrayList<ManualEntry>();
    ArrayList<ManualTopEntry> names1=new ArrayList<ManualTopEntry>();
    @Override
    public int getLayoutResId() {
        return R.layout.activity_manual;
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
        names.add(new ManualEntry("案例","通过精选了解万和美"));
        names.add(new ManualEntry("传递","学习分享 快乐装饰 明白消费"));
        names.add(new ManualEntry("开始","开始-立即新建-发布需求-下单"));
        names.add(new ManualEntry("进度","上帝视角参与装修，省心省力"));
        names.add(new ManualEntry("消息","与施工班组有效沟通，和好友愉快聊天"));
        names.add(new ManualEntry("好友","强大简单的好友模块"));
        names.add(new ManualEntry("日记","分享装修喜悦，还可以得奖励"));

        names1.add(new ManualTopEntry("整体装修",R.mipmap.zhengtizhuangxiu_img));
        names1.add(new ManualTopEntry("局部装修",R.mipmap.jubuzhuangxiu_img));
        names1.add(new ManualTopEntry("单项业务",R.mipmap.danxiangyewu_img));
        names1.add(new ManualTopEntry("装修贷款",R.mipmap.zhuangxiudaikuan_img));
        names1.add(new ManualTopEntry("资金增值",R.mipmap.zijinzengzi_img));
        names1.add(new ManualTopEntry("添加好友",R.mipmap.tianjiahaoyou_img));

        mAdapter = new CommonAdapter<ManualEntry>(getActivity(), R.layout.layout_manual_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final ManualEntry workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.title_tv);
                TextView sub = viewHolder.findViewById(R.id.sub_tv);
                name.setText(workerListEntry.getTitle());
                sub.setText(workerListEntry.getSubName());
            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqHelpFragment.class.getName());
                startActivity(intent);
            }
        });
        mAdapter1 = new CommonAdapter<ManualTopEntry>(getActivity(), R.layout.layout_manual_top_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final ManualTopEntry workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.title_tv);
                ImageView iv=viewHolder.findViewById(R.id.sub_iv);
                name.setText(workerListEntry.getTitle());
                iv.setImageResource(workerListEntry.getResId());
            }
        };
        listView1.setAdapter(mAdapter1);
        mAdapter1.addAll(names1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
//                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqInputFragment.class.getName());
//                startActivity(intent);
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
        getBaseActivity().setStatusBarColor(R.color.white);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("使用手册");
        toolbar.setBgColor(getResources().getColor(R.color.white));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setTitleColor(Color.BLACK);
       // toolbar.setLeftImage(getResources().getDrawable(R.mipmap.project_left));
    }
}
