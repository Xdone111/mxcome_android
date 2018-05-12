package com.fenazola.mxcome.fragment.project;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.project.OrderEntry;
import com.fenazola.mxcome.entry.project.PremiseEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.MyListView;
import com.google.gson.reflect.TypeToken;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.SharedPrefUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择工程
 */
public class ProjectChoiceFragment extends BaseFragment implements View.OnClickListener {

    private MyListView listView;
    private CommonAdapter<PremiseEntry> adapter;
    private TextView new_create_info;
    private int width;
    private ImageView adv;
    List<PremiseEntry> datas=new ArrayList<PremiseEntry>();
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_project_choice;
    }

    @Override
    public void initView() {
        super.initView();
        width = DPUtils.getScreenW(getActivity());
        listView = (MyListView) findViewById(R.id.listView);
        new_create_info = (TextView) findViewById(R.id.new_create_info);
        adv = (ImageView)findViewById(R.id.adv);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        EventBus.getDefault().register(this);
        changeItemH(adv);
        adapter = new CommonAdapter<PremiseEntry>(getActivity(), R.layout.listitem_house_choice) {
            @Override
            protected void convert(ViewHolder viewHolder, final PremiseEntry item, int position) {
                TextView tv_name = viewHolder.findViewById(R.id.tv_name);
                tv_name.setText(item.getHouseInfo());
                TextView tv_pcd = viewHolder.findViewById(R.id.tv_addr);
                tv_pcd.setText(item.getAddrInfo());
                View edit_project = viewHolder.findViewById(R.id.ll_left);
                TextView bom_line=viewHolder.findViewById(R.id.bom_line);
                TextView show_lin=viewHolder.findViewById(R.id.show_lin);
                edit_project.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        args.putSerializable(Constant.key1, item);
                        ProjectInputInfoFragment fragment = new ProjectInputInfoFragment();
                        fragment.setArguments(args);
                        addFragment(fragment);
                    }
                });
                TextView new_project = viewHolder.findViewById(R.id.new_project);
                new_project.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle args = new Bundle();
                        OrderEntry order = new OrderEntry();
                        order.setHouseId(item.getmDecora().getHouseId());
                        order.setHouse(item.getmDecora().getHouse());
                        order.setHouseInfo(item.getHouseInfo());
                        order.setAddrInfo(item.getAddrInfo());
                        order.setCity_code(item.getCity());
                        args.putSerializable(Constant.key1, order);
                        ProjectDemandChoiceFragment fragment = new ProjectDemandChoiceFragment();
                        fragment.setArguments(args);
                        addFragment(fragment);
                    }
                });
                if(datas.size()-1==position){
                    bom_line.setVisibility(View.VISIBLE);
                    show_lin.setVisibility(View.INVISIBLE);
                }else{
                    bom_line.setVisibility(View.GONE);
                    show_lin.setVisibility(View.VISIBLE);
                }
            }
        };
        listView.setAdapter(adapter);

        new_create_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectInputInfoFragment fragment = new ProjectInputInfoFragment();
                addFragment(fragment);
            }
        });

        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            String res = args.getString(Constant.key1);
            datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<PremiseEntry>>() {
            }.getType());
            adapter.addAll(datas);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reloadData(PremiseEntry entry) {
        SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
        UserEntry user = (UserEntry) prefUtils.get("user");
        Map<String, String> map = new HashMap<>();
        map.put("user_id", user.getUser_id());
        NetWorkUtils.post(getActivity(), "appApi/fDecoraInfo.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<PremiseEntry> datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<PremiseEntry>>() {
                }.getType());
                adapter.replaceAll(datas);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgRes(R.color.trans);
        toolbar.setTitle(getString(R.string.project_choice));
        toolbar.setRightText(getString(R.string.home_page));
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
    private void changeItemH(ImageView iv){
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) iv.getLayoutParams();
        linearParams.height = (int)((float)width*0.7);
        iv.setLayoutParams(linearParams);
    }
}
