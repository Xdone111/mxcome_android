package com.fenazola.mxcome.entry;

/**
 * Created by XDONE on 2017/4/20.
 */

public class DemandEntry  {

    private String material;
    private String addr;
    private String time;
    private String cancel;
    private String remain_time;
    private String money;
    private String state;

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getRemain_time() {
        return remain_time;
    }

    public void setRemain_time(String remain_time) {
        this.remain_time = remain_time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
