package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/4/6.
 */

public class BankCardEntry implements Serializable {
    private int icon;
    private String name;
    private String number;
    private String onlyOne;
    private String onlyDay;
    private String onlyMonth;

    public String getOnlyOne() {
        return onlyOne;
    }

    public void setOnlyOne(String onlyOne) {
        this.onlyOne = onlyOne;
    }

    public String getOnlyDay() {
        return onlyDay;
    }

    public void setOnlyDay(String onlyDay) {
        this.onlyDay = onlyDay;
    }

    public String getOnlyMonth() {
        return onlyMonth;
    }

    public void setOnlyMonth(String onlyMonth) {
        this.onlyMonth = onlyMonth;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
