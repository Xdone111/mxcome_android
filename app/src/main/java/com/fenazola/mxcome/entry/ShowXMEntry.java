package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/6/7.
 */

public class ShowXMEntry implements Serializable {

    private String address;
    private String customer;
    private String gc_pic;
    private String house_str;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getGc_pic() {
        return gc_pic;
    }

    public void setGc_pic(String gc_pic) {
        this.gc_pic = gc_pic;
    }

    public String getHouse_str() {
        return house_str;
    }

    public void setHouse_str(String house_str) {
        this.house_str = house_str;
    }
}
