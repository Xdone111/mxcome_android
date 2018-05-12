package com.fenazola.mxcome.fragment.project.monitor;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.SiteEntry;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/7/27.
 * 工地直播
 */

public class SiteLiveFragment extends BaseFragment {

    private ListView listView;
    private CommonAdapter<SiteEntry> adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_site_live;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        adapter = new CommonAdapter<SiteEntry>(getActivity(), R.layout.listview_site_live) {
            @Override
            protected void convert(ViewHolder viewHolder, SiteEntry siteEntry, int i) {
                final ImageView switchImg = viewHolder.findViewById(R.id.switch_img);
                TextView houseName = viewHolder.findViewById(R.id.houseName);
                TextView watchNum = viewHolder.findViewById(R.id.watchNum);
                switchImg.setImageResource(siteEntry.getHouseImg());
            }
        };
        listView.setAdapter(adapter);
        View headerView = getLayoutInflater(R.layout.listview_header_site_live);
        listView.addHeaderView(headerView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.i("xdd-------------", "执行了");
                SiteMonitorFragment fragment = new SiteMonitorFragment();
//                SiteEntry sitenEntry = adapter.getItem(position);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(Constant.key1,sitenEntry);
//                fragment.setArguments(bundle);
                addFragment(fragment);
            }
        });

        List<SiteEntry> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            SiteEntry entry = new SiteEntry();
            entry.setHouseImg(R.mipmap.site_live_play_img);
            entry.setHouseName("湘江世纪城");
            entry.setWatchNum("60");
            list.add(entry);
        }
        adapter.addAll(list);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("工地直播");
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitleColor(getColor(R.color.white));
        toolbar.setRightImage(R.mipmap.site_location_white_icon);
    }
}
