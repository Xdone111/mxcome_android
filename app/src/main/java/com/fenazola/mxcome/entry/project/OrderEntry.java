package com.fenazola.mxcome.entry.project;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zm on 2017/3/4.
 */

public class OrderEntry implements Serializable {

    private String houseId; // 房屋
    private String advance; // 预算
    private String plan_id; // 套餐ID
    private String mxcome_no;// 用户ID
    private String one_level_type; // 装修规模 整体、模块
    private String two_level_type; // 装修房屋类型 商品房、别墅。
    private String three_level_type; // 装修施工类型 全包、半包、轻包
    private String dec_grade; // 装修价位，奢、豪、舒、简
    private String dec_style; // 装修风格
    private String ruz_time; // 用户入住时间 格式2017-03-03
    private String prepay; // 预算比例
    private String mx_order_no; // 订单号
    private String houseInfo; // 房屋信息
    private String addrInfo; // 地址信息
    private HouseEntry house; // 地址对象
    private String city_code; // 选择城市
    private int timeout; // 剩余时间
    private String total_amount = "0.00";  // 工程金额
    private String dis_amount = "0.00"; // 优惠金额
    private String ordertime; // 订单时间
    private String has_demand;  //是否已有设计(1：pics有值)
    private String pics1; //户型图URL
    private String pics2; //设计图URL
    private String pics3; //施工图URL

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getMxcome_no() {
        return mxcome_no;
    }

    public void setMxcome_no(String mxcome_no) {
        this.mxcome_no = mxcome_no;
    }

    public String getOne_level_type() {
        return one_level_type;
    }

    public void setOne_level_type(String one_level_type) {
        this.one_level_type = one_level_type;
    }

    public String getTwo_level_type() {
        return two_level_type;
    }

    public void setTwo_level_type(String two_level_type) {
        this.two_level_type = two_level_type;
    }

    public String getThree_level_type() {
        return three_level_type;
    }

    public void setThree_level_type(String three_level_type) {
        this.three_level_type = three_level_type;
    }

    public String getDec_grade() {
        return dec_grade;
    }

    public void setDec_grade(String dec_grade) {
        this.dec_grade = dec_grade;
    }

    public String getDec_style() {
        return dec_style;
    }

    public void setDec_style(String dec_style) {
        this.dec_style = dec_style;
    }

    public String getRuz_time() {
        return ruz_time;
    }

    public void setRuz_time(String ruz_time) {
        this.ruz_time = ruz_time;
    }

    public String getPrepay() {
        return prepay;
    }

    public void setPrepay(String prepay) {
        this.prepay = prepay;
    }

    public String getMx_order_no() {
        return mx_order_no;
    }

    public void setMx_order_no(String mx_order_no) {
        this.mx_order_no = mx_order_no;
    }

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public String getAddrInfo() {
        return addrInfo;
    }

    public void setAddrInfo(String addrInfo) {
        this.addrInfo = addrInfo;
    }

    public HouseEntry getHouse() {
        return house;
    }

    public void setHouse(HouseEntry house) {
        this.house = house;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getDis_amount() {
        return dis_amount;
    }

    public void setDis_amount(String dis_amount) {
        this.dis_amount = dis_amount;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getHas_demand() {
        return has_demand;
    }

    public void setHas_demand(String has_demand) {
        this.has_demand = has_demand;
    }

    public String getPics1() {
        return pics1;
    }

    public void setPics1(String pics1) {
        this.pics1 = pics1;
    }

    public String getPics2() {
        return pics2;
    }

    public void setPics2(String pics2) {
        this.pics2 = pics2;
    }

    public String getPics3() {
        return pics3;
    }

    public void setPics3(String pics3) {
        this.pics3 = pics3;
    }

    @Override
    public String toString() {
        return "OrderEntry{" +
                "houseId='" + houseId + '\'' +
                ", advance='" + advance + '\'' +
                ", plan_id='" + plan_id + '\'' +
                ", mxcome_no='" + mxcome_no + '\'' +
                ", one_level_type='" + one_level_type + '\'' +
                ", two_level_type='" + two_level_type + '\'' +
                ", three_level_type='" + three_level_type + '\'' +
                ", dec_grade='" + dec_grade + '\'' +
                ", dec_style='" + dec_style + '\'' +
                ", ruz_time='" + ruz_time + '\'' +
                ", prepay='" + prepay + '\'' +
                ", mx_order_no='" + mx_order_no + '\'' +
                ", houseInfo='" + houseInfo + '\'' +
                ", addrInfo='" + addrInfo + '\'' +
                ", house=" + house +
                ", city_code='" + city_code + '\'' +
                ", timeout=" + timeout +
                ", total_amount='" + total_amount + '\'' +
                ", dis_amount='" + dis_amount + '\'' +
                ", ordertime='" + ordertime + '\'' +
                ", has_demand='" + has_demand + '\'' +
                ", pics1='" + pics1 + '\'' +
                ", pics2='" + pics2 + '\'' +
                ", pics3='" + pics3 + '\'' +
                '}';
    }
}
