package com.fenazola.mxcome.entry;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/3/15.
 */

public class AcquaintanceEntry implements Serializable {
    private ImageView icon;
    private String name;
    private String content;
    private String number;
    private String autonym;
    private String add;


    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAutonym(String autonym) {
        this.autonym = autonym;
    }

    public void setAdd(String add) {
        this.add = add;
    }


    public String getAdd() {
        return add;
    }

    public ImageView getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getNumber() {
        return number;
    }

    public String getAutonym() {
        return autonym;
    }


}
