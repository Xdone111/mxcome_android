package com.fenazola.mxcome.fragment.me;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.DecorateStepEntry;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/7/29.
 * 我的收藏
 */

public class MyCollectionFragment extends BaseFragment {

    private GridView gridView;
    private Integer[] mColors = new Integer[]{R.color.colorBlue, R.color.colorRed,
            R.color.me_color_green, R.color.colorYellow,
            R.color.green_coll_bg, R.color.topic_text_color6};

    private Integer[] mBg1s = new Integer[]{R.drawable.my_coll_blue_def,
            R.drawable.my_coll_red_def,
            R.drawable.my_coll_green1_def,
            R.drawable.my_coll_yellow_def,
            R.drawable.my_coll_green_def,
            R.drawable.my_coll_blue1_def};

    private Integer[] mBg2s = new Integer[]{R.drawable.my_coll_blue_sel,
            R.drawable.my_coll_red_sel,
            R.drawable.my_coll_green1_sel,
            R.drawable.my_coll_yellow_sel,
            R.drawable.my_coll_green_sel,
            R.drawable.my_coll_blue1_sel};

    List<String> mTitles = new ArrayList<String >();
    private CommonAdapter<String> adapter;
    private int pos = -1;

    private ListView listView;
    private CommonAdapter<String> mAdapter1;
    private List<String> names1=new ArrayList<String>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_my_collection;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.white);
        gridView = (GridView) findViewById(R.id.gridView);
        listView=(ListView) findViewById(R.id.show_list);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mTitles.add("案例");
        mTitles.add("百科");
        mTitles.add("话题");
        mTitles.add("设计");
        mTitles.add("视频");
        mTitles.add("杂志");
        for (int i=0;i<11;i++){
            names1.add("我是标题"+i);
        }

        adapter = new CommonAdapter<String>(getActivity(), R.layout.layout_my_collection_grid_item) {
            @Override
            protected void convert(ViewHolder viewHolder, String itemEntry, int i) {
                TextView tvBg = viewHolder.findViewById(R.id.tv_bg);
                tvBg.setText(itemEntry);
                if (pos == i) {
                    tvBg.setTextColor(getColor(R.color.white));
                    tvBg.setBackgroundResource(mBg2s[i]);
                } else {
                    tvBg.setTextColor(getColor(mColors[i]));
                    tvBg.setBackgroundResource(mBg1s[i]);
                }
            }
        };
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                adapter.notifyDataSetChanged();
            }
        });

        adapter.addAll(mTitles);

        mAdapter1 = new CommonAdapter<String>(getActivity(), R.layout.layout_my_collection_list_item) {
            @Override
            protected void convert(ViewHolder viewHolder, final String workerListEntry,final int i) {
                LinearLayout bg=viewHolder.findViewById(R.id.show_bg);
                TextView title = viewHolder.findViewById(R.id.tv_1);
                TextView date = viewHolder.findViewById(R.id.tv_2);
                TextView des = viewHolder.findViewById(R.id.tv_3);
                ImageView iv = viewHolder.findViewById(R.id.tv_4);
                TextView tvleft = viewHolder.findViewById(R.id.left_v);
                TextView tvRight = viewHolder.findViewById(R.id.right_v);
                TextView tvL=viewHolder.findViewById(R.id.right_v_l);
                TextView tvR=viewHolder.findViewById(R.id.right_v_r);
                title.setText(workerListEntry);
                if(i%2==0){
                    tvRight.setVisibility(View.VISIBLE);
                    tvleft.setVisibility(View.GONE);
                    bg.setBackgroundResource(R.drawable.rect_my_coll_right);
                    tvRight.setBackgroundResource(R.color.trans);
                    tvL.setVisibility(View.VISIBLE);
                    tvR.setVisibility(View.GONE);
                }else{

                    bg.setBackgroundResource(R.drawable.rect_my_coll_left);
                    tvRight.setVisibility(View.VISIBLE);
                    tvleft.setVisibility(View.VISIBLE);
                    tvRight.setBackgroundResource(R.color.white);
                    tvR.setVisibility(View.VISIBLE);
                    tvL.setVisibility(View.GONE);
                }

            }
        };
        listView.setAdapter(mAdapter1);
        mAdapter1.addAll(names1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setTitleColor(getColor(R.color.grey));
        toolbar.setTitle("收 藏");
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
    }
}
