package com.fenazola.mxcome.entry.project;

import java.util.List;

/**
 * Created by zm on 2017/5/15.
 */

public class Mdec2Entry {

    private String wsm_id; //步骤ID
    private String btime; //开始时间
    private String stage; //步骤名称
    private String decora_id; //工程ID
    private String etime; //结束时间
    private int state; //状态 //1施工中，2整改，3完成，4验收
    private String bfb; //每一步的进度百分比
    private List<PicsEntry> pics; //施工图片
    private String AstepName; //从哪步
    private String AstepNo; //从哪步ID
    private String BstepName; //到哪步
    private String BstepNo; //到哪步ID
    private String sg_gj; //工价
    private String sg_gs; //工时

    public String getWsm_id() {
        return wsm_id;
    }

    public void setWsm_id(String wsm_id) {
        this.wsm_id = wsm_id;
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getDecora_id() {
        return decora_id;
    }

    public void setDecora_id(String decora_id) {
        this.decora_id = decora_id;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBfb() {
        return bfb;
    }

    public void setBfb(String bfb) {
        this.bfb = bfb;
    }

    public List<PicsEntry> getPics() {
        return pics;
    }

    public void setPics(List<PicsEntry> pics) {
        this.pics = pics;
    }

    public String getAstepName() {
        return AstepName;
    }

    public void setAstepName(String astepName) {
        AstepName = astepName;
    }

    public String getAstepNo() {
        return AstepNo;
    }

    public void setAstepNo(String astepNo) {
        AstepNo = astepNo;
    }

    public String getBstepName() {
        return BstepName;
    }

    public void setBstepName(String bstepName) {
        BstepName = bstepName;
    }

    public String getBstepNo() {
        return BstepNo;
    }

    public void setBstepNo(String bstepNo) {
        BstepNo = bstepNo;
    }

    public String getSg_gj() {
        return sg_gj;
    }

    public void setSg_gj(String sg_gj) {
        this.sg_gj = sg_gj;
    }

    public String getSg_gs() {
        return sg_gs;
    }

    public void setSg_gs(String sg_gs) {
        this.sg_gs = sg_gs;
    }

    public String getStateName(){
        if(state == 1){
            return "施工中";
        } else if(state == 2){
            return "整改";
        } else if(state == 3){
            return "已完成";
        } else if(state == 4){
            return "已验收";
        } else {
            return "";
        }
    }
}
