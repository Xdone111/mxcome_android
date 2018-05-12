package com.fenazola.mxcome.fragment.me.myproject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.project.EstateEntry;
import com.fenazola.mxcome.entry.project.OrderEntry;
import com.fenazola.mxcome.entry.project.PremiseEntry;
import com.fenazola.mxcome.fragment.project.ProjectDemandChoiceFragment;
import com.fenazola.mxcome.fragment.project.ProjectInputInfoFragment;
import com.fenazola.mxcome.utils.Constant;
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
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.widget.CommonDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XDONE on 2017/4/20.
 * 待建工程
 */

public class WaitNewProjectFragment extends BaseFragment {
    private ListView listView;
    private CommonAdapter<PremiseEntry> adapter;
    private PtrClassicFrameLayout mPtrFrame;
    private TextView new_create_info;
    private RelativeLayout noProgjectRy;
    private LinearLayout linear;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_wait_new_project;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.mPtrFrame);
        new_create_info = (TextView) findViewById(R.id.new_create_info);
        noProgjectRy = (RelativeLayout) findViewById(R.id.show_no_project_ry);
        linear = (LinearLayout) findViewById(R.id.linear);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        EventBus.getDefault().register(this);
        adapter = new CommonAdapter<PremiseEntry>(getActivity(), R.layout.listitem_wait_project) {
            @Override
            protected void convert(ViewHolder viewHolder, final PremiseEntry item, int position) {
                TextView tv_name = viewHolder.findViewById(R.id.tv_name);
                tv_name.setText(item.getHouseInfo());
                TextView tv_pcd = viewHolder.findViewById(R.id.tv_addr);
                tv_pcd.setText(item.getCity() + " " + item.getCounty());
                TextView edit_project = viewHolder.findViewById(R.id.edit_project);
                TextView houseAddr = viewHolder.findViewById(R.id.house_addr);
                EstateEntry estate = item.getmDecora().getEstate();
                houseAddr.setText(item.getAddress() + estate.getRemark() + estate.getDongNo() + "栋" + estate.getFloor() + "室");
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
                        args.putSerializable(Constant.key1, order);
                        ProjectDemandChoiceFragment fragment = new ProjectDemandChoiceFragment();
                        fragment.setArguments(args);
                        addFragment(fragment);
                    }
                });
                TextView tv_mine_order = viewHolder.findViewById(R.id.tv_mine_order);
                tv_mine_order.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                TextView tv_delete = viewHolder.findViewById(R.id.tv_delete);
                if (item.getOrderNum() > 0) {
                    tv_delete.setTextColor(getColor(R.color.colorGrey));
                    Drawable left = ActivityCompat.getDrawable(getContext(), R.mipmap.my_project_no_delete);
                    tv_delete.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    tv_delete.setOnClickListener(null);
                } else {
                    tv_delete.setTextColor(getColor(R.color.colorBlue));
                    Drawable left = ActivityCompat.getDrawable(getContext(), R.mipmap.my_project_delete);
                    tv_delete.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    tv_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog(item);
                        }
                    });
                }
            }
        };
        listView.setAdapter(adapter);
        // Utils.setEmptyView(listView);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, listView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                reloadData(null);
            }
        });
        mPtrFrame.setPullToRefresh(false);
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        new_create_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectInputInfoFragment fragment = new ProjectInputInfoFragment();
                addFragment(fragment);
            }
        });

        reloadData(null);
    }

    public void showDialog(final PremiseEntry item) {
        CommonDialog dialog = new CommonDialog(getActivity());
        dialog.setTitle("确认删除");
        dialog.setContentText("您确认要删除该房屋吗？");
        dialog.setOnClickCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.setOnClickConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHouse(item);
            }
        });
        dialog.show();
    }

    public void deleteHouse(final PremiseEntry item) {
        HashMap<String, String> map = new HashMap<>();
        map.put("houseId", item.getmDecora().getHouseId());
        NetWorkUtils.post(getActivity(), "houseInfo.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                adapter.remove(item);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reloadData(PremiseEntry entry) {
        SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
        UserEntry user = (UserEntry) prefUtils.get("user");
        Map<String, String> map = new HashMap<>();
        map.put("user_id", user.getUser_id());
        NetWorkUtils.post(getActivity(), "appApi/fDecoraInfo.do", map, false, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<PremiseEntry> datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<PremiseEntry>>() {
                }.getType());
                adapter.replaceAll(datas);
                mPtrFrame.refreshComplete();
                if (datas != null && datas.size() > 0) {
                    linear.setVisibility(View.VISIBLE);
                    noProgjectRy.setVisibility(View.GONE);

                } else {
                    noProgjectRy.setVisibility(View.VISIBLE);
                    linear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(String result, String code, String msg) {
                mPtrFrame.refreshComplete();
                noProgjectRy.setVisibility(View.VISIBLE);
                linear.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
