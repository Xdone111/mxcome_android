package com.fenazola.mxcome.fragment.project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.ScheduParamEntry;
import com.fenazola.mxcome.entry.SchemeEntry;
import com.fenazola.mxcome.entry.SmartApplianceEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.WorkDesignerDetailEntry;
import com.fenazola.mxcome.entry.WorkerEntry;
import com.fenazola.mxcome.entry.project.OrderEntry;
import com.fenazola.mxcome.fragment.main.demand.DemandDesign;
import com.fenazola.mxcome.utils.DataCache;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.PayStyleDialog;
import com.fenazola.mxcome.widget.ScheduMaterialLayout;
import com.fenazola.mxcome.widget.ScheduWorkerLayout;
import com.fenazola.mxcome.widget.ScheduleDialog;
import com.fenazola.mxcome.widget.TextSeekbar;
import com.google.gson.reflect.TypeToken;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.indicator.UnderlinePageIndicator;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.widget.CommonDialog;
import com.zss.library.widget.CommonSwitchWidget;
import com.zss.library.widget.CommonWhiteDialog;
import com.zss.library.widget.CommonWidget;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工程排班
 *
 * @author zm
 */
public class ProjectScheduFragment extends BaseFragment implements View.OnClickListener {

    private int DEC_TYPE_CODE = 3;
    private boolean workConfig = true;
    private ScheduParamEntry choiceParamEntry;

    private ScheduWorkerLayout workerLayout;  //人员配置布局
    private ScheduMaterialLayout materialLayout; //材料配置布局

    private TextView house_info_tips; //房屋地址
    private TextView house_type_tips; //选择房屋类型
    private TextView dec_type_tips; //施工类型
    private Button authority; //全官方
    private Button recommend; //全推荐
    private TextView see_materail; //查看清单
    private TextView start_project; //立即施工

    private JSONObject workerObj = new JSONObject(); //全推荐 -2，// 全官方 -1
    private JSONObject material_choice_1001 = new JSONObject();  //主材包数组 为空未选择
    private JSONObject material_choice_1002 = new JSONObject();  //吊顶包数组 为空未选择
    private JSONObject material_choice_1003 = new JSONObject();  //背景包数组 为空未选择
    private JSONObject material_choice_1004 = new JSONObject();  //软配包数组 为空未选择
    private JSONObject material_choice_1005 = new JSONObject();  //家具包数组 为空未选择
    private JSONObject material_choice_1006 = new JSONObject();  //铺贴包数组 为空未选择

