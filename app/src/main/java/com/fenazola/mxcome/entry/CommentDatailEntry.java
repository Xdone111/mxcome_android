package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/5/23.
 */

public class CommentDatailEntry implements Serializable {

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private String nikeName;
    private String time;
    private String label;

}
