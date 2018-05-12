package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/5/3.
 */

public class SmartApplianceEntry implements Serializable {

    private String product_id;
    private String brand_id;
    private String item_id;
    private String mx_id;
    private String area_code;
    private String suite_title;
    private String show_pic;
    private String item_name;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getMx_id() {
        return mx_id;
    }

    public void setMx_id(String mx_id) {
        this.mx_id = mx_id;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }

    public String getSuite_title() {
        return suite_title;
    }

    public void setSuite_title(String suite_title) {
        this.suite_title = suite_title;
    }

    public String getShow_pic() {
        return show_pic;
    }

    public void setShow_pic(String show_pic) {
        this.show_pic = show_pic;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
