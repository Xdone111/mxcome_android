package com.fenazola.mxcome.entry.project;

/**
 * Created by zm on 2017/5/15.
 */

public class PicsEntry {

    private int tag; //1施工前,2,施工中,3施工后
    private String path;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
