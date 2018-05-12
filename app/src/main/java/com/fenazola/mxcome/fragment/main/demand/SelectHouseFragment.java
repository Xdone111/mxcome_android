package com.fenazola.mxcome.fragment.main.demand;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.project.OrderEntry;
import com.fenazola.mxcome.entry.project.PremiseEntry;
import com.fenazola.mxcome.fragment.project.ProjectInputInfoFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;
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
 * Created by XDONE on 2017/3/23.
 * 选择工地
 */

public class SelectHouseFragment extends BaseFragment {

    private ListView listView;
    private CommonAdapter<PremiseEntry> adapter;
    private List<PremiseEntry> datas;
    private int pos = -1;
    private TextView confirm;
    private OrderEntry order;
    private boolean isFirst = true;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_select_house;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
        confirm = (TextView) findViewById(R.id.confirm);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            order = (OrderEntry) args.getSerializable(Constant.key1);
        }

        adapter = new CommonAdapter<PremiseEntry>(getActivity(), R.layout.listitem_select_site) {
            @Override
            protected void convert(ViewHolder viewHolder, PremiseEntry entry, int i) {
                TextView name = viewHolder.findViewById(R.id.name);
                name.setText(entry.getHouseInfo());
                TextView addr = viewHolder.findViewById(R.id.addr);
                addr.setText(entry.getAddrInfo());
                ImageView check = viewHolder.findViewById(R.id.check);
                String houseId = entry.getmDecora().getHouseId();
                if (order != null && isFirst) {
                    String houseId1 = order.getHouseId();
                    if (houseId1.equals(houseId)) {
                        pos = i;
                        isFirst = false;
                    }
                }
                if (pos == i) {
                    check.setImageResource(R.mipmap.icon_seletct_add);
                } else {
                    check.setImageResource(R.drawable.corner_shape_grey);
                }
            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                adapter.notifyDataSetChanged();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pos >= 0) {
                    EventBus.getDefault().post(datas.get(pos).getmDecora());
                }
                getActivity().finish();
            }
        });

        SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
        UserEntry user = (UserEntry) prefUtils.get("user");
        Map<String, String> map = new HashMap<>();
        map.put("user_id", user.getUser_id());
        NetWorkUtils.post(getActivity(), "appApi/fDecoraInfo.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<PremiseEntry>>() {
                }.getType());
                adapter.replaceAll(datas);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setRightImage(R.mipmap.icon_add);
        toolbar.setTitle("选择工地");
        toolbar.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectInputInfoFragment fragment = new ProjectInputInfoFragment();
                addFragment(fragment);
            }
        });
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
                datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<PremiseEntry>>() {
                }.getType());
                adapter.replaceAll(datas);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
