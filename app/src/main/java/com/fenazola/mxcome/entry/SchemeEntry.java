package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 */

public class SchemeEntry implements Serializable {

    private int is_show; //是否显示 1: 可编辑， 0 不编辑
    private int clum_id; //技能ID 或者 材料包ID

    public boolean isEnable(){
        return is_show == 1;
    }

    public int getClum_id() {
        return clum_id;
    }

    public void setClum_id(int clum_id) {
        this.clum_id = clum_id;
    }

    public boolean isWorker(){
        return String.valueOf(clum_id).startsWith("80");
    }

    public boolean isMaterial(){
        return String.valueOf(clum_id).startsWith("100");
    }

    @Override
    public String toString() {
        return "SchemeEntry{" +
                "is_show=" + is_show +
                ", clum_id='" + clum_id + '\'' +
                '}';
    }
}
