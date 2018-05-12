package com.fenazola.mxcome.entry;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zm on 2017/2/28.
 */

public class InitEntry implements Serializable {

    private String item_name;

    private String item_value;

    private String scope_system;

    private ParamsEntry params;

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_value() {
        return item_value;
    }

    public void setItem_value(String item_value) {
        this.item_value = item_value;
    }

    public String getScope_system() {
        return scope_system;
    }

    public void setScope_system(String scope_system) {
        this.scope_system = scope_system;
    }

    public ParamsEntry getParams() {
        return params;
    }

    public void setParams(ParamsEntry params) {
        this.params = params;
    }
}
