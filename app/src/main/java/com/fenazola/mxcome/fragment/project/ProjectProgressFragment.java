package com.fenazola.mxcome.fragment.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.WatiPayEntry;
import com.fenazola.mxcome.entry.project.Mdec1Entry;
import com.fenazola.mxcome.entry.project.Mdec2Entry;
import com.fenazola.mxcome.entry.project.ProgressEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.ProjectStep1;
import com.fenazola.mxcome.widget.ProjectStep2;
import com.fenazola.mxcome.widget.ProjectStep3;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.FontUtils;
import com.zss.library.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zm on 2017/3/9.
 * 工程进度
 */

public class ProjectProgressFragment extends BaseFragment {

    private LinearLayout step_layout;
    private TextView tv_total_progress;
    private TextView tv_pay_amt;
    private TextView tv_building;
    private TextView tv_days;

    private TextView tv_project_msg;

    private TextView tv_location;
    private TextView tv_detype;

    private TextView tv_cart;
    private TextView tv_dlist;
    private View tv_days_layout;
    private TextView tv_video;


    private TextView tv_designer1;
    private TextView tv_designer2;
    private TextView tv_designer3;

    private ImageView img_project_show;

    private ProgressEntry proEntry;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_progress;
    }

    @Override
    public void initView() {
        super.initView();
        step_layout = (LinearLayout) findViewById(R.id.step_layout);
        tv_total_progress = (TextView) findViewById(R.id.tv_total_progress);
        tv_pay_amt = (TextView) findViewById(R.id.tv_pay_amt);
        tv_building = (TextView) findViewById(R.id.tv_building);
        tv_project_msg = (TextView) findViewById(R.id.tv_project_msg);
        tv_location = (TextView) findViewById(R.id.tv_location);
        tv_detype = (TextView) findViewById(R.id.tv_detype);
        tv_days = (TextView) findViewById(R.id.tv_days);
        tv_cart = (TextView) findViewById(R.id.tv_cart);
        tv_dlist = (TextView) findViewById(R.id.tv_dlist);
        tv_days_layout = findViewById(R.id.tv_days_layout);
        tv_video = (TextView) findViewById(R.id.tv_video);
        tv_designer1 = (TextView) findViewById(R.id.tv_designer1);
        tv_designer2 = (TextView) findViewById(R.id.tv_designer2);
        tv_designer3 = (TextView) findViewById(R.id.tv_designer3);
        img_project_show = (ImageView) findViewById(R.id.img_project_show);

        FontUtils.applyFont(tv_total_progress);
        FontUtils.applyFont(tv_days);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle bundle = getArguments();
        String pid = bundle.getString(Constant.key1);
        LogUtils.i("pid------", pid);

        getData(pid);
        step_layout.setVisibility(View.GONE);
        img_project_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (step_layout.isShown()) {
                    step_layout.setVisibility(View.GONE);
                    img_project_show.setImageResource(R.mipmap.project_progress_down);
                } else {
                    step_layout.setVisibility(View.VISIBLE);
                    img_project_show.setImageResource(R.mipmap.project_progress_up);
                }
            }
        });
    }

    private void getData(String pid) {
        HashMap<String, String> map = new HashMap<>();
        UserEntry userEntry = Utils.getUserEntry();
        map.put("user_id", userEntry.getUser_id());
        map.put("pid", pid);
        //map.put("pid","e5ab26bb555c4df7a104afb6c9d41195");
        NetWorkUtils.post(getActivity(), "webRpcMng/queryAppGcxq.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                proEntry = GsonUtils.getObjFromJSON(result, ProgressEntry.class);
                refreshUI();
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });

    }

    public void refreshUI(){
        step_layout.removeAllViews();
        if(proEntry != null && proEntry.getMdec1() != null){
            tv_days.setText(proEntry.getZgq());
            String payed = proEntry.getMdec1().getPayed_price()== null? "0.00": proEntry.getMdec1().getPayed_price();
            tv_pay_amt.setText(getString(R.string.project_pay_amt, payed));
            String press = proEntry.getMdec1().getTotal_press() == null? "00": proEntry.getMdec1().getTotal_press();
            tv_total_progress.setText(press);
            tv_location.setText(proEntry.getMdec1().getAddress());
            tv_detype.setText(proEntry.getMdec1().getDeName());
        }
        if(proEntry.getMdec2() != null && proEntry.getMdec2().size() > 0){
            if(proEntry.getMdec2().size() == 1){
                tv_building.setText(proEntry.getMdec2().get(0).getStage()+"项\n施工中");
            }else{
                tv_building.setText(proEntry.getMdec2().size()+"项\n施工中");
            }
            for(Mdec2Entry item : proEntry.getMdec2()){
                if(item.getState() == 1 || item.getState() == 2){
                    ProjectStep2 step2 = new ProjectStep2(getActivity());
                    step2.setMdec2Entry(item);
                    step_layout.addView(step2);
                }else {
                    ProjectStep1 step1 = new ProjectStep1(getActivity());
                    step1.setMdec2Entry(item);
                    step_layout.addView(step1);
                }
            }
            if(proEntry.getMdec1() != null){
                int statu = proEntry.getMdec1().getStatu();
                if(statu == 2 || statu == 3){
                    ProjectStep3 step3 = new ProjectStep3(getActivity());
                    step3.setPauseText("暂停", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    step_layout.addView(step3);
                }else if(statu == 4){
                    ProjectStep3 step3 = new ProjectStep3(getActivity());
                    step3.setPauseText("暂停中", null);
                    step_layout.addView(step3);
                }else if(statu == 5){
                    ProjectStep3 step3 = new ProjectStep3(getActivity());
                    step3.setPauseText("竣工", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            comfirm();
                        }
                    });
                    step_layout.addView(step3);
                }else if(statu == 6){
                    ProjectStep3 step3 = new ProjectStep3(getActivity());
                    step3.setPauseText("评论", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WorkerListFragment fragment = new WorkerListFragment();
                            Bundle args = new Bundle();
                            args.putString(Constant.key1, proEntry.getMdec1().getPid());
                            fragment.setArguments(args);
                            addFragment(fragment);
                        }
                    });
                    step_layout.addView(step3);
                }
            }
        } else {
            tv_building.setText("0项\n施工中");
        }
    }

    public void comfirm(){
        Map<String, String> map = new HashMap();
        UserEntry user = Utils.getUserEntry();
        map.put("mxcome_no", user.getMxcome_no());
        map.put("pid", proEntry.getMdec1().getPid());
        NetWorkUtils.post(getActivity(), "decora/confirmFinish.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                proEntry.getMdec1().setStatu(6);
                refreshUI();
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle(R.string.project_detail);
    }
}
