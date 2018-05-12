package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/5/12.
 */

public class MaterialListEntry implements Serializable {
    private String name;
    private int materailType;
    private String mum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaterailType() {
        return materailType;
    }

    public void setMaterailType(int materailType) {
        this.materailType = materailType;
    }

    public String getMum() {
        return mum;
    }

    public void setMum(String mum) {
        this.mum = mum;
    }
}
