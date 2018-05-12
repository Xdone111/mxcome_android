package com.fenazola.mxcome.entry;

import java.util.List;

/**
 * Created by XDONE on 2017/6/9.
 */

public class AccreditedInfoEntry {

    private String user_id;
    private String user_name;
    private String sex;
    private String user_type;
    private String card_id;
    private String gz_year;
    private String skill;
    private String bak_tel;
    private List<String> style;

    public List<String> getStyle() {
        return style;
    }

    public void setStyle(List<String> style) {
        this.style = style;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getGz_year() {
        return gz_year;
    }

    public void setGz_year(String gz_year) {
        this.gz_year = gz_year;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getBak_tel() {
        return bak_tel;
    }

    public void setBak_tel(String bak_tel) {
        this.bak_tel = bak_tel;
    }

}
