package com.fenazola.mxcome.entry.sercentre;

/**
 * 使用手册下部列表实体
 * Created by xhx on 2017/6/17.
 */

public class ManualEntry {
    private String title;
    private String subName;

    public ManualEntry() {
    }

    public ManualEntry(String title, String subName) {
        this.title = title;
        this.subName = subName;
    }

    public String getTitle() {
        return title;
    }

    public String getSubName() {
        return subName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
