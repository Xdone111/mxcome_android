package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * 优惠券实体类
 * Created by xhx on 2017/6/19.
 */

public class CouponEntry{

    private String min_consum;
    private String start_time;
    private String coupon_fee;
    private String elc_id;
    private String end_time;
    private String describe;
    private String coupon_receive_time;
    private String coupon_type;
    private String coupon_name;
    private String pre_release_num;
    private String daily_limit;
    private String id;

    public CouponEntry() {
    }

    public CouponEntry(String min_consum, String start_time, String coupon_fee, String elc_id, String end_time, String describe, String coupon_receive_time, String coupon_type, String coupon_name, String pre_release_num, String daily_limit, String id) {
        this.min_consum = min_consum;
        this.start_time = start_time;
        this.coupon_fee = coupon_fee;
        this.elc_id = elc_id;
        this.end_time = end_time;
        this.describe = describe;
        this.coupon_receive_time = coupon_receive_time;
        this.coupon_type = coupon_type;
        this.coupon_name = coupon_name;
        this.pre_release_num = pre_release_num;
        this.daily_limit = daily_limit;
        this.id = id;
    }

    public String getMin_consum() {
        return min_consum;
    }

    public void setMin_consum(String min_consum) {
        this.min_consum = min_consum;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getCoupon_fee() {
        return coupon_fee;
    }

    public void setCoupon_fee(String coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public String getElc_id() {
        return elc_id;
    }

    public void setElc_id(String elc_id) {
        this.elc_id = elc_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCoupon_receive_time() {
        return coupon_receive_time;
    }

    public void setCoupon_receive_time(String coupon_receive_time) {
        this.coupon_receive_time = coupon_receive_time;
    }

    public String getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getPre_release_num() {
        return pre_release_num;
    }

    public void setPre_release_num(String pre_release_num) {
        this.pre_release_num = pre_release_num;
    }

    public String getDaily_limit() {
        return daily_limit;
    }

    public void setDaily_limit(String daily_limit) {
        this.daily_limit = daily_limit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CouponEntry{" +
                "min_consum='" + min_consum + '\'' +
                ", start_time='" + start_time + '\'' +
                ", coupon_fee='" + coupon_fee + '\'' +
                ", elc_id='" + elc_id + '\'' +
                ", end_time='" + end_time + '\'' +
                ", describe='" + describe + '\'' +
                ", coupon_receive_time='" + coupon_receive_time + '\'' +
                ", coupon_type='" + coupon_type + '\'' +
                ", coupon_name='" + coupon_name + '\'' +
                ", pre_release_num='" + pre_release_num + '\'' +
                ", daily_limit='" + daily_limit + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
