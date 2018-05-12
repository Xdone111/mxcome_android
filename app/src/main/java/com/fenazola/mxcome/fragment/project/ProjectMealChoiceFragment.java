package com.fenazola.mxcome.fragment.project;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.MealEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.project.OrderEntry;
import com.fenazola.mxcome.entry.project.StyleEntry;
import com.fenazola.mxcome.fragment.MainFragment;
import com.fenazola.mxcome.fragment.main.demand.Demandwheel;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DataCache;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.ScheduUploadLayout;
import com.fenazola.mxcome.widget.StyleDescLayout;
import com.fenazola.mxcome.widget.TextSeekbar;
import com.google.gson.reflect.TypeToken;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.photopicker.PhotoPickerActivity;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.FileUtils;
import com.zss.library.utils.PicUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 选择设计风格
 *
 * @author zm
 */
public class ProjectMealChoiceFragment extends BaseFragment {

    private RadioGroup radioGroup;
    private TextSeekbar seekBar;
    private TextView dateTime;
    private GridView gridView;
    private CommonAdapter<MealEntry> mAdapter;
    private List<RadioButton> lists = new ArrayList<>();
    private int selectMealPos = -1;
    private OrderEntry order;

    private String plan_id; //套餐Id
    private String dec_style; //选择风格
    private String dec_advance = "200000"; //预算
    private String dec_type = DataCache.ENUM_CD_QUB; //施工类型、默认全包

    private LinearLayout slide1;
    private LinearLayout slide2;
    private LinearLayout slide3;

    private CommonToolbar topBar;
    private RelativeLayout choiceStyleLayout;
    private ScheduUploadLayout designerLayout;
    private TextView mine_design, next_step;

    private LinearLayout centerLayout;
    private StyleDescLayout bottomLayout;

    private JSONArray pics1 = new JSONArray();
    private JSONArray pics2 = new JSONArray();
    private JSONArray pics3 = new JSONArray();

    private final int REQ_CODE1 = 0x01;
    private final int REQ_CODE2 = 0x02;
    private final int REQ_CODE3 = 0x03;

    Projectwheel.MyListener myListener;
    private TextView styleName;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_project_meal_choice;
    }

    @Override
    public void initView() {
        super.initView();
        choiceStyleLayout = (RelativeLayout) findViewById(R.id.choiceStyleLayout);
        designerLayout = (ScheduUploadLayout) findViewById(R.id.designerLayout);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        topBar = (CommonToolbar) findViewById(R.id.topBar);

        lists.add((RadioButton) findViewById(R.id.radioTop));
        lists.add((RadioButton) findViewById(R.id.radioCenter));
        lists.add((RadioButton) findViewById(R.id.radioBottom));
        lists.add((RadioButton) findViewById(R.id.radioTopLeft));
        lists.add((RadioButton) findViewById(R.id.radioBottomLeft));
        lists.add((RadioButton) findViewById(R.id.radioTopRight));
        lists.add((RadioButton) findViewById(R.id.radioBottomRight));

        lists.add((RadioButton) findViewById(R.id.radioTop1));
        lists.add((RadioButton) findViewById(R.id.radioCenter1));
        lists.add((RadioButton) findViewById(R.id.radioBottom1));
        lists.add((RadioButton) findViewById(R.id.radioTopLeft1));
        lists.add((RadioButton) findViewById(R.id.radioBottomLeft1));
        lists.add((RadioButton) findViewById(R.id.radioTopRight1));
        lists.add((RadioButton) findViewById(R.id.radioBottomRight1));

        seekBar = (TextSeekbar) findViewById(R.id.seekBar);
        dateTime = (TextView) findViewById(R.id.dateTime);
        gridView = (GridView) findViewById(R.id.gridView);

        slide1 = (LinearLayout) findViewById(R.id.ll_slide_1);
        slide2 = (LinearLayout) findViewById(R.id.ll_slide_2);
        slide3 = (LinearLayout) findViewById(R.id.ll_slide_3);

        mine_design = (TextView) findViewById(R.id.mine_design);
        next_step = (TextView) findViewById(R.id.next_step);
        centerLayout = (LinearLayout) findViewById(R.id.center_layout);
        bottomLayout = (StyleDescLayout) findViewById(R.id.bottom_layout);
        styleName = (TextView) findViewById(R.id.styleName);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            order = (OrderEntry) args.getSerializable(Constant.key1);
        }

        for (RadioButton item : lists) {
            item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        setRadioStatus(buttonView);
                    }
                }
            });
        }

        List<String> datas = new ArrayList<>();
        datas.add("5w");
        datas.add("10w");
        datas.add("15w");
        datas.add("20w");
        datas.add("50w");
        datas.add("100w+");
        seekBar.setSection(3);
        seekBar.setDatas(datas);
        myListener = new Projectwheel.MyListener() {
            @Override
            public void refreshActivity(String text) {
                String tim = text.substring(0, text.length() - 1);
                tim = tim.replace("年", "-").replace("月", "-");
                dateTime.setText(tim);
            }
        };
        dateTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
