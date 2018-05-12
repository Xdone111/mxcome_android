package com.fenazola.mxcome.fragment.project;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.adapter.RecyclerSmartAdapter;
import com.fenazola.mxcome.entry.AdvertsEntry;
import com.fenazola.mxcome.entry.SmartApplianceEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DataCache;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.RecycleViewDivider;
import com.google.gson.reflect.TypeToken;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.CommonToastUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by XDONE on 2017/5/3.
 * 智能家居
 */

public class SmartHomeFurnishedFragment extends BaseFragment{

    private ListView listView;
    private CommonAdapter<AdvertsEntry> brandAdapter;
    private RecyclerView recycler;
    private RecyclerSmartAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private Button completeBtn;
    private SmartPackageFragment parent;
    private AdvertsEntry choiceBrand;
    private String gradeText = DataCache.ENUM_PW_SHE;
    private String itemId = "10070001";

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_smart_home;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
        recycler = (RecyclerView) findViewById(R.id.recyclerView);
        completeBtn = (Button) findViewById(R.id.complete);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        parent = (SmartPackageFragment)getBaseActivity().getCurrentFragmnet();
        choiceBrand = DataCache.brand10070001;

        brandAdapter = new CommonAdapter<AdvertsEntry>(getActivity(), R.layout.listitem_material_product) {

            @Override
            protected void convert(ViewHolder viewHolder, AdvertsEntry advertsEntry, int position) {
                TextView tv_choice = viewHolder.findViewById(R.id.tv_choice);
                ImageView img_choice = viewHolder.findViewById(R.id.img_choice);
                ImageView image = viewHolder.findViewById(R.id.image);
                final AdvertsEntry item = brandAdapter.getItem(position);
                if(choiceBrand != null && choiceBrand.getBrand_id().equals(item.getBrand_id())){
                    tv_choice.setVisibility(View.GONE);
                    img_choice.setVisibility(View.VISIBLE);
                }else{
                    tv_choice.setVisibility(View.VISIBLE);
                    img_choice.setVisibility(View.GONE);
                }
                Glide.with(getActivity()).load(Constant.imageUrl + Utils.getFirstJSONArray(item.getAdvert_pics())).into(image);
                tv_choice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listView.setVisibility(View.GONE);
                        recycler.setVisibility(View.VISIBLE);
                        completeBtn.setVisibility(View.VISIBLE);
                        choiceBrand = item;
                        brandAdapter.notifyDataSetChanged();
                        resetRecyclerView();
                    }
                });
            }
        };
        listView.setAdapter(brandAdapter);

        adapter = new RecyclerSmartAdapter(getActivity(), RecyclerSmartAdapter.FLAG.HOME_FURNISED);
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

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getDefSelect().size() == 0){
                    CommonToastUtils.showInCenterToast(getActivity(), "请选择智能家居服务");
                    return;
                }
                listView.setVisibility(View.VISIBLE);
                recycler.setVisibility(View.GONE);
                completeBtn.setVisibility(View.GONE);
                parent.setShowRightView(false);
                brandAdapter.notifyDataSetChanged();
            }
        });
        reqData();
    }

    private void reqData() {
        HashMap<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        map.put("user_id", user.getUser_id());
        map.put("clum_id", itemId); //智能家居
        NetWorkUtils.post(getActivity(), "hrpc/getAvertDataForClum", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<AdvertsEntry> list = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<AdvertsEntry>>(){}.getType());
                brandAdapter.addAll(list);
                if(list != null && choiceBrand != null){
                    for (AdvertsEntry item: list){
                        if(choiceBrand.getAdvert_id().equals(item.getAdvert_id())){
                            listView.setVisibility(View.GONE);
                            recycler.setVisibility(View.VISIBLE);
                            completeBtn.setVisibility(View.VISIBLE);
                            choiceBrand = item;
                            resetRecyclerView();
                            break;
                        }
                    }
                }
            }
            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public void setGradeCenterView(String gradeText){
        this.gradeText = gradeText;
        if (recycler.getVisibility() == View.VISIBLE){
            parent.setShowRightView(true);
        } else {
            parent.setShowRightView(false);
        }
        resetRecyclerView();
    }

    public void resetRecyclerView(){
        if(choiceBrand != null){
            adapter.getDefSelect().clear();
            HashMap<String, String> map = new HashMap<>();
            UserEntry user = Utils.getUserEntry();
            map.put("user_id", user.getUser_id());
            map.put("reqCode", itemId);
            map.put("tag", this.gradeText);
            map.put("brand_id", choiceBrand.getBrand_id());
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
    }

    public boolean onBackPressed() {
        if (recycler.getVisibility() == View.VISIBLE){
            listView.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.GONE);
            completeBtn.setVisibility(View.GONE);
            return true;
        }else{
            return false;
        }
    }

    public AdvertsEntry getSelectBrand(){
        return choiceBrand;
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
