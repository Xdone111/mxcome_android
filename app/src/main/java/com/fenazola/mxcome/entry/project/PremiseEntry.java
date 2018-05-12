package com.fenazola.mxcome.entry.project;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 * 用户房屋
 */

public class PremiseEntry implements Serializable {

    private String country;
    private String province;
    private String city;
    private String county;
    private String address;
    private DecoraEntry mDecora;
    private int orderNum;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public DecoraEntry getmDecora() {
        return mDecora;
    }

    public void setmDecora(DecoraEntry mDecora) {
        this.mDecora = mDecora;
    }

    public String getHouseInfo(){
        StringBuffer mStrBuf = new StringBuffer();
        String premiseName = mDecora.getEstate().getRemark();
        if(!TextUtils.isEmpty(premiseName)){
            mStrBuf.append(premiseName);
        }
        String carpetArea = mDecora.getHouse().getCarpetArea();
        if(!TextUtils.isEmpty(carpetArea)){
            mStrBuf.append(" "+carpetArea+"㎡");
        }
//        String outdoorArea = mDecora.getHouse().getOutdoorArea();
//        if(!TextUtils.isEmpty(outdoorArea)){
//            mStrBuf.append(" "+outdoorArea+"㎡");
//        }
        return mStrBuf.toString();
    }

    public String getAddrInfo(){
        StringBuffer mStrBuf = new StringBuffer();
        mStrBuf.append(city+" ");
        mStrBuf.append(county+" ");
        mStrBuf.append(address);
        String dongNo = mDecora.getEstate().getDongNo();
        if(!TextUtils.isEmpty(dongNo)){
            mStrBuf.append(dongNo+"栋");
        }
        String unitNo = mDecora.getEstate().getUnitNo();
        if(!TextUtils.isEmpty(unitNo)){
            mStrBuf.append(unitNo);
        }
        String floor = mDecora.getEstate().getFloor();
        if(!TextUtils.isEmpty(floor)){
            mStrBuf.append(floor);
        }
        String doorplate = mDecora.getEstate().getDoorplate();
        if(!TextUtils.isEmpty(doorplate)){
            mStrBuf.append(doorplate);
        }
        return mStrBuf.toString();
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
