package com.fenazola.mxcome.fragment.me;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fenazola.iframe.baidu.LocationService;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.LocationEntry;
import com.fenazola.mxcome.entry.UserEntry;
import com.fenazola.mxcome.fragment.sercentre.FaqTermsFragment;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.EditTextUtils;
import com.fenazola.mxcome.utils.GsonUtils;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.SmsSendHelper;
import com.fenazola.mxcome.utils.Utils;
import com.google.gson.reflect.TypeToken;
import com.zss.library.PermissionCallBack;
import com.zss.library.activity.ZFrameActivity;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.CommonToastUtils;
import com.zss.library.utils.DPUtils;
import com.zss.library.utils.MD5Utils;
import com.zss.library.widget.CommonEditWidget;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 注册
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {


    private Button confirm;
    private TextView provision;
    private CommonEditWidget eWidget1;
    private CommonEditWidget eWidget2;
    private CommonEditWidget eWidget3;
    private CommonEditWidget eWidget4;
    private SmsSendHelper smsHelper;
    private View view1;
    private View view2;
    private View view3;
    private View view4;

    public void initSms() {
        SMSSDK.initSDK(getActivity(), Constant.SMS_APPKEY, Constant.SMS_APPSECRET);
        SMSSDK.registerEventHandler(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                    }
                } else {
                    ((Throwable) data).getMessage();
                }
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.white);
        provision = (TextView) findViewById(R.id.provision);
        confirm = (Button) findViewById(R.id.confirm);
        eWidget1 = (CommonEditWidget) findViewById(R.id.eWidget1);
        eWidget2 = (CommonEditWidget) findViewById(R.id.eWidget2);
        eWidget3 = (CommonEditWidget) findViewById(R.id.eWidget3);
        eWidget4 = (CommonEditWidget) findViewById(R.id.eWidget4);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        setBgColor(R.color.white);
        int dp = DPUtils.dip2px(getContext(), 10);
        eWidget1.setLeftImageResource(R.mipmap.register_phone);
        eWidget1.setHint(getString(R.string.login_user));
        eWidget1.setCenterPadding(dp, 0, 0, 0);
        EditTextUtils.setPhoneAcceptedChars(eWidget1.getEditText());

        eWidget2.setLeftImageResource(R.mipmap.register_vcode);
        eWidget2.setHint(getString(R.string.login_vcode));
        eWidget2.setCenterPadding(dp, 0, 0, 0);
        TextView rightView = (TextView) getLayoutInflater(R.layout.layout_send_sms);
        eWidget2.setRightView(rightView);
        eWidget2.setInputType(EditTextUtils.getNumberSignedType());
        EditTextUtils.setMaxLength(eWidget2.getEditText(), 6);

        eWidget3.setLeftImageResource(R.mipmap.login_pwd);
        eWidget3.setHint(getString(R.string.login_pwd));
        eWidget3.setCenterPadding(dp, 0, 0, 0);
        EditTextUtils.setPasswordAcceptedChars(eWidget3.getEditText());

        eWidget4.setLeftImageResource(R.mipmap.project_person);
        eWidget4.setHint(getString(R.string.login_name));
        eWidget4.setCenterPadding(dp, 0, 0, 0);
        eWidget4.setInputType(EditTextUtils.getTextType());

        eWidget1.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    view1.setVisibility(View.VISIBLE);
                } else {
                    view1.setVisibility(View.GONE);
                }
            }
        });

        eWidget2.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    view2.setVisibility(View.VISIBLE);
                } else {
                    view2.setVisibility(View.GONE);
                }
            }
        });
        eWidget3.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    view3.setVisibility(View.VISIBLE);
                } else {
                    view3.setVisibility(View.GONE);
                }
            }
        });
        eWidget4.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    view4.setVisibility(View.VISIBLE);
                } else {
                    view4.setVisibility(View.GONE);
                }
            }
        });

        provision.setOnClickListener(this);
        confirm.setOnClickListener(this);

        smsHelper = new SmsSendHelper(rightView, 60000, 1000, new SmsSendHelper.ICallback() {
            @Override
            public boolean onStartTimer() {
                return sendSms(eWidget1.getText());
            }
        });

        initSms();

        locatioinPermissions();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.provision:
