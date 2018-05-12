package com.fenazola.mxcome.fragment.me.setting;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.CouponEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.swipelayout.SwipeRevealLayout;
import com.zss.library.swipelayout.ViewBinderHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优惠券
 * Created by xhx on 2017/8/10.
 */

public class Coupon1Fragment extends BaseFragment {
    private CommonAdapter<CouponEntry> mAdapter;
    private ListView listView;
    private ViewBinderHelper binderHelper;
    private int pos=0;
    private int type=0;

    List<CouponEntry>names=new ArrayList<CouponEntry>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_coupon;
    }

    @Override
    public void initView() {
        super.initView();
        listView=(ListView)findViewById(R.id.listView);
        type=getArguments().getInt(Constant.key1);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                initDates();
//            }
//        }, 100);

    }

    private void initDates() {
        //TODO 模拟数据

        getDiscount();
        initChangList();

    }

    private void initChangList() {
        binderHelper = new ViewBinderHelper();
        mAdapter = new CommonAdapter<CouponEntry>(getActivity(), R.layout.layout_coupon_item1) {
            @Override
            protected void convert(ViewHolder viewHolder, final CouponEntry couponEntry,final int i) {
                TextView tv1 = viewHolder.findViewById(R.id.show_monery_tv);
                TextView tv2 = viewHolder.findViewById(R.id.show_monery_tv1);
                TextView tv3 = viewHolder.findViewById(R.id.show_monery_tv2);
                TextView tv4 = viewHolder.findViewById(R.id.show_monery_tv3);
                TextView tv5 = viewHolder.findViewById(R.id.show_monery_tv4);
                ImageView iv = viewHolder.findViewById(R.id.show_get_iv);
                LinearLayout bg=viewHolder.findViewById(R.id.show_bg);
                tv1.setText(couponEntry.getCoupon_fee());
                tv3.setText(couponEntry.getCoupon_name());
                tv4.setText(couponEntry.getEnd_time());
                tv5.setText(couponEntry.getDescribe());
                if(i%4==0){
                    tv1.setTextColor(getColor(R.color.colorRed));
                    tv2.setTextColor(getColor(R.color.colorRed));
                    bg.setBackgroundResource(R.mipmap.youhui_red);
                }else  if(i%4==1){
                    tv1.setTextColor(getColor(R.color.colorYellow));
                    tv2.setTextColor(getColor(R.color.colorYellow));
                    bg.setBackgroundResource(R.mipmap.youhui_yellow);
                }else  if(i%4==2){
                    tv1.setTextColor(getColor(R.color.colorGreen));
                    tv2.setTextColor(getColor(R.color.colorGreen));
                    bg.setBackgroundResource(R.mipmap.youhui_lv);
                }else{
                    tv1.setTextColor(getColor(R.color.topic_text_color6));
                    tv2.setTextColor(getColor(R.color.topic_text_color6));
                    bg.setBackgroundResource(R.mipmap.youhui_zi);
                }
                if(couponEntry.getCoupon_type().equals("1")){
                    tv2.setText("￥");
                }else{
                    tv2.setText("折");

                }
                if(type==0){
                    iv.setVisibility(View.INVISIBLE);
                }else if(type==1){
                    iv.setVisibility(View.VISIBLE);
                    iv.setImageResource(R.mipmap.youhui_shiyong);
                }else {
                    iv.setVisibility(View.VISIBLE);
                    iv.setImageResource(R.mipmap.youhui_shixiao);

                }

                SwipeRevealLayout swipe_layout = viewHolder.findViewById(R.id.swipe_layout);
                binderHelper.bind(swipe_layout, String.valueOf(i));
                binderHelper.setOpenOnlyOne(true);
                viewHolder.findViewById(R.id.delete_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDiscount(i);
//                        if(i==names.size()-1&&names.size()>1){
//                            pos=i-1;
//                        }else{
//                            pos=i;
//                        }

                    }
                });
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //addCoupon(i);
                    }
                });
            }
        };
        listView.setAdapter(mAdapter);
        mAdapter.addAll(names);
        //记录listview上次滑动到的位置  因为要涉及到 重载页面 以此来隐藏侧滑三处按钮
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            /**
             * 滚动状态改变时调用
             */
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // 不滚动时保存当前滚动到的位置
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    pos = listView.getFirstVisiblePosition();
                }
            }

            /**
             * 滚动时调用
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        listView.setSelection(pos);

    }
    /**添加优惠券*/
    private void addCoupon(int i) {
        Map<String, String> map = new HashMap<>();
        UserEntry userEntry= Utils.getUserEntry();
        map.put("elc_id", names.get(i).getElc_id());
        map.put("user_id",userEntry.getUser_id());
        NetWorkUtils.postUrl(getActivity(), Constant.newBaseUrl+"addEleCouponUser", map, false, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                Toast.makeText(getActivity(),"领取优惠券成功",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String result, String code, String msg) {
                Toast.makeText(getActivity(),"领取优惠券失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /***
     *获取优惠券
     *
     */
    private void getDiscount(){
        Map<String, String> map = new HashMap<>();
        UserEntry userEntry= Utils.getUserEntry();
        map.put("user_id", userEntry.getUser_id());

        NetWorkUtils.postUrl(getActivity(), Constant.newBaseUrl+"elecoupons/list", map, false, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                names= GsonUtils.getListFromJSON(res,CouponEntry.class);
                // TODO: 2017/8/10  模拟数据
                for(int i=0;i<20;i++){
                        names.add(new CouponEntry("111"+i,"yyyy-MM-dd HH:0"+i,"10000"+i,"200204"+i,
                                "yyyy-MM-dd HH:0"+i,"好厉害的样子呀"+i,"本宝宝不知道些什么"+i,
                                "1","优惠券"+i,"10"+i,"几号放假上过课","100"+i));

                }
                initChangList();
            }

            @Override
            public void onError(String result, String code, String msg) {
                // TODO: 2017/8/10
                for(int i=0;i<20;i++){
                    names.add(new CouponEntry("111"+i,"yyyy-MM-dd HH:0"+i,"1"+i,"200204"+i,
                            "yyyy-MM-dd HH:0"+i,"好厉害的样子呀"+i,"本宝宝不知道些什么"+i,
                            "1","优惠券"+i,"10"+i,"几号放假上过课","100"+i));

                }
                initChangList();
            }
        });
    }

    /***
     *删除优惠券
     *
     */
    private void deleteDiscount(final int elcId){
        Map<String, String> map = new HashMap<>();
        map.put("id", names.get(elcId).getId());

        NetWorkUtils.postUrl(getActivity(), Constant.newBaseUrl+"delEleCouponUser", map, false, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                names.remove(names.get(elcId));
                initChangList();
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}