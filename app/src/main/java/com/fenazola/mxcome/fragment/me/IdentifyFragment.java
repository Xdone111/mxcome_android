package com.fenazola.mxcome.fragment.me;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.bumptech.glide.Glide;
import com.fenazola.iframe.baidu.LocationService;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableArea;
import com.fenazola.mxcome.entry.project.PremiseEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.PCDDialog;
import com.fenazola.mxcome.widget.clipView.CircleImageView;
import com.zss.library.PermissionCallBack;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.photopicker.PhotoPickerActivity;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.FileUtils;
import com.zss.library.utils.PicUtils;
import com.zss.library.widget.CommonEditWidget;
import com.zss.library.widget.CommonWheelDialog;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.fenazola.mxcome.R.id.photo;

/**
 * Created by XDONE on 2017/6/1.
 * 认证信息
 */

public class IdentifyFragment extends BaseFragment implements View.OnClickListener {

    private TextView head_btn1, head_btn2, head_btn5, head_btn6;
    private View ct_info1, ct_info2, ct_info3, ct_info4, ct_info5, ct_info6;
    private TextView country, province;

    private CommonEditWidget phoneWidget; //手机
    private CommonEditWidget nameWidget; //姓名
    private CommonEditWidget realNameWidget;//真实姓名
    private CommonEditWidget idCardWidget;//身份证号码

    private TextInputLayout streetTextInput; //街道信息提示
    private AutoCompleteTextView streetAutoText; //街道信息