//                Utils.showDateDialog(getActivity(), dateTime, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        setStyleChoiceNextStepEnable();
//                    }
//                });
                Projectwheel dialog5 = new Projectwheel(getActivity(), myListener);
                dialog5.show();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio1) {
                    dec_type = DataCache.ENUM_CD_BIB;
                    slide1.setVisibility(View.VISIBLE);
                    slide2.setVisibility(View.INVISIBLE);
                    slide3.setVisibility(View.INVISIBLE);
                } else if (checkedId == R.id.radio2) {
                    dec_type = DataCache.ENUM_CD_QUB;
                    slide1.setVisibility(View.INVISIBLE);
                    slide2.setVisibility(View.VISIBLE);
                    slide3.setVisibility(View.INVISIBLE);
                } else if (checkedId == R.id.radio3) {
                    dec_type = DataCache.ENUM_CD_QIB;
                    slide1.setVisibility(View.INVISIBLE);
                    slide2.setVisibility(View.INVISIBLE);
                    slide3.setVisibility(View.VISIBLE);
                }
            }
        });

        //设置默认全包
        radioGroup.check(R.id.radio2);
        dec_type = DataCache.ENUM_CD_QUB;
        slide1.setVisibility(View.INVISIBLE);
        slide2.setVisibility(View.VISIBLE);
        slide3.setVisibility(View.INVISIBLE);

        designerLayout.setOnClickChoice1Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoPicker(REQ_CODE1);
            }
        });

        designerLayout.setOnClickChoice2Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoPicker(REQ_CODE2);
            }
        });

        designerLayout.setOnClickChoice3Listener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoPicker(REQ_CODE3);
            }
        });

        bottomLayout.setOnClickCloseListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomLayout.setVisibility(View.GONE);
                centerLayout.setVisibility(View.VISIBLE);
            }
        });

        mAdapter = new CommonAdapter<MealEntry>(getActivity(), R.layout.listitem_meal_choice) {
            @Override
            protected void convert(ViewHolder viewHolder, MealEntry item, int position) {
                TextView meal = viewHolder.findViewById(R.id.meal);
                meal.setText(item.getAlias());
                if (selectMealPos == position) {
                    meal.setBackgroundResource(R.mipmap.choice_meal_select);
                    meal.setTextColor(getColor(R.color.colorBlue));
                } else {
                    meal.setBackgroundResource(R.mipmap.choice_meal_noselect);
                    meal.setTextColor(getColor(R.color.white));
                }
            }
        };
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectMealPos = position;
                mAdapter.notifyDataSetChanged();
                plan_id = mAdapter.getItem(selectMealPos).getPlan_id();
                setStyleChoiceNextStepEnable();
            }
        });

        mine_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMineDesignerNextStepEnable();
                choiceStyleLayout.setVisibility(View.GONE);
                designerLayout.setVisibility(View.VISIBLE);
                mine_design.setVisibility(View.GONE);
                next_step.setText(getString(R.string.next));
                next_step.setBackgroundResource(R.mipmap.schedu_next_btn);
                getBaseActivity().setStatusBarColor(R.color.colorBlue);
                topBar.setTitle("设计排班");
                topBar.setLeftImage(R.mipmap.project_left);
                topBar.setBgColor(getColor(R.color.trans));
                topBar.setTitleColor(getColor(R.color.white));
                topBar.setRightImage(R.mipmap.question_w);

            }
        });