//                ServiceTermsFragment fragment = new ServiceTermsFragment();
//                addFragment(fragment);
                Intent intent = new Intent(getActivity(), ZFrameActivity.class);
                intent.putExtra(ZFrameActivity.CLASS_NAME, FaqTermsFragment.class.getName());
                startActivity(intent);
                break;
            case R.id.confirm:
                if (TextUtils.isEmpty(eWidget1.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_phone);
                    return;
                }
                if (eWidget1.getText().startsWith("+") && eWidget1.getText().length() != 14) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_ok_phone);
                    return;
                }
                if (TextUtils.isEmpty(eWidget2.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_vcode);
                    return;
                }
                if (TextUtils.isEmpty(eWidget3.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd);
                    return;
                }
                if (eWidget3.getText().length() < 6) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_pwd_length);
                    return;
                }
                if (eWidget1.getText().startsWith("1") && eWidget1.getText().length() != 11) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_ok_phone);
                    return;
                }
                if (TextUtils.isEmpty(eWidget4.getText())) {
                    CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_nick_name);
                    return;
                }
                doRegister(eWidget1.getText(), eWidget2.getText(), eWidget3.getText(), eWidget4.getText());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        smsHelper.onFinish();
        SMSSDK.unregisterAllEventHandler();
        if (locationService != null) {
            locationService.stop();
            locationService.unregisterListener(mListener);
        }
    }

    public boolean sendSms(String phone) {
        if (TextUtils.isEmpty(phone)) {
            CommonToastUtils.showInCenterToast(getActivity(), R.string.tips_phone);
            return false;
        }
        SMSSDK.getVerificationCode("+86", phone);
        return true;
    }

    public void doRegister(String tel, String verifyCode, String pwd, String nickName) {
        Map<String, String> map = new HashMap<>();
        map.put("tel", tel);
        map.put("verify_code", verifyCode);
        String md5Str = MD5Utils.getMD5String(pwd).toUpperCase();
        map.put("pwd", md5Str);
        map.put("nick_name", nickName);
        map.put("os", "android");
        LocationEntry entry = Utils.getLocationEntry();
        map.put("province", entry.getProvince());
        map.put("city", entry.getCity());
        map.put("county", entry.getDistrict());
        map.put("longitude", "" + entry.getLongitude());
        map.put("latitude", "" + entry.getLatitude());
        NetWorkUtils.post(getActivity(), "appApi/appRegister.do", map, new NetWorkUtils.IListener() {
            @Override
            public void onSuccess(String result, JSONObject resObj) {
                String res = resObj.optString("res");
                List<UserEntry> list = GsonUtils.getListFromJSON(res, new TypeToken<ArrayList<UserEntry>>() {
                }.getType());
                UserEntry entry = list.get(0);
                EventBus.getDefault().post(entry.getTel());
                getActivity().finish();
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
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setBgColor(getColor(R.color.colorBlue));
        toolbar.setTitle(getString(R.string.me_register));
    }

    // **************************** 定位相关代码 **************************** //
    private LocationService locationService;

    private BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                String province = location.getProvince();
                String city = location.getCity();
                String district = location.getDistrict();
                String street = location.getStreet();
                String addrStr = location.getAddrStr();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                LocationEntry entry = Utils.getLocationEntry();
                entry.setProvince(province);
                entry.setCity(city);
                entry.setDistrict(district);
                entry.setStreet(street);
                entry.setAddrStr(addrStr);
                entry.setLongitude(longitude);
                entry.setLatitude(latitude);
                Utils.saveLocationEntry(entry);

            }
        }

        public void onConnectHotSpotMessage(String s, int i) {
        }
    };

    public void locatioinPermissions() {
        verifyPermissions(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION,
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}
                , 0x01, new PermissionCallBack() {
                    @Override
                    public void onGranted() {
                        locationService = new LocationService(getActivity());
                        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
                        locationService.registerListener(mListener);
                        locationService.start();
                    }

                    @Override
                    public void onDenied() {

                    }
                });
    }
    // **************************** 定位相关代码 **************************** //
}
