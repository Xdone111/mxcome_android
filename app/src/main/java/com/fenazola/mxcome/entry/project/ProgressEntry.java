package com.fenazola.mxcome.entry.project;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XDONE on 2017/4/6.
 */

public class ProgressEntry implements Serializable {

    private String zgq; //工期总天数;

    private Mdec1Entry mdec1;

    private List<Mdec2Entry> mdec2;

    public String getZgq() {
        return zgq;
    }

    public void setZgq(String zgq) {
        this.zgq = zgq;
    }

    public Mdec1Entry getMdec1() {
        return mdec1;
    }

    public void setMdec1(Mdec1Entry mdec1) {
        this.mdec1 = mdec1;
    }

    public List<Mdec2Entry> getMdec2() {
        return mdec2;
    }

    public void setMdec2(List<Mdec2Entry> mdec2) {
        this.mdec2 = mdec2;
    }
}
