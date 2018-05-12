package com.fenazola.mxcome.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XDONE on 2017/6/7.
 */

public class WorkDesignerDetailEntry implements Serializable {
    private BaseEntry base;
    private String comment;
    private ShowXMEntry show_xm;
    private String commentNum;
    private String total_hp;
    private String total_zp;
    private String total_cp;

    public String getTotal_cp() {
        return total_cp;
    }

    public void setTotal_cp(String total_cp) {
        this.total_cp = total_cp;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getTotal_hp() {
        return total_hp;
    }

    public void setTotal_hp(String total_hp) {
        this.total_hp = total_hp;
    }

    public String getTotal_zp() {
        return total_zp;
    }

    public void setTotal_zp(String total_zp) {
        this.total_zp = total_zp;
    }

    public BaseEntry getBase() {
        return base;
    }

    public void setBase(BaseEntry base) {
        this.base = base;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ShowXMEntry getShow_xm() {
        return show_xm;
    }

    public void setShow_xm(ShowXMEntry show_xm) {
        this.show_xm = show_xm;
    }
}
