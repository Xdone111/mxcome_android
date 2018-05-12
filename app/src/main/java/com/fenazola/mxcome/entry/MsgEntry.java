package com.fenazola.mxcome.entry;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zm on 2017/3/15.
 */

public class MsgEntry implements Serializable{

    private String destCid; //接受CID
    private String srcCid; //发送CID
    private String destName; //接受昵称
    private String srcName; //发送昵称
    private String destMxcomeNo; //接受MXCOME号
    private String srcMxcomeNo; //发送MXCOME号
    private String msgContent; //消息内容
    private int bizType; //业务类型 0:  官方  1：客户， 2：好友
    private int msgType;  //消息类型 0：文字，1：图片，2：语音
    private String msgTime; //  消息时间
    private String msgUrl; //  图片、语音不为空
    private String destPhotoUrl; //接受头像
    private String srcPhotoUrl;  //发送头像

    public List<MsgDataEntry> getData() {
        return data;
    }

    public void setData(List<MsgDataEntry> data) {
        this.data = data;
    }

    private int voiceTime; // 语音时长

    private List<MsgDataEntry> data; //抢单成功有值


    public String getDestCid() {
        return destCid;
    }

    public void setDestCid(String destCid) {
        this.destCid = destCid;
    }

    public String getSrcCid() {
        return srcCid;
    }

    public void setSrcCid(String srcCid) {
        this.srcCid = srcCid;
    }

    public String getDestName() {
        return destName;
    }

    public void setDestName(String destName) {
        this.destName = destName;
    }

    public String getSrcName() {
        return srcName;
    }

    public void setSrcName(String srcName) {
        this.srcName = srcName;
    }

    public String getDestMxcomeNo() {
        return destMxcomeNo;
    }

    public void setDestMxcomeNo(String destMxcomeNo) {
        this.destMxcomeNo = destMxcomeNo;
    }

    public String getSrcMxcomeNo() {
        return srcMxcomeNo;
    }

    public void setSrcMxcomeNo(String srcMxcomeNo) {
        this.srcMxcomeNo = srcMxcomeNo;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
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

    public String getDestPhotoUrl() {
        return destPhotoUrl;
    }

    public void setDestPhotoUrl(String destPhotoUrl) {
        this.destPhotoUrl = destPhotoUrl;
    }

    public String getSrcPhotoUrl() {
        return srcPhotoUrl;
    }

    public void setSrcPhotoUrl(String srcPhotoUrl) {
        this.srcPhotoUrl = srcPhotoUrl;
    }

    public int getVoiceTime() {
        return voiceTime;
    }

    public void setVoiceTime(int voiceTime) {
        this.voiceTime = voiceTime;
    }

    @Override
    public String toString() {
        return "MsgEntry{" +
                "destCid='" + destCid + '\'' +
                ", srcCid='" + srcCid + '\'' +
                ", destName='" + destName + '\'' +
                ", srcName='" + srcName + '\'' +
                ", destMxcomeNo='" + destMxcomeNo + '\'' +
                ", srcMxcomeNo='" + srcMxcomeNo + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", bizType=" + bizType +
                ", msgType=" + msgType +
                ", msgTime='" + msgTime + '\'' +
                ", msgUrl='" + msgUrl + '\'' +
                ", destPhotoUrl='" + destPhotoUrl + '\'' +
                ", srcPhotoUrl='" + srcPhotoUrl + '\'' +
                ", voiceTime=" + voiceTime +
                ", data=" + data +
                '}';
    }
}
