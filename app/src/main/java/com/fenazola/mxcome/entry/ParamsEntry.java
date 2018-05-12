package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/4/8.
 */
public class ParamsEntry implements Serializable {

    private String down_path;
    private String ver_no;
    private String os_type;
    private String push_time;

    public String getDown_path() {
        return down_path;
    }

    public void setDown_path(String down_path) {
        this.down_path = down_path;
    }

    public String getVer_no() {
        return ver_no;
    }

    public void setVer_no(String ver_no) {
        this.ver_no = ver_no;
    }

    public String getOs_type() {
        return os_type;
    }

    public void setOs_type(String os_type) {
        this.os_type = os_type;
    }

    public String getPush_time() {
        return push_time;
    }

    public void setPush_time(String push_time) {
        this.push_time = push_time;
    }
}