    private OrderEntry order;
    private LinearLayout second_ll;
    PayStyleDialog.MyListener myListener;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_project_schedu;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.colorBlue);
        workerLayout = (ScheduWorkerLayout) findViewById(R.id.workerLayout);
        materialLayout = (ScheduMaterialLayout) findViewById(R.id.materialLayout);

        house_info_tips = (TextView) findViewById(R.id.house_info_tips);
        house_type_tips = (TextView) findViewById(R.id.house_type_tips);
        dec_type_tips = (TextView) findViewById(R.id.dec_type_tips);
        authority = (Button) findViewById(R.id.authority);
        recommend = (Button) findViewById(R.id.recommend);
        see_materail = (TextView) findViewById(R.id.see_materail);
        start_project = (TextView) findViewById(R.id.start_project);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        EventBus.getDefault().register(this);

        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            order = (OrderEntry) args.getSerializable(Constant.key1);
        }

        house_info_tips.setText(order.getHouseInfo());
        house_type_tips.setText(DataCache.dictMap.get(order.getOne_level_type()));
        if (DataCache.ENUM_CD_QIB.equals(order.getThree_level_type())) {
            DEC_TYPE_CODE = 1;
            dec_type_tips.setText("轻包方案");
        } else if (DataCache.ENUM_CD_BIB.equals(order.getThree_level_type())) {
            DEC_TYPE_CODE = 2;
            dec_type_tips.setText("半包方案");
        } else if (DataCache.ENUM_CD_QUB.equals(order.getThree_level_type())) {
            DEC_TYPE_CODE = 3;
            dec_type_tips.setText("全包方案");
        }
        authority.setOnClickListener(this); //全官方
        recommend.setOnClickListener(this); //全推荐

        see_materail.setOnClickListener(this);
        start_project.setOnClickListener(this); //立即施工

        workerLayout.setTitleItem1(getString(R.string.project_schedu_worker1));
        workerLayout.setTitleItem2(getString(R.string.project_schedu_worker2));
        workerLayout.setTitleItem3(getString(R.string.project_schedu_worker3));
        workerLayout.setTitleItem4(getString(R.string.project_schedu_worker4));
        workerLayout.setTitleItem5(getString(R.string.project_schedu_worker5));
        workerLayout.setTitleItem6(getString(R.string.project_schedu_worker6));
        workerLayout.setTitleItem7(getString(R.string.project_schedu_worker7));
        workerLayout.setOnSelectListener(new ScheduleDialog.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                if (position == 1) {
                    workerLayout.setChoiceValue1("官方");
                }
            }
        });

        HashMap<String, String> map = new HashMap<>();
        UserEntry user = Utils.getUserEntry();
        map.put("user_id", user.getUser_id());
        NetWorkUtils.post(getActivity(), "hrpc/getPrePushDataForV1.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<ScheduParamEntry> datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<ScheduParamEntry>>() {
                }.getType());
                for (ScheduParamEntry item : datas) {
                    if (item.getRequest_code() == DEC_TYPE_CODE) {
                        choiceParamEntry = item;
                        setClickEnable();
                        break;
                    }
                }
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
        try {
            workerObj = new JSONObject();
            workerObj.put("800", "-1");
            workerObj.put("801", "-1");
            workerObj.put("802", "-1");
            workerObj.put("803", "-1");
            workerObj.put("804", "-1");
            workerObj.put("805", "-1");
            workerObj.put("806", "-1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        refreshUI();
        myListener = new PayStyleDialog.MyListener() {

            @Override
            public void buyOrder(String text) {
                generateOrder();
            }
        };
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE); //隐藏框架Toolbar
        CommonToolbar topBar = (CommonToolbar) findViewById(R.id.topBar);
        topBar.setRightImage(R.mipmap.question_w);
        topBar.setOnLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!workConfig) {
                    workConfig = true;
                    refreshUI();
                } else {
                    backStackFragment();
                }
            }
        });
        topBar.setBgColor(getColor(R.color.trans));
        topBar.setTitle(getString(R.string.project_scehdu_system));
    }

    @Override
    public boolean onBackPressed() {
        if (!workConfig) {
            workConfig = true;
            refreshUI();
            return true;
        } else {
            return super.onBackPressed();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        DataCache.clear();
    }

    public void refreshUI() {
        if (workConfig) {
            workerLayout.setVisibility(View.VISIBLE);
            materialLayout.setVisibility(View.GONE);
            see_materail.setVisibility(View.GONE);
            start_project.setText("下一步");
            if (DEC_TYPE_CODE == 3) {
                authority.setVisibility(View.GONE);
                recommend.setVisibility(View.GONE);
            } else {
                authority.setVisibility(View.VISIBLE);
                recommend.setVisibility(View.VISIBLE);
            }
        } else {
            workerLayout.setVisibility(View.GONE);
            materialLayout.setVisibility(View.VISIBLE);
            see_materail.setVisibility(View.VISIBLE);
            start_project.setText("立即施工");
            authority.setVisibility(View.GONE);
            recommend.setVisibility(View.GONE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Message msg) {
        if (msg.what == 1) {  //选设计
            WorkDesignerDetailEntry work = (WorkDesignerDetailEntry) msg.obj;
            int lastCheck = workerLayout.getWtype();
            if (lastCheck == 0) {
                workerLayout.setChoiceValue1("官方");
                return;
            }
            if (lastCheck == 800) {
                workerLayout.setChoiceValue1(work.getBase().getNick_name());
            }
            try {
                workerObj.put(String.valueOf(workerLayout.getWtype()), work.getBase().getPv_id());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (msg.what == 2) { //选材
            LogUtils.i("---zss---", "-----" + DataCache.material1001);
            LogUtils.i("---zss---", "-----" + DataCache.material1002);
            LogUtils.i("---zss---", "-----" + DataCache.material1003);
            LogUtils.i("---zss---", "-----" + DataCache.material1004);
            LogUtils.i("---zss---", "-----" + DataCache.material1005);
            LogUtils.i("---zss---", "-----" + DataCache.material1006);
            LogUtils.i("---zss---", "-----" + DataCache.material10070001);
            LogUtils.i("---zss---", "-----" + DataCache.material10070002);
            if (DataCache.material1001.size() > 0) {
                materialLayout.setChoiceValue1("已选择");
                material_choice_1001 = mapToJson(DataCache.material1001);
            }
            if (DataCache.material1002.size() > 0) {
                materialLayout.setChoiceValue2("已选择");
                material_choice_1002 = mapToJson(DataCache.material1002);
            }
            if (DataCache.material1003.size() > 0) {
                materialLayout.setChoiceValue3("已选择");
                material_choice_1003 = mapToJson(DataCache.material1003);
            }
            if (DataCache.material1004.size() > 0) {
                materialLayout.setChoiceValue4("已选择");
                material_choice_1004 = mapToJson(DataCache.material1004);
            }
            if (DataCache.material1005.size() > 0) {
                materialLayout.setChoiceValue5("已选择");
                material_choice_1005 = mapToJson(DataCache.material1005);
            }
            if (DataCache.material1006.size() > 0) {
                materialLayout.setChoiceValue6("已选择");
                material_choice_1006 = mapToJson(DataCache.material1006);
            }
//            if (DataCache.material10070001.size() > 0 || DataCache.material10070002.size() > 0) {
//                materialLayout.setChoiceValue7("已选择");
//            }
        }
    }

    public JSONObject mapToJson(HashMap<String, String> map) {
        JSONObject obj = new JSONObject();
        for (Map.Entry<String, String> item : map.entrySet()) {
            try {
                obj.put(item.getKey(), item.getValue());
            } catch (JSONException e) {
            }
        }
        return obj;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.authority:
//                workerLayout.setChoiceValue1("官方");
//                workerLayout.setChoiceValue2("官方");
//                workerLayout.setChoiceValue3("官方");
//                workerLayout.setChoiceValue4("官方");
//                workerLayout.setChoiceValue5("官方");
//                workerLayout.setChoiceValue6("官方");
//                workerLayout.setChoiceValue7("官方");
//                try {
//                    workerObj = new JSONObject();
//                    workerObj.put("800","-1");
//                    workerObj.put("801","-1");
//                    workerObj.put("802","-1");
//                    workerObj.put("803","-1");
//                    workerObj.put("804","-1");
//                    workerObj.put("805","-1");
//                    workerObj.put("806","-1");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            case R.id.recommend:
//                workerLayout.setChoiceValue1("推荐");
//                workerLayout.setChoiceValue2("推荐");
//                workerLayout.setChoiceValue3("推荐");
//                workerLayout.setChoiceValue4("推荐");
//                workerLayout.setChoiceValue5("推荐");
//                workerLayout.setChoiceValue6("推荐");
//                workerLayout.setChoiceValue7("推荐");
//                try {
//                    workerObj = new JSONObject();
//                    workerObj.put("800","-2");
//                    workerObj.put("801","-2");
//                    workerObj.put("802","-2");
//                    workerObj.put("803","-2");
//                    workerObj.put("804","-2");
//                    workerObj.put("805","-2");
//                    workerObj.put("806","-2");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
            case R.id.see_materail:
//                SeeMaterailFragment fragment = new SeeMaterailFragment();
//                addFragment(fragment);
                break;
            case R.id.start_project:
                if (workConfig) {
                    workConfig = false;
                    refreshUI();
                } else {
                    initAdv();
                }
                break;
        }
    }

    /**
     * 一站式采购 硬广
     */
    private void initAdv() {
        View view = getLayoutInflater(R.layout.layout_schedu_caigou);
        CommonWhiteDialog dialog = new CommonWhiteDialog(getActivity());
        dialog.setTitle("一站式采购");
        dialog.setMiddleView(view);
        dialog.setOnClickCancelListener("立即施工", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initSection();

            }
        });
        dialog.setOnClickConfirmListener("继续选材", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        dialog.show();
    }

    /**
     * 付款比例
     */
    private void initSection() {
        PayStyleDialog dialog = new PayStyleDialog(getActivity(),myListener);
        dialog.show();
    }

    public void setClickEnable() {
        List<SchemeEntry> workers = choiceParamEntry.getWorker();
        if (workers != null) {
            workerLayout.setClickEnable(workers);
        }
        List<SchemeEntry> materials = choiceParamEntry.getMaterial();
        if (materials != null) {
            DataCache.enableList = choiceParamEntry.getMaterialEnable();
            DataCache.disableList = choiceParamEntry.getMaterialDisable();
            materialLayout.setClickEnable(materials);
        }
    }

    public void generateOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("pid", order.getHouseId());// 房屋id
        map.put("advance", order.getAdvance());// 预算
        map.put("plan_id", order.getPlan_id());// 套餐id
        SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
        UserEntry user = (UserEntry) prefUtils.get("user");
        map.put("mxcome_no", user.getMxcome_no());// 用户id
        map.put("one_level_type", order.getOne_level_type()); // 装修规模 整体，还是模块
        map.put("two_level_type", order.getTwo_level_type()); // 装修房屋类型 商品房
        map.put("three_level_type", order.getThree_level_type()); // 装修施工类型 全包半包
        map.put("dec_grade", order.getDec_grade());// 装修品位 奢，豪，舒，简
        map.put("dec_style", order.getDec_style()); // 装修风格
        map.put("ruz_time", order.getRuz_time());// 用户入住时间
        map.put("has_demand", order.getHas_demand());
        JSONObject obj = new JSONObject();
        try {
            obj.put("worker_choice_ids", workerObj.toString());
            obj.put("material_choice_1001", material_choice_1001.toString());
            obj.put("material_choice_1002", material_choice_1002.toString());
            obj.put("material_choice_1003", material_choice_1003.toString());
            obj.put("material_choice_1004", material_choice_1004.toString());
            obj.put("material_choice_1005", material_choice_1005.toString());
            obj.put("material_choice_1006", material_choice_1006.toString());
            String material10070001 = "";
            String material10070002 = "";
            if (DataCache.material10070001.size() > 0) {
                JSONArray arrs = new JSONArray();
                for (SmartApplianceEntry item : DataCache.material10070001) {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("product_id", item.getProduct_id());
                        json.put("brand_id", item.getBrand_id());
                        json.put("item_id", item.getItem_id());
                    } catch (Exception e) {
                    }
                    arrs.put(json);
                }
                material10070001 = arrs.toString();
            }
            if (DataCache.material10070002.size() > 0) {
                JSONArray arrs = new JSONArray();
                for (SmartApplianceEntry item : DataCache.material10070002) {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("product_id", item.getProduct_id());
                        json.put("brand_id", item.getBrand_id());
                        json.put("item_id", item.getItem_id());
                    } catch (Exception e) {
                    }
                    arrs.put(json);
                }
                material10070002 = arrs.toString();
            }
            obj.put("material_choice_10070001", material10070001);
            obj.put("material_choice_10070002", material10070002);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        map.put("mxcome_params", obj.toString());
        NetWorkUtils.post(getActivity(), "hrpc/pushOrderData.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                JSONObject res = resObj.optJSONObject("res");
                order.setMx_order_no(res.optString("mx_order_no"));
                order.setTotal_amount(res.optString("total_amount"));
                order.setDis_amount(res.optString("dis_amount"));
                order.setOrdertime(res.optString("ordertime"));
                order.setTimeout(res.optInt("timeout"));
                Bundle args = new Bundle();
                args.putSerializable(Constant.key1, order);
                ProjectDownpayFragment fragment = new ProjectDownpayFragment();
                fragment.setArguments(args);
                addFragment(fragment);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });

    }
}
