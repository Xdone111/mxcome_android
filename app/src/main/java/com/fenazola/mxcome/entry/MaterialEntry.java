package com.fenazola.mxcome.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XDONE on 2017/4/13.
 */

public class MaterialEntry implements Serializable {

    private String pitem_id;
    private String item_id;
    private String item_name;

    public String getPitem_id() {
        return pitem_id;
    }

    public void setPitem_id(String pitem_id) {
        this.pitem_id = pitem_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
}
