package com.fenazola.mxcome.entry.ransfer;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * 媒体分集实体类
 * Created by xhx on 2017/3/18.
 */

public class MediaDiversityEntry implements Parcelable {

    private String content;
    private String time;
    private String index;
    private String path;

    public MediaDiversityEntry() {

    }
    public MediaDiversityEntry(String content, String time, String index, String path) {
        this.content = content;
        this.time = time;
        this.index = index;
        this.path = path;
    }

    protected MediaDiversityEntry(Parcel in) {
        content = in.readString();
        time = in.readString();
        index = in.readString();
        path = in.readString();
    }

    public static final Creator<MediaDiversityEntry> CREATOR = new Creator<MediaDiversityEntry>() {
        @Override
        public MediaDiversityEntry createFromParcel(Parcel in) {
            return new MediaDiversityEntry(in);
        }

        @Override
        public MediaDiversityEntry[] newArray(int size) {
            return new MediaDiversityEntry[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeString(time);
        dest.writeString(index);
        dest.writeString(path);
    }
}
