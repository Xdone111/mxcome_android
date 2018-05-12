package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 * 咨询消息
 */

public class ConsultEntry implements Serializable {

    private String title;
    private String summary;
    private String status;

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
