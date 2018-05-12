package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 */

public class UserEntry implements Serializable {

    private String age;
    private String area_id;
    private String asset;
    private String cid;
    private String email;
    private String mxcome_no;
    private String pic;
    private String sex;
    private String status;
    private String tel;
    private String user_id;
    private String user_name;
    private String user_type;
    private String userSign;
    private boolean hasPayPwd;

    public boolean isHasPayPwd() {
        return hasPayPwd;
    }

    public void setHasPayPwd(boolean hasPayPwd) {
        this.hasPayPwd = hasPayPwd;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getUserSign() {
        return userSign;
    }

    public void setUserSign(String userSign) {
        this.userSign = userSign;
    }
}
