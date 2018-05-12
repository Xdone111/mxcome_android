package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 */

public class EncycEntry implements Serializable {

    private int icon;
    private String title;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
