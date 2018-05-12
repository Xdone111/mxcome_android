package com.fenazola.mxcome.fragment.project;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.fenazola.iframe.baidu.LocationService;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableArea;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.entry.project.OrderEntry;
import com.fenazola.mxcome.entry.project.PremiseEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.EditTextUtils;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.PCDDialog;
import com.google.gson.reflect.TypeToken;
import com.zss.library.PermissionCallBack;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.SharedPrefUtils;
import com.zss.library.widget.CommonEditWidget;
import com.zss.library.widget.CommonWheelDialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 录入信息
 */
public class ProjectInputInfoFragment extends BaseFragment implements View.OnClickListener {

    private TextView head_btn1, head_btn2, head_btn3, head_btn4;
    private View ct_info1, ct_info2, ct_info3, ct_info4;
    private TextView country, province;
    private CommonEditWidget floor_info1, floor_info2, floor_info3, floor_info4;
    private CommonEditWidget house_info1, house_info2, house_info3, house_info4;

    private CommonEditWidget phoneWidget; //手机
    private CommonEditWidget nameWidget; //姓名

    private TextInputLayout streetTextInput; //街道信息提示
    private AutoCompleteTextView streetAutoText; //街道信息

    private TextInputLayout floorTextInput; //楼盘名称提示
    private AutoCompleteTextView floorAutoText; //楼盘名称

    private CommonEditWidget carpetAreaWidget; //室内面积
    private CommonEditWidget outdoorAreaWidget; //花园面积

    private RadioGroup radioGroup;
    private boolean ct_show1 = true, ct_show2 = true, ct_show3 = true, ct_show4 = true;

    private List<TableArea> countryList;
    private List<TableArea> provList;
    private List<TableArea> cityList;
    private List<TableArea> distList;
    private TableArea currentCountry, currentProv, currentCity, currentDist;
    private PCDDialog dialog;
    private int searchFlag = 0;
    private LocationService locationService;
    private SuggestionSearch suggestionSearch;
    private PremiseEntry premise;

