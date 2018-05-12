package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 */

public class MealEntry implements Serializable {

    private String rule_scrope;
    private String sort_no;
    private String plan_id;
    private String plan_name;
    private String plan_executor;
    private String detail;
    private String alias;

    public String getRule_scrope() {
        return rule_scrope;
    }

    public void setRule_scrope(String rule_scrope) {
        this.rule_scrope = rule_scrope;
    }

    public String getSort_no() {
        return sort_no;
    }

    public void setSort_no(String sort_no) {
        this.sort_no = sort_no;
    }

    public String getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(String plan_id) {
        this.plan_id = plan_id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getPlan_executor() {
        return plan_executor;
    }

    public void setPlan_executor(String plan_executor) {
        this.plan_executor = plan_executor;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
