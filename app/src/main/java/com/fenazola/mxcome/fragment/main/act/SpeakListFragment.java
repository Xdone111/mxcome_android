package com.fenazola.mxcome.fragment.main.act;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * Created by xuhuixiang on 2017/7/27.
 * 账户问题
 */

public class SpeakListFragment extends BaseFragment {
    private ListView listView;
    private View contentView;
    private CommonAdapter<String> mAdapter;
    private ArrayList<String> names=new ArrayList<String>();
    @Override
    public int getLayoutResId() {
        return R.layout.speak_list_fragment;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.show_lv);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //TODO
        initDateList();
        //names=names1;
        mAdapter = new CommonAdapter<String>(getActivity(), R.layout.layout_speak_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry, int i) {
                //TextView name = viewHolder.findViewById(R.id.list_item_tv);
                //name.setText(workerListEntry);


            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"点我干什么",Toast.LENGTH_SHORT).show();
            }
        });

    }
    //模拟数据

    private void initDateList() {
      for (int i=0;i<10;i++){
      names.add(""+i);}
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("MXCOME代言人");

    }
}
