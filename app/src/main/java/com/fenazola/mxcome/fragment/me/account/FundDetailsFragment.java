package com.fenazola.mxcome.fragment.me.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.TableArea;
import com.fenazola.mxcome.entry.BankCardEntry;
import com.fenazola.mxcome.entry.FundDetailsEntry;
import com.fenazola.mxcome.fragment.main.location.LocationFragment;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;

/**
 * Created by xuhuixiang on 2017/7/27.
 * 明细
 */

public class FundDetailsFragment extends BaseFragment {
    private ListView listView;
    private View contentView;
    private int pos=0;
    private FrameLayout classify_layout;

    private CommonAdapter<FundDetailsEntry> mAdapter;
    private ArrayList<FundDetailsEntry> names=new ArrayList<FundDetailsEntry>();
    private CommonAdapter<String> mAdapter1;
    private ArrayList<String> names1=new ArrayList<String>();
    private boolean isShow=false;
    @Override
    public int getLayoutResId() {
        return R.layout.fund_details_fragment;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView) findViewById(R.id.show_lv);
        classify_layout = (FrameLayout) findViewById(R.id.classify_layout);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        //TODO
        initDateList();
        //names=names1;
        mAdapter = new CommonAdapter<FundDetailsEntry>(getActivity(), R.layout.layout_fund_list_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final FundDetailsEntry workerListEntry, int i) {
                TextView name = viewHolder.findViewById(R.id.list_item_tv);
                TextView name1 = viewHolder.findViewById(R.id.list_item_tv1);
                TextView name2 = viewHolder.findViewById(R.id.list_item_tv2);
                TextView name3 = viewHolder.findViewById(R.id.list_item_tv3);
                name.setText(workerListEntry.getTitle());
                name1.setText(workerListEntry.getMny());
                name2.setText(workerListEntry.getDate());
                name3.setText(workerListEntry.getSumMny());


            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }
    //模拟数据

    private void initDateList() {
        for (int i=0;i<10;i++){
            FundDetailsEntry entry=new FundDetailsEntry();
            entry.setTitle("中国工商银行储蓄卡");
            if(i%2==0) {
                entry.setMny("+200" + i);
            }else{
                entry.setMny("-188" + i);

            }
            entry.setDate("yyyy-MM-dd");
            entry.setSumMny("10000"+i);
            names.add(entry);

        }
        names1.add("全部");
        names1.add("转入");
        names1.add("转出");
        names1.add("收益");
        names1.add("消费");
        names1.add("ME宝");



    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle("资金明细");
        toolbar.setRightText("筛选");
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initArea();

            }
        });
    }
    private void initArea() {
        classify_layout.removeAllViews();
        if(isShow==false) {
            isShow=true;
            contentView = getLayoutInflater(R.layout.popwin_fund);
            GridView gridView = (GridView) contentView.findViewById(R.id.gridView);
            LinearLayout lly=(LinearLayout)contentView.findViewById(R.id.ll_bottom);
            mAdapter1 = new CommonAdapter<String>(getActivity(), R.layout.griditem_worker_area) {
                @Override
                protected void convert(ViewHolder viewHolder, String tableArea, int i) {
                    TextView area = viewHolder.findViewById(R.id.area);
                    area.setText(tableArea);
                    if (pos == i) {
                        area.setBackgroundResource(R.drawable.rect_shape_blue_4dp);
                        area.setTextColor(getColor(R.color.white));
                    } else {
                        area.setBackgroundResource(R.drawable.rect_shape_grey);
                        area.setTextColor(getColor(R.color.colorGrey));

                    }
                }
            };
            gridView.setAdapter(mAdapter1);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    pos = position;
                    mAdapter1.notifyDataSetChanged();
                }
            });
            lly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    classify_layout.removeAllViews();
                    isShow=false;
                }
            });
            mAdapter1.replaceAll(names1);
            classify_layout.addView(contentView);
        }else{
            isShow=false;
        }
    }
}
