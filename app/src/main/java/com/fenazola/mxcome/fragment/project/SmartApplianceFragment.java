package com.fenazola.mxcome.fragment.project;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.adapter.RecyclerSmartAdapter;
import com.fenazola.mxcome.entry.AdvertsEntry;
import com.fenazola.mxcome.entry.SmartApplianceEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.DataCache;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.RecycleViewDivider;
import com.google.gson.reflect.TypeToken;
import com.zss.library.fragment.BaseFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by XDONE on 2017/5/3.
 * 智能家电
 */

public class SmartApplianceFragment extends BaseFragment{

    private RecyclerView recycler;
    private RecyclerSmartAdapter adapter;
    private SmartPackageFragment parent;
    private GridLayoutManager gridLayoutManager;
    private String gradeText = DataCache.ENUM_PW_SHE;
    private String itemId = "10070002";

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_smart_appliance;
    }

    @Override
    public void initView() {
        super.initView();
        recycler = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        parent = (SmartPackageFragment)getBaseActivity().getCurrentFragmnet();

        adapter = new RecyclerSmartAdapter(getActivity(), RecyclerSmartAdapter.FLAG.APPLIANCE);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(gridLayoutManager);
        recycler.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.HORIZONTAL, 1, getColor(R.color.white)));

        adapter.setOnItemClickListener(new RecyclerSmartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setDefSelect(position);
            }
        });
        recycler.setAdapter(adapter);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? gridLayoutManager.getSpanCount() : 1;
            }
        });

        resetRecyclerView();
    }

    public void setGradeCenterView(String gradeText){
        this.gradeText = gradeText;
        parent.setShowRightView(true);
        resetRecyclerView();
    }

    public void resetRecyclerView(){
        adapter.getDefSelect().clear();
        HashMap<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        map.put("user_id", user.getUser_id());
        map.put("reqCode", itemId);
        map.put("tag", this.gradeText);
        map.put("brand_id", "");
        NetWorkUtils.post(getActivity(), "advertApi/getZhiNerMessageData.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String advets = resObj.optString("advets");
                List<AdvertsEntry> advList = GsonUtils.getListFromJSON(advets, new TypeToken<ArrayList<AdvertsEntry>>(){}.getType());
                String products = resObj.optString("products");
                List<SmartApplianceEntry> proList = GsonUtils.getListFromJSON(products, new TypeToken<ArrayList<SmartApplianceEntry>>(){}.getType());
                adapter.addAll(advList, proList);
            }
            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public List<SmartApplianceEntry> getSelectMaterial(){
        List<SmartApplianceEntry> arrs = new ArrayList<>();
        List<Integer> selectIds = adapter.getDefSelect();
        for(Integer item : selectIds){
            arrs.add(adapter.getRealPosition(item));
        }
        return arrs;
    }

}
