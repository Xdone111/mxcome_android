package com.fenazola.mxcome.entry.project;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 */

public class EstateEntry implements Serializable {

    private String dongNo;
    private String doorplate;
    private String floor;
    private String remark;
    private String unitNo;

    public String getDongNo() {
        return dongNo;
    }

    public void setDongNo(String dongNo) {
        this.dongNo = dongNo;
    }

    public String getDoorplate() {
        return doorplate;
    }

    public void setDoorplate(String doorplate) {
        this.doorplate = doorplate;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }
}
