package com.fenazola.mxcome.fragment.sercentre;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
 * 录入信息
 * @author xhx
 */
public class FaqInputFragment extends BaseFragment implements View.OnClickListener{
    private ListView listView;

    private CommonAdapter<String> mAdapter;
    ArrayList<String> names=new ArrayList<String>();
    ArrayList<String> names1=new ArrayList<String>();
    private TextView initMore;
    @Override
    public int getLayoutResId() {
        return R.layout.activity_faq_input;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.listView);
        initMore=(TextView) findViewById(R.id.show_more);
        initMore.setOnClickListener(this);

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
                name.setText(workerListEntry);
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
    }

    private void initDateList() {
        if(names.size()>=9){
            return;
        }
        names.add("录入个人信息");
        names.add("如何实名认证");
        names.add("录入房屋信息");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.show_more:
                initDateList();
                mAdapter.clear();
                mAdapter.addAll(names);
                mAdapter.notifyDataSetChanged();

        }
    }
    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("录入信息");
        toolbar.setBgColor(getResources().getColor(R.color.colorBlue));
        toolbar.setTitleColor(Color.WHITE);
        //toolbar.setLeftImage(getResources().getDrawable(R.mipmap.project_left));
    }
}
