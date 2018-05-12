package com.fenazola.mxcome.entry.ransfer;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/3/18.
 */

public class MediaEntry implements Serializable {

    private int number;
    private ImageView image;
    private String content;
    private String time;
    private String nike;


    public String getNike() {
        return nike;
    }

    public void setNike(String nike) {
        this.nike = nike;
    }


    public void setNumber(int number) {
        this.number = number;
    }

    public void setImage(ImageView icon) {
        this.image = image;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public ImageView getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}
