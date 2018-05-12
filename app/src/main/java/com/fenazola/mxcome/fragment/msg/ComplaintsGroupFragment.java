package com.fenazola.mxcome.fragment.msg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.fragment.sercentre.FaqHelpFragment;
import com.fenazola.mxcome.fragment.sercentre.FaqTermsFragment;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * 投诉此群
 * @author xhx
 */
public class ComplaintsGroupFragment extends BaseFragment implements View.OnClickListener{
    private ListView listView;

    private CommonAdapter<String> mAdapter;
    ArrayList<String> names=new ArrayList<String>();
    private  TextView complanTv;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_complaints_group;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.listView);
        complanTv=(TextView)findViewById(R.id.show_bot);
        complanTv.setOnClickListener(this);

    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        //TODO
        initDateList();
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
        if(names.size()>=9){
            return;
        }
        names.add("群成员不当内容骚扰");
        names.add("群成员欺诈骗钱行为");
        names.add("群成员违规发布广告");
        names.add("群成员传播谣言信息");
        names.add("群成员存在赌博行为");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.show_bot:
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqTermsFragment.class.getName());
                startActivity(intent);
        }
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("投诉此群");
        toolbar.setBgColor(getResources().getColor(R.color.white));
        toolbar.setTitleColor(Color.BLACK);
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
    }
}
