package com.fenazola.mxcome.entry;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 */

public class TopicEntry implements Serializable {

    private String title;
    private String summary;
    private String cnt1;
    private String cnt2;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCnt1() {
        return cnt1;
    }

    public void setCnt1(String cnt1) {
        this.cnt1 = cnt1;
    }

    public String getCnt2() {
        return cnt2;
    }

    public void setCnt2(String cnt2) {
        this.cnt2 = cnt2;
    }
}
