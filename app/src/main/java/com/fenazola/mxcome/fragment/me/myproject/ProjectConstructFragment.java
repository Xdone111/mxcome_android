package com.fenazola.mxcome.fragment.me.myproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.WatiPayEntry;
import com.fenazola.mxcome.utils.DateUtils;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.ptr.PtrClassicFrameLayout;
import com.zss.library.ptr.PtrDefaultHandler;
import com.zss.library.ptr.PtrFrameLayout;
import com.zss.library.utils.LogUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by XDONE on 2017/4/21.
 * 正在施工
 */

public class ProjectConstructFragment extends BaseFragment {

    private ListView listView;
    private CommonAdapter<WatiPayEntry> adapter;
    private PtrClassicFrameLayout mPtrFrame;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_construct_project;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.mPtrFrame);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        adapter = new CommonAdapter<WatiPayEntry>(getActivity(), R.layout.listitem_construct_project) {
            @Override
            protected void convert(ViewHolder viewHolder, WatiPayEntry item, int position) {
                TextView tv_name = viewHolder.findViewById(R.id.tv_name);
                tv_name.setText(item.getTitle());
                TextView tv_title = viewHolder.findViewById(R.id.tv_time);
                tv_title.setText("施工时间 " + DateUtils.getTime(item.getAdd_time()));
                TextView house_type = viewHolder.findViewById(R.id.house_type);
                house_type.setText(item.getProjectOrderInfo());
            }
        };
        listView.setAdapter(adapter);
        Utils.setEmptyViewProject(listView,getActivity());
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, listView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                reloadData();
            }
        });
        mPtrFrame.setPullToRefresh(false);
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        reloadData();

    }

    private void reloadData() {
        UserEntry user = Utils.getUserEntry();
        String mxcome_no = user.getMxcome_no();
        HashMap<String, String> map = new HashMap<>();
        map.put("mxcome_no", mxcome_no);
        map.put("statu", "3");
        NetWorkUtils.post(getActivity(), "decora/queryMyDecoraInfo.do", map, false, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<WatiPayEntry> datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<WatiPayEntry>>() {
                }.getType());
                adapter.replaceAll(datas);
                mPtrFrame.refreshComplete();
            }

            @Override
            public void onError(String result, String code, String msg) {
                mPtrFrame.refreshComplete();
            }
        });

    }
}

