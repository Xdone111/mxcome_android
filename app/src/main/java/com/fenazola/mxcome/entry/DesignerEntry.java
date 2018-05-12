package com.fenazola.mxcome.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zm on 2017/2/16.
 */

public class DesignerEntry implements Serializable {

    private String cid; //推送ID
    private String pv_id; //用户ID
    private String mxcome_no; //MXCOME号
    private String user_name; //用户名
    private String nick_name; //昵称
    private String status; //状态
    private String user_type; //用户类型
    private String jdl; //接单量
    private String pf; //评分
    private String pic; //图标
    private String sex; //性别
    private String age; //年龄
    private String repre_ative; //代表作品
    private String unit; //工作单位
    private String area_id; //湖南省-长沙市-开福区
    private String distance; ///距离
    private String missto_l; //浏览量
    private String score; //评分等级
    private List<String> label; //客户评价标签
    private List<String> skill_style; //技能风格
    private String gz_year;
    private int mShield;
    private String is_rz;//认证
    private String getorder_l;

    public String getGetorder_l() {
        return getorder_l;
    }

    public void setGetorder_l(String getorder_l) {
        this.getorder_l = getorder_l;
    }

    public int getmShield() {
        return mShield;
    }

    public void setmShield(int mShield) {
        this.mShield = mShield;
    }

    public String getGz_year() {
        return gz_year;
    }

    public void setGz_year(String gz_year) {
        this.gz_year = gz_year;
    }

    public String getIs_rz() {
        return is_rz;
    }

    public void setIs_rz(String is_rz) {
        this.is_rz = is_rz;
    }


    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPv_id() {
        return pv_id;
    }

    public void setPv_id(String pv_id) {
        this.pv_id = pv_id;
    }

    public String getMxcome_no() {
        return mxcome_no;
    }

    public void setMxcome_no(String mxcome_no) {
        this.mxcome_no = mxcome_no;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getJdl() {
        return jdl;
    }

    public void setJdl(String jdl) {
        this.jdl = jdl;
    }

    public String getPf() {
        return pf;
    }

    public void setPf(String pf) {
        this.pf = pf;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRepre_ative() {
        return repre_ative;
    }

    public void setRepre_ative(String repre_ative) {
        this.repre_ative = repre_ative;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getMissto_l() {
        return missto_l;
    }

    public void setMissto_l(String missto_l) {
        this.missto_l = missto_l;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<String> getLabel() {
        return label;
    }

    public void setLabel(List<String> label) {
        this.label = label;
    }

    public List<String> getSkill_style() {
        return skill_style;
    }

    public void setSkill_style(List<String> skill_style) {
        this.skill_style = skill_style;
    }
}
