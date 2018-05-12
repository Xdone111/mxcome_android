package com.fenazola.mxcome.entry;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 */

public class LocationEntry implements Serializable {

    String province = ""; //省
    String city = "";     //市
    String district = "";   //区
    String street = "";   //街道
    String addrStr = ""; //地址
    double longitude; //经度
    double latitude; //纬度
    String userSelectCity = ""; //用户自选

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddrStr() {
        return addrStr;
    }

    public void setAddrStr(String addrStr) {
        this.addrStr = addrStr;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUserSelectCity() {
        return userSelectCity;
    }

    public void setUserSelectCity(String userSelectCity) {
        this.userSelectCity = userSelectCity;
    }
}
