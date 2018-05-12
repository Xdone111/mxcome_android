package com.fenazola.mxcome.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zm on 2017/2/16.
 */

public class WorkerEntry implements Serializable {

    private String address; //地址
    private String time;//注册时间
    private String circle_name; //名称
    private String getorder_l;//接单量
    private String distance;//距离
    private String longitude; //经度
    private String latitude; //纬度
    private String missto_l;//浏览量
    private String wc_id; //工人圈ID
    private String score; //综合评分
    private String cid; //推送ID
    private String status; //工人状态 1可以接单、2停止接单、3帐号冻结(黑名单)
    private String tel; //手机号码
    private String email; //邮箱
    private String user_name; //用户名称
    private String area_id;  //长沙市
    private String sex; //性别
    private String mxcome_no; //MXCOME号
    private String pic;//工人的头像
    private String age; //年龄
    private String rz_desc; //认证描述
    private String card_id;//身份证
    private String pv_id;  //施工者ID
    private String user_type; //施工者类型
    private String wx_openid; //微信openid
    private String gz_year;//工作经验
    private String is_rz;//认证状态
    private String nick_name; //昵称
    private String mShield;//装修盾

    private List<String> label;//被评论的标签
    private List<String> skill_style;//技能列表

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCircle_name() {
        return circle_name;
    }

    public void setCircle_name(String circle_name) {
        this.circle_name = circle_name;
    }

    public String getGetorder_l() {
        return getorder_l;
    }

    public void setGetorder_l(String getorder_l) {
        this.getorder_l = getorder_l;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMissto_l() {
        return missto_l;
    }

    public void setMissto_l(String missto_l) {
        this.missto_l = missto_l;
    }

    public String getWc_id() {
        return wc_id;
    }

    public void setWc_id(String wc_id) {
        this.wc_id = wc_id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMxcome_no() {
        return mxcome_no;
    }

    public void setMxcome_no(String mxcome_no) {
        this.mxcome_no = mxcome_no;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRz_desc() {
        return rz_desc;
    }

    public void setRz_desc(String rz_desc) {
        this.rz_desc = rz_desc;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getPv_id() {
        return pv_id;
    }

    public void setPv_id(String pv_id) {
        this.pv_id = pv_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getWx_openid() {
        return wx_openid;
    }

    public void setWx_openid(String wx_openid) {
        this.wx_openid = wx_openid;
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

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getmShield() {
        return mShield;
    }

    public void setmShield(String mShield) {
        this.mShield = mShield;
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
