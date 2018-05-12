package com.fenazola.mxcome.entry.project;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 */

public class DecoraEntry implements Serializable {

    private String houseId;
    private String mx_pj_tel;
    private String sex;
    private String user_name;
    private EstateEntry estate;
    private HouseEntry house;


    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getMx_pj_tel() {
        return mx_pj_tel;
    }

    public void setMx_pj_tel(String mx_pj_tel) {
        this.mx_pj_tel = mx_pj_tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public EstateEntry getEstate() {
        return estate;
    }

    public void setEstate(EstateEntry estate) {
        this.estate = estate;
    }

    public HouseEntry getHouse() {
        return house;
    }

    public void setHouse(HouseEntry house) {
        this.house = house;
    }
}
