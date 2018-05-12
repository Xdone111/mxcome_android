package com.fenazola.mxcome.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.WatiPayEntry;
import com.fenazola.mxcome.fragment.main.tool.CalculateFragment;
import com.fenazola.mxcome.fragment.main.tool.CalendarFragment;
import com.fenazola.mxcome.fragment.main.tool.GradienterFragment;
import com.fenazola.mxcome.fragment.project.AfterSaleProtectionFragment;
import com.fenazola.mxcome.fragment.project.ProjectProgressFragment;
import com.fenazola.mxcome.fragment.project.ProjectWebFragment;
import com.fenazola.mxcome.fragment.project.monitor.SiteLiveFragment;
import com.fenazola.mxcome.fragment.project.scheme.SchemePictureFragment;
import com.fenazola.mxcome.fragment.sercentre.FaqTermsFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.CircleMenuLayout;
import com.fenazola.mxcome.widget.ToolMXLine;
import com.google.gson.reflect.TypeToken;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.FontUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 工程
 */
public class ProjectFragment extends BaseFragment implements View.OnClickListener {

    private View ll_progress;
    private TextView tv_progress;
    private TextView tv_addr;

    private CircleMenuLayout menuLayout;

    private String[] mItemTexts = new String[]{"方案", "监控", "进度", "工具"};

    private List<Integer> mItemTextColors = new ArrayList<>();
    private List<Integer> mItemIds = new ArrayList<>();

    private Button nav1, nav2, nav3;

    private WatiPayEntry curPrem;

