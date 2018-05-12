package com.fenazola.mxcome.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/27.
 * 消息会话表
 */

@DatabaseTable(tableName = "tb_talk")
public class TableTalk implements Serializable{

    @DatabaseField(generatedId = true)
    private int groupId; //会话ID

    @DatabaseField()
    private String cid; //推送ID

    @DatabaseField()
    private String icon; //会话Url

    @DatabaseField()
    private String groupName; //会话名称

    @DatabaseField()
    private String lastTime; //最新时间

    @DatabaseField()
    private String lastMsg; //最新消息

    @DatabaseField()
    private int unreadCnt; //未读条数

    @DatabaseField()
    private int bizType; //业务类型 0:官方，1:客户，2:好友

    @DatabaseField()
    private int msgType;  //消息类型 0：文字，1：图片，2：语音

    @DatabaseField()
    private String mxcomeNo; //MXCOME号

    @DatabaseField()
    private String meMxcomeNo; //我的MXCOME号

    @DatabaseField()
    private String special; //特别关心

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public int getUnreadCnt() {
        return unreadCnt;
    }

    public void setUnreadCnt(int unreadCnt) {
        this.unreadCnt = unreadCnt;
    }

    public int getBizType() {
        return bizType;
    }

    public void setBizType(int bizType) {
        this.bizType = bizType;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getMxcomeNo() {
        return mxcomeNo;
    }

    public void setMxcomeNo(String mxcomeNo) {
        this.mxcomeNo = mxcomeNo;
    }

    public String getMeMxcomeNo() {
        return meMxcomeNo;
    }

    public void setMeMxcomeNo(String meMxcomeNo) {
        this.meMxcomeNo = meMxcomeNo;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    @Override
    public String toString() {
        return "TableTalk{" +
                "groupId=" + groupId +
                ", cid='" + cid + '\'' +
                ", icon='" + icon + '\'' +
                ", groupName='" + groupName + '\'' +
                ", lastTime='" + lastTime + '\'' +
                ", lastMsg='" + lastMsg + '\'' +
                ", unreadCnt=" + unreadCnt +
                ", bizType=" + bizType +
                ", msgType=" + msgType +
                ", mxcomeNo='" + mxcomeNo + '\'' +
                ", meMxcomeNo='" + meMxcomeNo + '\'' +
                ", special='" + special + '\'' +
                '}';
    }
}
