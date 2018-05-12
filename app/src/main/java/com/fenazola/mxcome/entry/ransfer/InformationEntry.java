package com.fenazola.mxcome.entry.ransfer;

/**
 * 传递模块杂志的实体类
 * Created by xhx on 2017/6/6.
 */

public class InformationEntry  {
    private String titleName;
    private String subtitleTitleName;
    private String imgUrl;
    public InformationEntry(){

    }
    public InformationEntry(String titleName,String subtitleTitleName,String imgUrl){
        this.titleName=titleName;
        this.subtitleTitleName=subtitleTitleName;
        this.imgUrl=imgUrl;
    }
    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getSubtitleTitleName() {
        return subtitleTitleName;
    }

    public void setSubtitleTitleName(String subtitleTitleName) {
        this.subtitleTitleName = subtitleTitleName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "InformationEntry{" +
                "titleName='" + titleName + '\'' +
                ", subtitleTitleName='" + subtitleTitleName + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
