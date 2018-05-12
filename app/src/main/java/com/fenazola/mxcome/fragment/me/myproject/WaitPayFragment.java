package com.fenazola.mxcome.fragment.me.myproject;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.WatiPayEntry;
import com.fenazola.mxcome.entry.project.PremiseEntry;
import com.fenazola.mxcome.utils.DateUtils;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.ptr.PtlmDefaultHandler;
import com.zss.library.ptr.PtrClassicFrameLayout;
import com.zss.library.ptr.PtrDefaultHandler;
import com.zss.library.ptr.PtrFrameLayout;
import com.zss.library.ptr.PtrHandler;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.StringUtils;
import com.zss.library.widget.CommonDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by XDONE on 2017/4/20.
 * 已建工程
 */

public class WaitPayFragment extends BaseFragment {

    private ListView listView;
    private CommonAdapter<WatiPayEntry> adapter;
    private PtrClassicFrameLayout mPtrFrame;
    private SimpleDateFormat foramt = new SimpleDateFormat("mm:ss");

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_wait_pay;
    }

    @Override
    public void initView() {
        super.initView();
        listView = (ListView) findViewById(R.id.listView);
        mPtrFrame = (PtrClassicFrameLayout)findViewById(R.id.mPtrFrame);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        adapter = new CommonAdapter<WatiPayEntry>(getActivity(), R.layout.listitem_wait_pay_project) {
            @Override
            protected void convert(ViewHolder viewHolder, final WatiPayEntry item, int position) {
                TextView tv_name = viewHolder.findViewById(R.id.tv_name);
                tv_name.setText(item.getTitle());
                TextView tv_title = viewHolder.findViewById(R.id.tv_time);
                tv_title.setText("发布时间 "+ DateUtils.getTime(item.getAdd_time()));
                TextView house_type = viewHolder.findViewById(R.id.house_type);
                house_type.setText(item.getProjectOrderInfo());
                TextView orderNo = viewHolder.findViewById(R.id.order_no);
                orderNo.setText("订单号 " + item.getOrder_id());
                TextView copy = viewHolder.findViewById(R.id.copy);
                TextView timeout = viewHolder.findViewById(R.id.timeout);
                TextView pay = viewHolder.findViewById(R.id.pay);
                copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setPrimaryClip(ClipData.newPlainText(null, item.getOrder_id()));
                        Toast.makeText(getActivity(), "复制成功", Toast.LENGTH_SHORT).show();
                    }
                });
                ImageView img_delete = viewHolder.findViewById(R.id.img_delete);
                if(item.getOrder_statu() == 0){
                    img_delete.setVisibility(View.VISIBLE);
                    img_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDialog(item);
                        }
                    });
                    if(item.getIs_timeout() == 0){
                        setTimeout(timeout, 30, item.getAdd_time());
                        pay.setVisibility(View.VISIBLE);
                        pay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }else{
                        timeout.setText("已超时");
                        pay.setVisibility(View.GONE);
                        pay.setOnClickListener(null);
                    }
                } else {
                    img_delete.setVisibility(View.GONE);
                    img_delete.setOnClickListener(null);
                    timeout.setVisibility(View.GONE);
                    pay.setVisibility(View.VISIBLE);
                    pay.setText("定金已付\n等待服务");
                }

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
        handler.postDelayed(runnable, 1000);
        reloadData();
    }


    public void reloadData(){
        UserEntry user = Utils.getUserEntry();
        String mxcome_no = user.getMxcome_no();
        HashMap<String, String> map = new HashMap<>();
        map.put("mxcome_no", mxcome_no);
        map.put("statu", "0,1");
        NetWorkUtils.post(getActivity(), "decora/queryMyDecoraInfo.do", map, false, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<WatiPayEntry> datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<WatiPayEntry>>(){}.getType());
                adapter.replaceAll(datas);
                mPtrFrame.refreshComplete();
            }

            @Override
            public void onError(String result, String code, String msg) {
                mPtrFrame.refreshComplete();
            }
        });
    }

    public void showDialog(final WatiPayEntry item){
        CommonDialog dialog = new CommonDialog(getActivity());
        dialog.setTitle("确认删除");
        dialog.setContentText("您确认要删除该订单吗？");
        dialog.setOnClickCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.setOnClickConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOrder(item);
            }
        });
        dialog.show();
    }

    public void deleteOrder(final WatiPayEntry item){
        HashMap<String, String> map = new HashMap<>();
        map.put("orderId", item.getOrder_id());
        NetWorkUtils.post(getActivity(), "deleteOrder.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                adapter.remove(item);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public void setTimeout(TextView textView, int timeout, long orderTime) {
        long time = timeout * 60 * 1000;
        long duringTime = new Date().getTime() - new Date(orderTime).getTime();
        long countDownTime = time - duringTime;
        LogUtils.i("---zss---", " --- time = "+time + " --- countDownTime = " + countDownTime);
        String timeStr = foramt.format(countDownTime);
        textView.setText(timeStr);

    }
    private Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            adapter.notifyDataSetChanged();
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
