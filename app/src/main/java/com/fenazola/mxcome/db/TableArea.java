package com.fenazola.mxcome.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Comparator;
import java.util.List;

/**
 * Created by zm on 2017/2/27.
 */

@DatabaseTable(tableName = "tb_area")
public class TableArea implements Comparator{

    @DatabaseField()
    private int areaid;

    @DatabaseField()
    private String area;

    @DatabaseField()
    private int parentid;

    @DatabaseField()
    private int areatype;

    private String op_type;

    private String version_code;

    @DatabaseField()
    private String keyword;

    public int getAreaid() {
        return areaid;
    }

    public void setAreaid(int areaid) {
        this.areaid = areaid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public int getAreatype() {
        return areatype;
    }

    public void setAreatype(int areatype) {
        this.areatype = areatype;
    }

    public String getOp_type() {
        return op_type;
    }

    public void setOp_type(String op_type) {
        this.op_type = op_type;
    }

    public String getVersion_code() {
        return version_code;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public int compare(Object o1, Object o2) {
        TableArea obj1 = (TableArea)o1;
        TableArea obj2 = (TableArea)o2;
        return obj1.getKeyword().compareTo(obj2.getKeyword());
    }
}
