package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/7/28.
 */

public class SiteEntry  implements Serializable{
    private int houseImg;
    private String watchNum;
    private String houseName;

    public int getHouseImg() {
        return houseImg;
    }

    public void setHouseImg(int houseImg) {
        this.houseImg = houseImg;
    }

    public String getWatchNum() {
        return watchNum;
    }

    public void setWatchNum(String watchNum) {
        this.watchNum = watchNum;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }
}