    private String selectValue;
    private Button reset;
    private Button next;

    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(final BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                currentProv = new TableArea();
                currentProv.setArea(location.getProvince());
                currentCity = new TableArea();
                currentCity.setArea(location.getCity());
                currentDist = new TableArea();
                currentDist.setArea(location.getDistrict());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        province.setText(currentProv.getArea() + "-" + currentCity.getArea() + "-" + currentDist.getArea());
                        streetAutoText.setText(location.getStreet());
                    }
                });
            }
        }

        public void onConnectHotSpotMessage(String s, int i) {
        }
    };

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_project_input_info;
    }

    @Override
    public void initView() {
        super.initView();

        reset = (Button) findViewById(R.id.reset);
        next = (Button) findViewById(R.id.next);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        head_btn1 = (TextView) findViewById(R.id.head_btn1);
        head_btn2 = (TextView) findViewById(R.id.head_btn2);
        head_btn3 = (TextView) findViewById(R.id.head_btn3);
        head_btn4 = (TextView) findViewById(R.id.head_btn4);

        ct_info1 = findViewById(R.id.content_info1);
        ct_info2 = findViewById(R.id.content_info2);
        ct_info3 = findViewById(R.id.content_info3);
        ct_info4 = findViewById(R.id.content_info4);

        phoneWidget = (CommonEditWidget) findViewById(R.id.phoneWidget);
        nameWidget = (CommonEditWidget) findViewById(R.id.nameWidget);

        streetTextInput = (TextInputLayout) findViewById(R.id.street_text_input);
        streetAutoText = (AutoCompleteTextView) findViewById(R.id.street_auto_text);

        floorTextInput = (TextInputLayout) findViewById(R.id.floor_text_input);
        floorAutoText = (AutoCompleteTextView) findViewById(R.id.floor_auto_text);

        carpetAreaWidget = (CommonEditWidget) findViewById(R.id.carpetAreaWidget);
        outdoorAreaWidget = (CommonEditWidget) findViewById(R.id.outdoorAreaWidget);

        floor_info1 = (CommonEditWidget) findViewById(R.id.floor_info1);
        floor_info2 = (CommonEditWidget) findViewById(R.id.floor_info2);
        floor_info3 = (CommonEditWidget) findViewById(R.id.floor_info3);
        floor_info4 = (CommonEditWidget) findViewById(R.id.floor_info4);

        country = (TextView) findViewById(R.id.country);
        province = (TextView) findViewById(R.id.province);

        house_info1 = (CommonEditWidget) findViewById(R.id.house_info1);
        house_info2 = (CommonEditWidget) findViewById(R.id.house_info2);
        house_info3 = (CommonEditWidget) findViewById(R.id.house_info3);
        house_info4 = (CommonEditWidget) findViewById(R.id.house_info4);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            premise = (PremiseEntry) args.getSerializable(Constant.key1);
        }

        radioGroup.check(R.id.radio1);
        int padding = DPUtils.dip2px(getActivity(), 10);
        phoneWidget.setInputType(InputType.TYPE_CLASS_PHONE);
        phoneWidget.setLeftImageResource(R.mipmap.project_phone);
        //phoneWidget.setHint(getString(R.string.project_phone));
        UserEntry userEntry = Utils.getUserEntry();
        phoneWidget.setText(userEntry.getTel());
        phoneWidget.setCenterPadding(padding, 0, 0, 0);

        nameWidget.setLeftImageResource(R.mipmap.project_person);
        nameWidget.setHint(getString(R.string.project_name1));
        nameWidget.setCenterPadding(padding, 0, 0, 0);

        streetTextInput.setHint(getString(R.string.project_street));
        streetAutoText.setThreshold(1);

        floorTextInput.setHint(getString(R.string.project_floor_name));
        floorAutoText.setThreshold(1);

        carpetAreaWidget.setHint(getString(R.string.project_room_area));
        carpetAreaWidget.setInputType(EditTextUtils.getNumberType());
        TextView textView = new TextView(getActivity());
        textView.setText("㎡");
        carpetAreaWidget.setRightView(textView);

        outdoorAreaWidget.setHint(getString(R.string.project_garden_area));
        outdoorAreaWidget.setInputType(EditTextUtils.getNumberType());
        textView = new TextView(getActivity());
        textView.setText("㎡");
        outdoorAreaWidget.setRightView(textView);

        floor_info1.setHint(getString(R.string.project_floor_info1));
        floor_info1.setInputType(EditTextUtils.getNumberType());
        floor_info2.setHint(getString(R.string.project_floor_info2));
        floor_info2.setInputType(EditTextUtils.getTextType());
        floor_info3.setHint(getString(R.string.project_floor_info3));
        floor_info3.setInputType(EditTextUtils.getTextType());
        floor_info4.setHint(getString(R.string.project_floor_info4));
        floor_info4.setInputType(EditTextUtils.getTextType());

        house_info1.setHint(getString(R.string.project_house_info1));
        house_info1.setInputType(EditTextUtils.getNumberType());
        house_info1.setRightImageResource(R.mipmap.me_triangle);
        house_info2.setHint(getString(R.string.project_house_info2));
        house_info2.setInputType(EditTextUtils.getNumberType());
        house_info2.setRightImageResource(R.mipmap.me_triangle);
        house_info3.setHint(getString(R.string.project_house_info3));
        house_info3.setInputType(EditTextUtils.getNumberType());
        house_info3.setRightImageResource(R.mipmap.me_triangle);
        house_info4.setHint(getString(R.string.project_house_info4));
        house_info4.setInputType(EditTextUtils.getNumberType());
        house_info4.setRightImageResource(R.mipmap.me_triangle);

        head_btn1.setOnClickListener(this);
        head_btn2.setOnClickListener(this);
        head_btn3.setOnClickListener(this);
        head_btn4.setOnClickListener(this);

        house_info1.setOnClickListener(this);
        house_info2.setOnClickListener(this);
        house_info3.setOnClickListener(this);
        house_info4.setOnClickListener(this);

        country.setOnClickListener(this);
        province.setOnClickListener(this);
        reset.setOnClickListener(this);
        next.setOnClickListener(this);

        countryList = queryData(0, 0);
        currentCountry = countryList.get(0);
        country.setText(currentCountry.getArea());
        provList = queryData(currentCountry.getAreaid(), 1);
        currentProv = provList.get(0);
        cityList = queryData(currentProv.getAreaid(), 2);
        currentCity = cityList.get(0);
        distList = queryData(currentCity.getAreaid(), 3);
        currentDist = distList.get(0);

        locatioinPermissions();

        nameWidget.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    nameWidget.setRightImageResource(R.mipmap.input_edit_red);
                } else {
                    nameWidget.setRightImageResource(0);
                }
            }
        });
        phoneWidget.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    phoneWidget.setRightImageResource(R.mipmap.input_edit_red);
                } else {
                    phoneWidget.setRightImageResource(0);
                }
            }
        });

        suggestionSearch = SuggestionSearch.newInstance();
        suggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
                    return;
                }
                ArrayList datas = new ArrayList<String>();
                for (SuggestionResult.SuggestionInfo info : suggestionResult.getAllSuggestions()) {
                    if (info.key != null) {
                        datas.add(info.key);
                    }
                }
                ArrayAdapter mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, datas);
                if (searchFlag == 0) {
                    streetAutoText.setAdapter(mAdapter);
                } else {
                    floorAutoText.setAdapter(mAdapter);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
        streetAutoText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                if (cs.length() <= 0) {
                    return;
                }
                searchFlag = 0;
                suggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(cs.toString()).city(currentCity.getArea().toString()));
            }
        });
        floorAutoText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                if (cs.length() <= 0) {
                    return;
                }
                searchFlag = 1;
                suggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(cs.toString()).city(currentCity.getArea().toString()));
            }
        });

        if (premise != null) {
            setUpdateDatas();
        }
    }


    public void setUpdateDatas() {
        currentProv = new TableArea();
        currentProv.setArea(premise.getProvince());
        currentCity = new TableArea();
        currentCity.setArea(premise.getCity());
        currentDist = new TableArea();
        currentDist.setArea(premise.getCounty());
        province.setText(currentProv.getArea() + "-" + currentCity.getArea() + "-" + currentDist.getArea());
        if (getString(R.string.project_man).equals(premise.getmDecora().getSex())) {
            radioGroup.check(R.id.radio1);
        } else {
            radioGroup.check(R.id.radio2);
        }
        phoneWidget.setText(premise.getmDecora().getMx_pj_tel()); //手机
        nameWidget.setText(premise.getmDecora().getUser_name()); //姓名
        streetAutoText.setText(premise.getAddress()); //街道信息

        floor_info1.setText(premise.getmDecora().getEstate().getDongNo()); //栋号
        floor_info2.setText(premise.getmDecora().getEstate().getUnitNo()); //单元
        floor_info3.setText(premise.getmDecora().getEstate().getFloor()); //楼层
        floor_info4.setText(premise.getmDecora().getEstate().getDoorplate()); //门牌
        floorAutoText.setText(premise.getmDecora().getEstate().getRemark()); //楼盘名称

        carpetAreaWidget.setText(premise.getmDecora().getHouse().getCarpetArea()); //室内面积
        outdoorAreaWidget.setText(premise.getmDecora().getHouse().getOutdoorArea()); //花园面积

        house_info1.setText(premise.getmDecora().getHouse().getRoom()); //室
        house_info2.setText(premise.getmDecora().getHouse().getHall()); //厅
        house_info3.setText(premise.getmDecora().getHouse().getGalley()); //厨
        house_info4.setText(premise.getmDecora().getHouse().getToilet()); //卫生间

        head_btn1.performClick();
        head_btn2.performClick();
        head_btn3.performClick();
        head_btn4.performClick();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_btn1:
                if (ct_show1) {
                    ct_info1.setVisibility(View.GONE);
                } else {
                    ct_info1.setVisibility(View.VISIBLE);
                }
                ct_show1 = !ct_show1;
                phoneWidget.getEditText().setFocusable(true);
                phoneWidget.getEditText().setFocusableInTouchMode(true);
                phoneWidget.getEditText().requestFocus();
                break;
            case R.id.head_btn2:
                if (ct_show2) {
                    ct_info2.setVisibility(View.GONE);
                } else {
                    ct_info2.setVisibility(View.VISIBLE);
                }
                ct_show2 = !ct_show2;
                streetAutoText.setFocusable(true);
                streetAutoText.setFocusableInTouchMode(true);
                streetAutoText.requestFocus();
                break;
            case R.id.head_btn3:
                if (ct_show3) {
                    ct_info3.setVisibility(View.GONE);
                } else {
                    ct_info3.setVisibility(View.VISIBLE);
                }
                ct_show3 = !ct_show3;
                floor_info1.getEditText().setFocusable(true);
                floor_info1.getEditText().setFocusableInTouchMode(true);
                floor_info1.getEditText().requestFocus();
                break;
            case R.id.head_btn4:
                if (ct_show4) {
                    ct_info4.setVisibility(View.GONE);
                } else {
                    ct_info4.setVisibility(View.VISIBLE);
                }
                ct_show4 = !ct_show4;
                carpetAreaWidget.getEditText().setFocusable(true);
                carpetAreaWidget.getEditText().setFocusableInTouchMode(true);
                carpetAreaWidget.getEditText().requestFocus();
                break;
            case R.id.country:
                showCountryDialog();
                break;
            case R.id.province:
                showPCDDialog();
                break;
            case R.id.floor_info1:
                break;
            case R.id.floor_info2:
                break;
            case R.id.floor_info3:
                break;
            case R.id.floor_info4:
                break;
            case R.id.house_info1:
                showWheelDialog(house_info1, getStringArray(R.array.arrays_house));
                break;
            case R.id.house_info2:
                showWheelDialog(house_info2, getStringArray(R.array.arrays_house));
                break;
            case R.id.house_info3:
                showWheelDialog(house_info3, getStringArray(R.array.arrays_house));
                break;
            case R.id.house_info4:
                showWheelDialog(house_info4, getStringArray(R.array.arrays_house));
                break;
            case R.id.reset:
                CommonToastUtils.showInCenterToast(getActivity(), "即将上线，敬请期待");
                break;
            case R.id.next:
                if (TextUtils.isEmpty(phoneWidget.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_phone);
                    return;
                }
                if (TextUtils.isEmpty(nameWidget.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_name);
                    return;
                }
                if (getString(R.string.project_region).equals(province.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_project_region);
                    return;
                }
                if (TextUtils.isEmpty(carpetAreaWidget.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_room_area);
                    return;
                }
                if (TextUtils.isEmpty(streetAutoText.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_street_name);
                    return;
                }
                if (TextUtils.isEmpty(floorAutoText.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_floor_name);
                    return;
                }

                if (TextUtils.isEmpty(house_info1.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_floor1);
                    return;
                }
                if (TextUtils.isEmpty(house_info2.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_floor2);
                    return;
                }
                if (TextUtils.isEmpty(house_info3.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_floor3);
                    return;
                }
                if (TextUtils.isEmpty(house_info4.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_floor4);
                    return;
                }
                saveUpdatePremise();
                break;
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.black);
        CommonToolbar toolbar = getToolbar();
        toolbar.setBgRes(R.color.trans);
        toolbar.setTitle(getString(R.string.project_input_info));
    }

    public void showWheelDialog(final CommonEditWidget text, String[] arr) {
        CommonWheelDialog dialog = new CommonWheelDialog(getActivity(), arr);
        dialog.setOnSelectedListener(new CommonWheelDialog.OnSelectedListener() {
            @Override
            public void onSelected(int wheelItem, int position, String value) {
                selectValue = value;
            }
        });
        dialog.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(selectValue);
            }
        });
        dialog.show();
    }

    public void showCountryDialog() {
        if (countryList != null && countryList.size() > 0) {
            String[] arr = new String[countryList.size()];
            for (int i = 0; i < countryList.size(); i++) {
                arr[i] = countryList.get(i).getArea();
            }
            CommonWheelDialog dialog = new CommonWheelDialog(getActivity(), arr);
            dialog.setOnSelectedListener(new CommonWheelDialog.OnSelectedListener() {
                @Override
                public void onSelected(int wheelItem, int position, String value) {
                    currentCountry = countryList.get(position);
                }
            });
            dialog.setOnRightListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    country.setText(currentCountry.getArea());
                    provList = queryData(currentCountry.getAreaid(), 1);
                    currentProv = provList.get(0);
                    cityList = queryData(currentProv.getAreaid(), 2);
                    currentCity = cityList.get(0);
                    distList = queryData(currentCity.getAreaid(), 3);
                    currentDist = distList.get(0);
                    province.setText(currentProv.getArea() + "-" + currentCity.getArea() + "-" + currentDist.getArea());
                }
            });
            dialog.show();
        }
    }

    public void showPCDDialog() {
        dialog = new PCDDialog(getActivity(), provList, cityList, distList);
        dialog.setOnSelectedListener(new PCDDialog.OnSelectedListener() {
            @Override
            public void onSelected(int wheelItem, int position) {
                if (wheelItem == 0) {
                    currentProv = provList.get(position);
                    cityList = queryData(currentProv.getAreaid(), 2);
                    if (cityList != null && cityList.size() == 0) {
                        cityList.clear();
                        cityList.add(currentProv);
                        distList.clear();
                        distList.add(currentProv);
                    } else {
                        currentCity = cityList.get(0);
                        distList = queryData(currentCity.getAreaid(), 3);
                    }
                    dialog.replace(cityList, distList);
                } else if (wheelItem == 1) {
                    currentCity = cityList.get(position);
                    distList = queryData(currentCity.getAreaid(), 3);
                    distList.add(currentCity);
                    dialog.replace(null, distList);

                } else if (wheelItem == 2) {
                    currentDist = distList.get(position);
                }
            }
        });
        dialog.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                province.setText(currentProv.getArea() + "-" + currentCity.getArea() + "-" + currentDist.getArea());
            }
        });
        dialog.show();
    }

    public List<TableArea> queryData(Object parentid, Object areatype) {
        BaseDaoImpl dao = new BaseDaoImpl(TableArea.class);
        try {
            String[] columnNames = new String[]{"parentid", "areatype"};
            Object[] columnValues = new Object[]{parentid, areatype};
            return dao.query(columnNames, columnValues);
        } catch (SQLException e) {
            return new ArrayList<TableArea>();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (suggestionSearch != null) {
            suggestionSearch.destroy();
            suggestionSearch = null;
        }
        if (locationService != null) {
            locationService.stop();
            locationService.unregisterListener(mListener);
        }
    }

    public void locatioinPermissions() {
        verifyPermissions(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}
                , 0x01, new PermissionCallBack() {
                    @Override
                    public void onGranted() {
                        if (premise == null) {
                            locationService = new LocationService(getActivity());
                            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                            locationService.registerListener(mListener);
                            locationService.start();
                        }
                    }

                    @Override
                    public void onDenied() {

                    }
                });
    }

    public void saveUpdatePremise() {
        SharedPrefUtils prefUtils = Utils.getSharedPrefCommonFile();
        UserEntry entry = (UserEntry) prefUtils.get("user");
        HashMap<String, String> map = new HashMap<>();
        // map.put("user_id",  entry.getMxcome_no());
        map.put("user_id", entry.getUser_id());
        String actionDo;
        if (premise != null) {
            map.put("pid", premise.getmDecora().getHouseId());
            actionDo = "appApi/uDecoraInfo.do";
        } else {
            actionDo = "appApi/buildDecoraInfo.do";
        }
        map.put("tel", phoneWidget.getText());
        map.put("user_name", nameWidget.getText());
        if (radioGroup.getCheckedRadioButtonId() == R.id.radio1) {
            map.put("sex", "男");
        } else {
            map.put("sex", "女");
        }
        map.put("country", country.getText().toString());
        map.put("province", currentProv.getArea());
        map.put("city", currentCity.getArea());
        map.put("county", currentDist.getArea());
        map.put("address", streetAutoText.getText().toString()); //街道信息

        map.put("carpetArea", carpetAreaWidget.getText().toString());
        map.put("outdoorArea", outdoorAreaWidget.getText().toString());
        map.put("room", house_info1.getText().toString());
        map.put("hall", house_info2.getText().toString());
        map.put("galley", house_info3.getText().toString());
        map.put("toilet", house_info4.getText().toString());
        map.put("garage", "");

        map.put("dongNo", floor_info1.getText().toString());
        map.put("unitNo", floor_info2.getText().toString());
        map.put("floor", floor_info3.getText().toString());
        map.put("doorplate", floor_info4.getText().toString());

        map.put("remark", floorAutoText.getText().toString()); //楼盘名称
        NetWorkUtils.post(getActivity(), actionDo, map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                if (premise != null) {
                    backStackFragment();
                    EventBus.getDefault().post(premise);
                } else {
                    String res = resObj.optString("res");
                    ArrayList<PremiseEntry> datas = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<PremiseEntry>>() {
                    }.getType());
                    if (datas != null && datas.size() > 0) {
                        PremiseEntry entry = datas.get(0);
                        Bundle args = new Bundle();
                        OrderEntry order = new OrderEntry();
                        order.setHouseId(entry.getmDecora().getHouseId());
                        order.setHouse(entry.getmDecora().getHouse());
                        order.setHouseInfo(entry.getHouseInfo());
                        order.setAddrInfo(entry.getAddrInfo());
                        order.setCity_code(currentCity.getArea());
                        args.putSerializable(Constant.key1, order);
                        ProjectDemandChoiceFragment fragment = new ProjectDemandChoiceFragment();
                        fragment.setArguments(args);
                        addFragment(fragment);
                        EventBus.getDefault().post(entry);
                    }
                }
            }

            @Override
            public void onError(String result, String code, String msg) {

            }

        });
    }

}