    private String[] mToolTexts = new String[]{"水平", "计算", "黄历"};
    private Integer[] mToolTextColors = new Integer[]{R.color.colorBlue, R.color.colorBlue, R.color.colorBlue};
    private Integer[] mToolIds = new Integer[]{R.drawable.corner_shape_white, R.drawable.corner_shape_white, R.drawable.corner_shape_white};
    /**
     * 工具的 已舍弃 end
     */
    private ImageView mainMXImg;
    private ToolMXLine mx_line;
    private boolean toolMode = false;
    int width;
    private ImageView center_icon_toos;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_project;
    }

    @Override
    public void initView() {
        super.initView();
        WindowManager wm = getActivity().getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        menuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        ll_progress = findViewById(R.id.ll_progress);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_addr = (TextView) findViewById(R.id.tv_addr);
        nav1 = (Button) findViewById(R.id.nav1);
        nav2 = (Button) findViewById(R.id.nav2);
        nav3 = (Button) findViewById(R.id.nav3);
        mainMXImg = (ImageView) findViewById(R.id.mainMXImg);
        mx_line = (ToolMXLine) findViewById(R.id.mx_line);
        center_icon_toos = (ImageView) findViewById(R.id.center_icon_toos);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        FontUtils.applyFont(tv_progress);
        changeItemH();

        //mItemTextColors.add(R.color.colorDisabled);
        mItemTextColors.add(R.color.white);
        mItemTextColors.add(R.color.white);
        mItemTextColors.add(R.color.white);
        mItemTextColors.add(R.color.white);
        //mItemIds.add(R.drawable.main_small_corner_disabled);
        mItemIds.add(R.drawable.main_small_corner_bg_new);
        mItemIds.add(R.drawable.main_small_corner_bg_new);
        mItemIds.add(R.drawable.main_small_corner_bg_new);
        mItemIds.add(R.drawable.main_small_corner_bg_new);


        menuLayout.setMenuItemIconsAndTexts(mItemIds, mItemTextColors, Arrays.asList(mItemTexts));
        tv_addr.setText("点击这里\n创建工程");
        ll_progress.setVisibility(View.GONE);

        nav1.setOnClickListener(this);
        nav2.setOnClickListener(this);
        nav3.setOnClickListener(this);

        menuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                Intent intent;
                switch (pos) {
                    case 0:
                        if (toolMode) { //水平
                            intent = new Intent(getActivity(), ZFrameActivity.class);
                            intent.putExtra(ZFrameActivity.CLASS_NAME, GradienterFragment.class.getName());
                            startActivity(intent);
                        } else {
                            if (curPrem != null) {//方案
                                intent = new Intent(getActivity(), ZFrameActivity.class);
                                intent.putExtra(ZFrameActivity.CLASS_NAME, SchemePictureFragment.class.getName());
                                intent.putExtra(Constant.key1, curPrem);
                                Utils.startLogin(getActivity(), intent);
                            }
                        }
                        break;
                    case 1:
                        if (toolMode) { //计算
                            intent = new Intent(getActivity(), ZFrameActivity.class);
                            intent.putExtra(ZFrameActivity.CLASS_NAME, CalculateFragment.class.getName());
                            startActivity(intent);
                        } else {
                            //监控
                            intent = new Intent(getActivity(), ZFrameActivity.class);
                            intent.putExtra(ZFrameActivity.CLASS_NAME, SiteLiveFragment.class.getName());
                            startActivity(intent);
                        }
                        break;
                    case 2:
                        if (toolMode) { //黄历
                            intent = new Intent(getActivity(), ZFrameActivity.class);
                            intent.putExtra(ZFrameActivity.CLASS_NAME, CalendarFragment.class.getName());
                            startActivity(intent);
                        } else {
                            if (curPrem != null) {//进度
                                intent = new Intent(getActivity(), ZFrameActivity.class);
                                intent.putExtra(ZFrameActivity.CLASS_NAME, ProjectProgressFragment.class.getName());
                                intent.putExtra(Constant.key1, "100");
                                Utils.startLogin(getActivity(), intent);
                            }
                        }
                        break;
                    case 3:
//                        mItemTextColors.clear();
//                        mItemTextColors.add(R.color.colorBlue);
//                        mItemTextColors.add(R.color.colorBlue);
//                        mItemTextColors.add(R.color.colorBlue);
//                        mItemIds.clear();
//                        mItemIds.add(R.drawable.main_small_corner_bg);
//                        mItemIds.add(R.drawable.main_small_corner_bg);
//                        mItemIds.add(R.drawable.main_small_corner_bg);
                        menuLayout.removeAllItem();
                        menuLayout.setMenuItemIconsAndTexts(Arrays.asList(mToolIds), Arrays.asList(mToolTextColors), Arrays.asList(mToolTexts));
                        int lineX = (int) (mainMXImg.getPivotX() * 1.3);
                        mx_line.setLineX(lineX);
                        mainMXImg.setVisibility(View.VISIBLE);
                        toolMode = true;
                        center_icon_toos.setVisibility(View.VISIBLE);
                        tv_addr.setVisibility(View.INVISIBLE);
                        break;
                }


            }

            @Override
            public void itemCenterClick(View view) {
                if (curPrem == null) {
                    Utils.startNewProject(getActivity());
                } else {
                    Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                    intent.putExtra(ZFrameActivity.CLASS_NAME, ProjectProgressFragment.class.getName());
                    intent.putExtra(Constant.key1, curPrem.getPid());
                    Utils.startLogin(getActivity(), intent);
                }
            }

            @Override
            public void itemLongClick(View view, int pos) {

            }
        });
        refreshUI();
        mainMXImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuLayout.removeAllItem();
                mItemTextColors.clear();
                mItemTextColors.add(R.color.white);
                mItemTextColors.add(R.color.white);
                mItemTextColors.add(R.color.white);
                mItemTextColors.add(R.color.white);
                mItemIds.clear();
                mItemIds.add(R.drawable.main_small_corner_bg_new);
                mItemIds.add(R.drawable.main_small_corner_bg_new);
                mItemIds.add(R.drawable.main_small_corner_bg_new);
                mItemIds.add(R.drawable.main_small_corner_bg_new);
                menuLayout.setMenuItemIconsAndTexts(mItemIds, mItemTextColors, Arrays.asList(mItemTexts));
                mx_line.setLineX(-1);
                mainMXImg.setVisibility(View.INVISIBLE);
                center_icon_toos.setVisibility(View.GONE);
                tv_addr.setVisibility(View.VISIBLE);
                toolMode = false;
            }
        });
    }

    private void changeItemH() {
        ImageView iv = (ImageView) findViewById(R.id.iv_1);
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) iv.getLayoutParams();
        linearParams.height = (int) ((float) width * 0.53);
        iv.setLayoutParams(linearParams);
    }

    private void refreshUI() {
        if (Utils.isLogin()) {
            UserEntry user = Utils.getUserEntry();
            HashMap<String, String> map = new HashMap<>();
            map.put("mxcome_no", user.getMxcome_no());
            map.put("statu", "3");
            NetWorkUtils.post(getActivity(), "decora/queryMyDecoraInfo.do", map, new NetWorkUtils.IListener() {
                @Override
                public void onSuccess(String result, JSONObject resObj) {
                    String res = resObj.optString("res");
                    List<WatiPayEntry> datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<WatiPayEntry>>() {
                    }.getType());
                    menuLayout.removeAllItem();
                    mItemTextColors.clear();
                    mItemIds.clear();
                    if (datas != null && datas.size() > 0) {
                        mItemTextColors.add(R.color.white);
                        mItemTextColors.add(R.color.white);
                        mItemTextColors.add(R.color.white);
                        mItemIds.add(R.drawable.main_small_corner_bg_new);
                        mItemIds.add(R.drawable.main_small_corner_bg_new);
                        mItemIds.add(R.drawable.main_small_corner_bg_new);
                        mItemTextColors.add(R.color.white);
                        mItemIds.add(R.drawable.main_small_corner_bg_new);
                        menuLayout.setMenuItemIconsAndTexts(mItemIds, mItemTextColors, Arrays.asList(mItemTexts));
                        curPrem = datas.get(0);
                        setPro();
                    } else {
                        mItemTextColors.add(R.color.white);
                        mItemTextColors.add(R.color.white);
                        mItemTextColors.add(R.color.white);
                        mItemIds.add(R.drawable.main_small_corner_bg_new);
                        mItemIds.add(R.drawable.main_small_corner_bg_new);
                        mItemIds.add(R.drawable.main_small_corner_bg_new);
                        mItemTextColors.add(R.color.white);
                        mItemIds.add(R.drawable.main_small_corner_bg_new);
                        menuLayout.setMenuItemIconsAndTexts(mItemIds, mItemTextColors, Arrays.asList(mItemTexts));
                    }
                }

                @Override
                public void onError(String result, String code, String msg) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav1:
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(Constant.key1,1);
                intent.putExtra(ZFrameActivity.CLASS_NAME, ProjectWebFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.nav2:
                Utils.startNewProject(getActivity());
                break;
            case R.id.nav3:
                Intent intent1 = new Intent(getActivity(), ZFrameActivity.class);
                intent1.putExtra(ZFrameActivity.CLASS_NAME, AfterSaleProtectionFragment.class.getName());
                Utils.startLogin(getActivity(), intent1);
                break;
        }
    }

    public void setPro() {
        ll_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("10");
        tv_addr.setText(curPrem.getTitle());
    }
}