    private RadioGroup radioGroup;
    private boolean ct_show1 = true, ct_show2 = true, ct_show3 = true, ct_show4 = true, ct_show5 = true, ct_show6 = true;

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
    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private TextView baseSubmit;
    private CircleImageView photo;
    private TextView name;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_identify;
    }

    @Override
    public void initView() {
        super.initView();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        head_btn1 = (TextView) findViewById(R.id.head_btn1);
        head_btn2 = (TextView) findViewById(R.id.head_btn2);
        head_btn5 = (TextView) findViewById(R.id.head_btn5);
        head_btn6 = (TextView) findViewById(R.id.head_btn6);

        ct_info1 = findViewById(R.id.content_info1);
        ct_info2 = findViewById(R.id.content_info2);
        ct_info3 = findViewById(R.id.content_info3);
        ct_info4 = findViewById(R.id.content_info4);
        ct_info5 = findViewById(R.id.content_info5);
        ct_info6 = findViewById(R.id.content_info6);

        baseSubmit = (TextView) findViewById(R.id.base_submit);
        baseSubmit.setVisibility(View.VISIBLE);
        View line2 = findViewById(R.id.line2);
        line2.setVisibility(View.GONE);

        phoneWidget = (CommonEditWidget) findViewById(R.id.phoneWidget);
        phoneWidget.setVisibility(View.GONE);
        nameWidget = (CommonEditWidget) findViewById(R.id.nameWidget);
        realNameWidget = (CommonEditWidget) findViewById(R.id.realNameWidget);
        idCardWidget = (CommonEditWidget) findViewById(R.id.idCardWidget);

        streetTextInput = (TextInputLayout) findViewById(R.id.street_text_input);
        streetAutoText = (AutoCompleteTextView) findViewById(R.id.street_auto_text);

        country = (TextView) findViewById(R.id.country);
        province = (TextView) findViewById(R.id.province);

        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.relativeLayout3);

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        photo = (CircleImageView) findViewById(R.id.photo);
        name = (TextView) findViewById(R.id.name);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            premise = (PremiseEntry) args.getSerializable(Constant.key1);
        }
        Glide.with(getActivity()).load(Constant.baseUrl + Utils.getUserEntry().getPic()).error(R.mipmap.me_photo).into(photo);
        name.setText(Utils.getUserEntry().getUser_name());
        radioGroup.check(R.id.radio1);
        int dp = DPUtils.dip2px(getContext(), 10);
        nameWidget.setLeftImageResource(R.mipmap.project_person);
        nameWidget.setHint(getString(R.string.project_name));
        nameWidget.setCenterPadding(dp, 0, 0, 0);

        realNameWidget.setLeftImageResource(R.mipmap.project_name);
        realNameWidget.setHint(getString(R.string.project_real_name));
        realNameWidget.setCenterPadding(dp, 0, 0, 0);

        idCardWidget.setLeftImageResource(R.mipmap.real_name_icon);
        idCardWidget.setInputType(InputType.TYPE_CLASS_NUMBER);
        idCardWidget.setHint(getString(R.string.project_idcard));
        idCardWidget.setCenterPadding(dp, 0, 0, 0);

        streetTextInput.setHint(getString(R.string.project_street));
        streetAutoText.setThreshold(1);

        head_btn1.setOnClickListener(this);
        head_btn2.setOnClickListener(this);
        head_btn5.setOnClickListener(this);
        head_btn6.setOnClickListener(this);

        country.setOnClickListener(this);
        province.setOnClickListener(this);

        relativeLayout1.setOnClickListener(this);
        relativeLayout2.setOnClickListener(this);
        relativeLayout3.setOnClickListener(this);


        countryList = queryData(0, 0);
        currentCountry = countryList.get(0);
        country.setText(currentCountry.getArea());
        provList = queryData(currentCountry.getAreaid(), 1);
        currentProv = provList.get(0);
        cityList = queryData(currentProv.getAreaid(), 2);
        currentCity = cityList.get(0);
        distList = queryData(currentCity.getAreaid(), 3);
        currentDist = distList.get(0);

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
        realNameWidget.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    realNameWidget.setRightImageResource(R.mipmap.input_edit_red);
                } else {
                    realNameWidget.setRightImageResource(0);

                }
            }
        });

        idCardWidget.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    idCardWidget.setRightImageResource(R.mipmap.input_edit_red);
                } else {
                    idCardWidget.setRightImageResource(0);

                }
            }
        });
        streetTextInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                } else {
                }
            }
        });

        locatioinPermissions();

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
        nameWidget.setText(premise.getmDecora().getUser_name()); //姓名
        streetAutoText.setText(premise.getAddress()); //街道信息


        head_btn1.performClick();
        head_btn2.performClick();


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
            case R.id.head_btn5:
                if (ct_show5) {
                    ct_info5.setVisibility(View.GONE);
                } else {
                    ct_info5.setVisibility(View.VISIBLE);
                }
                ct_show5 = !ct_show5;
                break;
            case R.id.head_btn6:
                if (ct_show6) {
                    ct_info6.setVisibility(View.GONE);
                } else {
                    ct_info6.setVisibility(View.VISIBLE);
                }
                ct_show6 = !ct_show6;
                break;
            case R.id.country:
                showCountryDialog();
                break;
            case R.id.province:
                showPCDDialog();
                break;
            case R.id.next:
                if (TextUtils.isEmpty(nameWidget.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_name);
                    return;
                }
                if (getString(R.string.project_region).equals(province.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_project_region);
                    return;
                }

                if (TextUtils.isEmpty(streetAutoText.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_street_name);
                    return;
                }
                break;
            case R.id.relativeLayout1:
                openPhotoPicker(1);
                break;
            case R.id.relativeLayout2:
                openPhotoPicker(2);
                break;
            case R.id.relativeLayout3:
                openPhotoPicker(3);
                break;

        }
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

    public void openPhotoPicker(final int i) {
        verifyPermissions(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}
                , 0x01, new PermissionCallBack() {
                    @Override
                    public void onGranted() {
                        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
                        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
                        startActivityForResult(intent, i);
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
                    for (String fileUrl : results) {
                        File file = new File(FileUtils.getExtDir(getActivity()) + Constant.imageDir);
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        String targetPath = file.getPath() + File.separator + "image_" + System.currentTimeMillis() + ".jpg";
                        fileUrl = PicUtils.compressImage(fileUrl, targetPath, 50);

                        if (requestCode == 1) {
                            Glide.with(getActivity()).load(targetPath).into(image1);
                        } else if (requestCode == 2) {
                            Glide.with(getActivity()).load(targetPath).into(image2);
                        } else if (requestCode == 3) {
                            Glide.with(getActivity()).load(targetPath).into(image3);
                        }

                    }
                }
            }
        }
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.colorBlue);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("填写认证资料");
        toolbar.setBgColor(getColor(R.color.colorBlue));
        //toolbar.setRightImage(R.mipmap.main_location);
    }
}
