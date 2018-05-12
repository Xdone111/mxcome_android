package com.fenazola.mxcome.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.fenazola.iframe.baidu.LocationService;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.LocationEntry;
import com.fenazola.mxcome.utils.NetWorkUtils;
import com.fenazola.mxcome.utils.Utils;
import com.zss.library.PermissionCallBack;
import com.zss.library.utils.DPUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Hades on 17/03/20.
 */
public class LocationItemLayout extends LinearLayout {

    private TextView text;
    private ImageView refresh;
    private ImageView map;

    public LocationItemLayout(Context context) {
        super(context);
        initView(context);
    }

    public LocationItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.layout_location_item, this);
        text = (TextView) findViewById(R.id.text);
        refresh = (ImageView) findViewById(R.id.refresh);
        map = (ImageView) findViewById(R.id.map);
        refresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationEntry entry = Utils.getLocationEntry();
                entry.setUserSelectCity("");
                Utils.saveLocationEntry(entry);
                refreshLocation();
            }
        });
        setLocationText(getChoiceCity());
    }

    public void setLocationText(CharSequence sequence){
        try {
            text.setText("当前位置："+sequence);
        } catch (Exception e) {
        }
    }

    public String getChoiceCity(){
        String city = "";
        LocationEntry entry = Utils.getLocationEntry();
        if(!TextUtils.isEmpty(entry.getUserSelectCity())){
            city = entry.getUserSelectCity();
        }else{
            city = entry.getCity();
        }
        return city;
    }

    // **************************** 定位相关代码 **************************** //
    private LocationService locationService;

    private BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(final BDLocation location) {
            post(new Runnable() {
                @Override
                public void run() {
                    NetWorkUtils.dismissProgressDialog(getContext());
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
                        setLocationText(getChoiceCity());
                        EventBus.getDefault().post(entry);
                    }
                }
            });
        }
        public void onConnectHotSpotMessage(String s, int i){
            post(new Runnable() {
                @Override
                public void run() {
                    NetWorkUtils.dismissProgressDialog(getContext());
                }
            });
        }
    };

    public void refreshLocation(){
        post(new Runnable() {
            @Override
            public void run() {
                NetWorkUtils.showProgressDialog(getContext(), "");
            }
        });
        locationService = new LocationService(getContext());
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.registerListener(mListener);
        locationService.start();
    }

    public void onDestroy(){
        if(locationService != null){
            locationService.stop();
            locationService.unregisterListener(mListener);
        }
    }

    // **************************** 定位相关代码 **************************** //

}
