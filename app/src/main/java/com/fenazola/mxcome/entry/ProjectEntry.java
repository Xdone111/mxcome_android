package com.fenazola.mxcome.entry;

import com.fenazola.mxcome.entry.project.PremiseEntry;
import com.fenazola.mxcome.utils.GsonUtils;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 */

public class ProjectEntry implements Serializable {

    private String cust_id; //客户ID
    private String cust_mxcome_no; //客户MXCOME号
    private String customer; //客户昵称
    private String decora_id; //工程ID
    private String decora_json; //工程信息
    private String is_recom; //是否推荐
    private String mx_comeno; //自己的MXCOME号
    private String user_id; //自己的ID

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getCust_mxcome_no() {
        return cust_mxcome_no;
    }

    public void setCust_mxcome_no(String cust_mxcome_no) {
        this.cust_mxcome_no = cust_mxcome_no;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDecora_id() {
        return decora_id;
    }

    public void setDecora_id(String decora_id) {
        this.decora_id = decora_id;
    }

    public PremiseEntry getPremise() {
        return GsonUtils.getObjFromJSON(decora_json, PremiseEntry.class);
    }


    public String getIs_recom() {
        return is_recom;
    }

    public void setIs_recom(String is_recom) {
        this.is_recom = is_recom;
    }

    public String getMx_comeno() {
        return mx_comeno;
    }

    public void setMx_comeno(String mx_comeno) {
        this.mx_comeno = mx_comeno;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
