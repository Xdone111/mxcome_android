package com.fenazola.mxcome.entry.project;

import java.io.Serializable;

/**
 * Created by zm on 2017/3/4.
 */

public class StyleEntry implements Serializable {

    private String style_id;

    private String pic;

    private String descinfo;

    public String getStyle_id() {
        return style_id;
    }

    public void setStyle_id(String style_id) {
        this.style_id = style_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescinfo() {
        return descinfo;
    }

    public void setDescinfo(String descinfo) {
        this.descinfo = descinfo;
    }
}
