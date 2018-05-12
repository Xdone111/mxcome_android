package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/4/1.
 */

public class DiaryEntry implements Serializable {
    private int photo;
    private String location;
    private String info;
    private String comment;
    private String look;

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLook() {
        return look;
    }

    public void setLook(String look) {
        this.look = look;
    }
}
