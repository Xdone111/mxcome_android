package com.fenazola.mxcome.fragment.main.location;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fenazola.iframe.baidu.LocationService;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.adapter.CityListAdapter;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableArea;
import com.fenazola.mxcome.db.TableChat;
import com.fenazola.mxcome.entry.LocateState;
import com.fenazola.mxcome.entry.LocationEntry;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.SideLetterBar;
import com.zss.library.PermissionCallBack;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by XDONE on 2017/4/13.
 */

public class LocationFragment extends BaseFragment {

    private TextView tv_letter_overlay;
    private ListView listview_all_city;
    private SideLetterBar side_letter_bar;
    private CityListAdapter mCityAdapter;
    private List<TableArea> mAllCities = new ArrayList<>();
    private String[] mTitles = new String[]{"北京市", "长沙市", "重庆市", "大连市", "广州市", "贵阳市", "杭州市", "深圳市", "武汉市", "西安市", "厦门市"};


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_location;
    }

    @Override
    public void initView() {
        super.initView();
        tv_letter_overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        listview_all_city = (ListView) findViewById(R.id.listview_all_city);
        side_letter_bar = (SideLetterBar) findViewById(R.id.side_letter_bar);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        side_letter_bar.setOverlay(tv_letter_overlay);
        side_letter_bar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                listview_all_city.setSelection(position);
            }
        });
        BaseDaoImpl dao = new BaseDaoImpl(TableArea.class);
        try {
            mAllCities = dao.getDao().queryBuilder()
                    .orderBy("keyword", true).where().eq("areatype", 2).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mCityAdapter = new CityListAdapter(getActivity(), Arrays.asList(mTitles), mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                LocationEntry entry = Utils.getLocationEntry();
                entry.setUserSelectCity(name);
                Utils.saveLocationEntry(entry);
                mCityAdapter.updateLocateState(LocateState.SUCCESS, name);
                getActivity().finish();
            }

            @Override
            public void onLocateClick() {
                mCityAdapter.updateLocateState(LocateState.LOCATING, "定位中");
                locatioinPermissions();
            }
        });
        listview_all_city.setAdapter(mCityAdapter);

        LocationEntry entry = Utils.getLocationEntry();
        if (!TextUtils.isEmpty(entry.getCity())){
            mCityAdapter.updateLocateState(LocateState.SUCCESS, entry.getCity());
        } else{
            mCityAdapter.updateLocateState(LocateState.LOCATING, "定位中");
            locatioinPermissions();
        }

    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.action_bar_bg);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("城市列表");
        toolbar.setBgColor(getColor(R.color.colorBlue));
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
                mCityAdapter.updateLocateState(LocateState.SUCCESS, entry.getCity());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (locationService != null) {
            locationService.stop();
            locationService.unregisterListener(mListener);
        }
    }

    // **************************** 定位相关代码 **************************** //

}
