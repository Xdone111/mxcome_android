package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by zm on 2017/6/21.
 */

public class MsgDataEntry implements Serializable {

    private String u_mxcomeno;
    private String order_no;
    private String w_mxcomeno;
    private String worker_name;
    private String skill;
    private String worker_cid;
    private String user_cid;
    private String log_time;
    private String statu;
    private String u_longitude;
    private String u_latitude;
    private String w_longitude;
    private String w_latitude;
    private String distance;
    private String score;

    public String getU_mxcomeno() {
        return u_mxcomeno;
    }

    public void setU_mxcomeno(String u_mxcomeno) {
        this.u_mxcomeno = u_mxcomeno;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getW_mxcomeno() {
        return w_mxcomeno;
    }

    public void setW_mxcomeno(String w_mxcomeno) {
        this.w_mxcomeno = w_mxcomeno;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getWorker_cid() {
        return worker_cid;
    }

    public void setWorker_cid(String worker_cid) {
        this.worker_cid = worker_cid;
    }

    public String getUser_cid() {
        return user_cid;
    }

    public void setUser_cid(String user_cid) {
        this.user_cid = user_cid;
    }

    public String getLog_time() {
        return log_time;
    }

    public void setLog_time(String log_time) {
        this.log_time = log_time;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getU_longitude() {
        return u_longitude;
    }

    public void setU_longitude(String u_longitude) {
        this.u_longitude = u_longitude;
    }

    public String getU_latitude() {
        return u_latitude;
    }

    public void setU_latitude(String u_latitude) {
        this.u_latitude = u_latitude;
    }

    public String getW_longitude() {
        return w_longitude;
    }

    public void setW_longitude(String w_longitude) {
        this.w_longitude = w_longitude;
    }

    public String getW_latitude() {
        return w_latitude;
    }

    public void setW_latitude(String w_latitude) {
        this.w_latitude = w_latitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "MsgDataEntry{" +
                "u_mxcomeno='" + u_mxcomeno + '\'' +
                ", order_no='" + order_no + '\'' +
                ", w_mxcomeno='" + w_mxcomeno + '\'' +
                ", worker_name='" + worker_name + '\'' +
                ", skill='" + skill + '\'' +
                ", worker_cid='" + worker_cid + '\'' +
                ", user_cid='" + user_cid + '\'' +
                ", log_time='" + log_time + '\'' +
                ", statu='" + statu + '\'' +
                ", u_longitude='" + u_longitude + '\'' +
                ", u_latitude='" + u_latitude + '\'' +
                ", w_longitude='" + w_longitude + '\'' +
                ", w_latitude='" + w_latitude + '\'' +
                ", distance='" + distance + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
