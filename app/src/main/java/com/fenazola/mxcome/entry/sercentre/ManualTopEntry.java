package com.fenazola.mxcome.entry.sercentre;

/**
 * 使用手册顶部GV列表实体
 * Created by xhx on 2017/6/17.
 */

public class ManualTopEntry {
    private String title;
    private int resId;

    public ManualTopEntry() {
    }

    public ManualTopEntry(String title, int resId) {
        this.title = title;
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
