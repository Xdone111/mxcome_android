package com.fenazola.mxcome.entry;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/6/7.
 */

public class CommentEntry implements Parcelable {
    private String cp;
    private String hp;
    private String message;
    private String pic_url;
    private String service_attitude;
    private String user_name;
    private String work_quality;
    private String zp;

    public CommentEntry() {
    }

    protected CommentEntry(Parcel in) {
        cp = in.readString();
        hp = in.readString();
        message = in.readString();
        pic_url = in.readString();
        service_attitude = in.readString();
        user_name = in.readString();
        work_quality = in.readString();
        zp = in.readString();
    }

    public static final Creator<CommentEntry> CREATOR = new Creator<CommentEntry>() {
        @Override
        public CommentEntry createFromParcel(Parcel in) {
            return new CommentEntry(in);
        }

        @Override
        public CommentEntry[] newArray(int size) {
            return new CommentEntry[size];
        }
    };

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getService_attitude() {
        return service_attitude;
    }

    public void setService_attitude(String service_attitude) {
        this.service_attitude = service_attitude;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getWork_quality() {
        return work_quality;
    }

    public void setWork_quality(String work_quality) {
        this.work_quality = work_quality;
    }

    public String getZp() {
        return zp;
    }

    public void setZp(String zp) {
        this.zp = zp;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cp);
        dest.writeString(hp);
        dest.writeString(message);
        dest.writeString(pic_url);
        dest.writeString(service_attitude);
        dest.writeString(user_name);
        dest.writeString(work_quality);
        dest.writeString(zp);
    }
}
