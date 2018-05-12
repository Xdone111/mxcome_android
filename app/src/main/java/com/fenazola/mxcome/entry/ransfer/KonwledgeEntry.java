package com.fenazola.mxcome.entry.ransfer;

import java.io.Serializable;

/**
 * Created by xuhuixiang on 2017/8/5.
 */

public class KonwledgeEntry implements Serializable {
    private String id;
    private String parent_id;
    private String category_name;
    private String background;
    private String icon;
    private String column_type;

    public KonwledgeEntry() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColumn_type() {
        return column_type;
    }

    public void setColumn_type(String column_type) {
        this.column_type = column_type;
    }
}
