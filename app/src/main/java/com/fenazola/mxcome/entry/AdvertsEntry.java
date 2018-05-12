package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/4/13.
 */

public class AdvertsEntry implements Serializable {
    private String advert_id;
    private String brand_id;
    private String order_str;
    private String shop_name;
    private String produc_id;
    private String advert_pics;

    public String getAdvert_id() {
        return advert_id;
    }

    public void setAdvert_id(String advert_id) {
        this.advert_id = advert_id;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getOrder_str() {
        return order_str;
    }

    public void setOrder_str(String order_str) {
        this.order_str = order_str;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getProduc_id() {
        return produc_id;
    }

    public void setProduc_id(String produc_id) {
        this.produc_id = produc_id;
    }

    public String getAdvert_pics() {
        return advert_pics;
    }

    public void setAdvert_pics(String advert_pics) {
        this.advert_pics = advert_pics;
    }
}
