package com.fenazola.mxcome.entry.ransfer;

/**
 * Created by xuhuixiang on 2017/7/28.
 * 百科 列表页实体
 */

public class DecorationKnowleEntry {
    private String date;
    private String title;
    private String look;
    private String coll;
    private String path;

    public DecorationKnowleEntry() {
    }

    public DecorationKnowleEntry(String date, String title, String look, String coll, String path) {
        this.date = date;
        this.title = title;
        this.look = look;
        this.coll = coll;
        this.path = path;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLook() {
        return look;
    }

    public void setLook(String look) {
        this.look = look;
    }

    public String getColl() {
        return coll;
    }

    public void setColl(String coll) {
        this.coll = coll;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
