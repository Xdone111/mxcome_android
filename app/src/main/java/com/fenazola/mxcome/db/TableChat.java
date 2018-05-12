package com.fenazola.mxcome.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by zm on 2017/2/27.
 */

@DatabaseTable(tableName = "tb_chat")
public class TableChat {

    @DatabaseField(generatedId = true)
    private int msgId; //消息ID

    @DatabaseField()
    private int groupId; //会话ID

    @DatabaseField()
    private String name; //用户名称

    @DatabaseField()
    private String msgContent; //消息内容

    @DatabaseField()
    private int msgType;  //消息类型 0：文字，1：图片，2：语音

    @DatabaseField()
    private int userType; //用户类型 0：别人，1：自己

    @DatabaseField()
    private String msgTime; //消息时间

    @DatabaseField()
    private String msgUrl; // 图片、语音不为空

    @DatabaseField()
    private int voiceTime; // 语音时长

    @DatabaseField()
    private int unread; // 0: 未读， 1：已读

    @DatabaseField()
    private int sendStatus; //发送标示 0：发送中，1：成功， 2：失败

    @DatabaseField(defaultValue = "")
    private String localUrl; // 本地图片

    private int msgWidth; //宽度

    private int msgHeight; //高度

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getMsgUrl() {
        return msgUrl;
    }

    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl;
    }

    public int getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(int voiceTime) {
        this.voiceTime = voiceTime;
    }

    public int getUnread() {
        return unread;
    }

    public boolean getUnreadStatus(){
        return unread==0;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public int getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(int sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getLocalUrl() {
        return localUrl;
    }

    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    public int getMsgWidth() {
        return msgWidth;
    }

    public void setMsgWidth(int msgWidth) {
        this.msgWidth = msgWidth;
    }

    public int getMsgHeight() {
        return msgHeight;
    }

    public void setMsgHeight(int msgHeight) {
        this.msgHeight = msgHeight;
    }

    @Override
    public String toString() {
        return "TableChat{" +
                "msgId=" + msgId +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", msgType=" + msgType +
                ", userType=" + userType +
                ", msgTime='" + msgTime + '\'' +
                ", msgUrl='" + msgUrl + '\'' +
                ", voiceTime=" + voiceTime +
                ", unread=" + unread +
                ", sendStatus=" + sendStatus +
                ", localUrl='" + localUrl + '\'' +
                ", msgWidth=" + msgWidth +
                ", msgHeight=" + msgHeight +
                '}';
    }
}