//        next_step.setEnabled(false);
        next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mine_design.getVisibility() == View.GONE) { //在设计页面
                    if (pics1.length() <= 0) {
                        CommonToastUtils.showInCenterToast(getActivity(), "请上传平面户型图");
                        return;
                    }
                    if (pics2.length() <= 0) {
                        CommonToastUtils.showInCenterToast(getActivity(), "请上传设计效果图");
                        return;
                    }
                    if (pics3.length() <= 0) {
                        CommonToastUtils.showInCenterToast(getActivity(), "请上传工程施工图");
                        return;
                    }
                    order.setHas_demand("1");
                    order.setPics1(pics1.toString());
                    order.setPics2(pics2.toString());
                    order.setPics3(pics3.toString());
                    setStyleChoiceNextStepEnable();
                    choiceStyleLayout.setVisibility(View.VISIBLE);
                    designerLayout.setVisibility(View.GONE);
                    mine_design.setVisibility(View.VISIBLE);
                    next_step.setText(getString(R.string.next));

                    getBaseActivity().setStatusBarColor(R.color.white);
                    topBar.setTitle("工程排班系统");
                    topBar.setLeftImage(R.mipmap.icon_grey_back);
                    topBar.setBgColor(getColor(R.color.white));
                    topBar.setTitleColor(getColor(R.color.black));
                    topBar.setRightImage(R.mipmap.fund_add_question);
                    mine_design.setVisibility(View.VISIBLE);
                } else {
                    if (TextUtils.isEmpty(dec_style)) {
                        CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_dec_style);
                        return;
                    }
                    if (TextUtils.isEmpty(plan_id)) {
                        CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_plan_id);
                        return;
                    }
                    if (TextUtils.isEmpty(dateTime.getText())) {
                        CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_date_time);
                        return;
                    }
                    order.setHas_demand("0");
                    order.setDec_style(dec_style);
                    switch (seekBar.getSection()) {
                        case 0:
                            dec_advance = "50000";
                            break;
                        case 1:
                            dec_advance = "100000";
                            break;
                        case 2:
                            dec_advance = "150000";
                            break;
                        case 3:
                            dec_advance = "200000";
                            break;
                        case 4:
                            dec_advance = "500000";
                            break;
                        case 5:
                            dec_advance = "1000000";
                            break;
                    }
                    order.setThree_level_type(dec_type);
                    order.setAdvance(dec_advance);
                    order.setPlan_id(plan_id);
                    order.setRuz_time(dateTime.getText().toString());
                    Bundle args = new Bundle();
                    args.putSerializable(Constant.key1, order);
                    ProjectScheduFragment fragment = new ProjectScheduFragment();
                    fragment.setArguments(args);
                    addFragment(fragment);
                }
            }
        });
        initMeal();
    }

    public void setStyleChoiceNextStepEnable() {
//        next_step.setEnabled(false);
        if (TextUtils.isEmpty(dec_style)) {
            return;
        }
        if (TextUtils.isEmpty(plan_id)) {
            return;
        }
        if (TextUtils.isEmpty(dateTime.getText())) {
            return;
        }
//        next_step.setEnabled(true);
        next_step.setBackgroundResource(R.mipmap.schedu_next_btn_red);
    }

    public void setMineDesignerNextStepEnable() {
//        next_step.setEnabled(false);
        if (pics1.length() <= 0) {
            return;
        }
        if (pics2.length() <= 0) {
            return;
        }
        if (pics3.length() <= 0) {
            return;
        }
//        next_step.setEnabled(true);
        next_step.setBackgroundResource(R.mipmap.schedu_next_btn_red);
        mine_design.setText("已传设计");
        mine_design.setBackgroundResource(R.mipmap.project_meal_green_btn);
    }

    public void initMeal() {
        UserEntry user = Utils.getUserEntry();
        HashMap<String, String> map = new HashMap<>();
        map.put("mxcome_no", user.getMxcome_no());
        map.put("city_code", order.getCity_code());
        NetWorkUtils.post(getActivity(), "appApi/queryPlanData.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<MealEntry> list = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<MealEntry>>() {
                }.getType());
                mAdapter.replaceAll(list);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    public void setRadioStatus(CompoundButton buttonView) {
        switch (buttonView.getId()) {
            case R.id.radioTop:
                dec_style = DataCache.ENUM_FG_JY;
                styleName.setText("简约风格");
                showBottomLayout();
                break;
            case R.id.radioCenter:
                dec_style = DataCache.ENUM_FG_ZS;
                styleName.setText("中式风格");
                showBottomLayout();
                break;
            case R.id.radioBottom:
                dec_style = DataCache.ENUM_FG_RS;
                styleName.setText("日式风格");
                showBottomLayout();
                break;
            case R.id.radioTopLeft:
                dec_style = DataCache.ENUM_FG_XD;
                styleName.setText("现代风格");
                showBottomLayout();
                break;
            case R.id.radioBottomLeft:
                dec_style = DataCache.ENUM_FG_XGD;
                styleName.setText("新古典风格");
                showBottomLayout();
                break;
            case R.id.radioTopRight:
                dec_style = DataCache.ENUM_FG_OS;
                styleName.setText("欧式风格");
                showBottomLayout();
                break;
            case R.id.radioBottomRight:
                dec_style = DataCache.ENUM_FG_YJ;
                styleName.setText("宜家风格");
                showBottomLayout();
                break;
            case R.id.radioTop1:
                dec_style = DataCache.ENUM_FG_HD;
                styleName.setText("混搭风格");
                showBottomLayout();
                break;
            case R.id.radioCenter1:
                dec_style = DataCache.ENUM_FG_BO;
                styleName.setText("北欧风格");
                showBottomLayout();
                break;
            case R.id.radioBottom1:
                dec_style = DataCache.ENUM_FG_DNY;
                styleName.setText("东南亚风格");
                showBottomLayout();
                break;
            case R.id.radioTopLeft1:
                dec_style = DataCache.ENUM_FG_TY;
                styleName.setText("田园风格");
                showBottomLayout();
                break;
            case R.id.radioBottomLeft1:
                dec_style = DataCache.ENUM_FG_JO;
                styleName.setText("简欧风格");
                showBottomLayout();
                break;
            case R.id.radioTopRight1:
                dec_style = DataCache.ENUM_FG_MS;
                styleName.setText("美式风格");
                showBottomLayout();
                break;
            case R.id.radioBottomRight1:
                dec_style = DataCache.ENUM_FG_ZZH;
                styleName.setText("地中海风格");
                showBottomLayout();
                break;
        }
        for (RadioButton item : lists) {
            if (item != buttonView) {
                item.setChecked(false);
            }
        }
    }

    public void showBottomLayout() {
        int number = Utils.letterToNumber(dec_style);
        HashMap<String, String> map = new HashMap<>();
        map.put("style", number + "");
        NetWorkUtils.post(getActivity(), "appApi/doFetchPics.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                centerLayout.setVisibility(View.GONE);
                bottomLayout.setVisibility(View.VISIBLE);
                setStyleChoiceNextStepEnable();
                String res = resObj.optString("res");
                List<StyleEntry> mList = GsonUtils.getListFromJSON(res, StyleEntry.class);
                bottomLayout.setTitleAndImage(mList);
            }

            @Override
            public void onError(String result, String code, String msg) {

            }
        });
    }

    @Override
    public boolean onBackPressed() {
        if (mine_design.getVisibility() == View.GONE) { //在设计页面
            setStyleChoiceNextStepEnable();
            choiceStyleLayout.setVisibility(View.VISIBLE);
            designerLayout.setVisibility(View.GONE);
            getBaseActivity().setStatusBarColor(R.color.white);

            topBar.setTitle("工程排班系统");
            topBar.setLeftImage(R.mipmap.icon_grey_back);
            topBar.setBgColor(getColor(R.color.white));
            topBar.setTitleColor(getColor(R.color.black));
            topBar.setRightImage(R.mipmap.fund_add_question);
            mine_design.setVisibility(View.VISIBLE);
            next_step.setText(getString(R.string.next));
            return true;
        }
        return super.onBackPressed();
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE);
        //getBaseActivity().setStatusBarColor(R.color.action_bar_bg);
        getBaseActivity().setStatusBarColor(R.color.white);

        topBar.setTitle("工程排班系统");
        topBar.setLeftImage(R.mipmap.icon_grey_back);
        topBar.setBgColor(getColor(R.color.white));
        topBar.setTitleColor(getColor(R.color.black));
        topBar.setRightImage(R.mipmap.fund_add_question);
        topBar.setOnLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mine_design.getVisibility() == View.GONE) { //在设计页面
                    setStyleChoiceNextStepEnable();
                    choiceStyleLayout.setVisibility(View.VISIBLE);
                    designerLayout.setVisibility(View.GONE);
                    getBaseActivity().setStatusBarColor(R.color.white);

                    topBar.setTitle("工程排班系统");
                    topBar.setLeftImage(R.mipmap.icon_grey_back);
                    topBar.setBgColor(getColor(R.color.white));
                    topBar.setTitleColor(getColor(R.color.black));
                    topBar.setRightImage(R.mipmap.fund_add_question);
                    mine_design.setVisibility(View.VISIBLE);
                    next_step.setText(getString(R.string.next));
                } else {
                    backStackFragment();
                }
            }
        });
    }


    public void openPhotoPicker(final int requestCode) {
        verifyPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                , 0x01, new PermissionCallBack() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
                        startActivityForResult(intent, requestCode);
                    }

                    @Override
                    public void onDenied() {

                    }
                });
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (data.getExtras() != null && data.getExtras().containsKey(PhotoPickerActivity.KEY_RESULT)) {
                List<String> results = (List<String>) data.getExtras().get(PhotoPickerActivity.KEY_RESULT);
                if (results != null && results.size() > 0) {
                    String fileUrl = results.get(0);
                    File file = new File(FileUtils.getExtDir(getActivity()) + Constant.imageDir);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    String targetPath = file.getPath() + File.separator + "temp.jpg";
                    fileUrl = PicUtils.compressImage(fileUrl, targetPath, 50);
                    Utils.uploadImage(getActivity(), fileUrl, new Utils.UrlCallback() {
                        @Override
                        public void onSuccess(String url) {
                            if (requestCode == REQ_CODE1) {
                                pics1.put(url);
                                designerLayout.setUploadSuccess1();
                            } else if (requestCode == REQ_CODE2) {
                                pics2.put(url);
                                designerLayout.setUploadSuccess2();
                            } else if (requestCode == REQ_CODE3) {
                                pics3.put(url);
                                designerLayout.setUploadSuccess3();
                            }
                            setMineDesignerNextStepEnable();
                        }

                        @Override
                        public void onError() {

                        }
                    });
                }
            }
        }
    }

}
