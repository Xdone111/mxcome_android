package com.fenazola.mxcome.entry.project;

/**
 * Created by zm on 2017/5/15.
 */

public class Mdec1Entry {

    private String pid; //工程ID
    private String add_time; //工程创建时间
    private String address; //地址
    private String asset_id; //房屋ID
    private int de_type; //1全包,2半包,3轻包,10单项需求
    private String payed_price; //已支付总金额
    private int statu; //状态 1已发布 2施工中 3整改 4停工 5验收 6客户验收
    private String total_press; //总工程进度

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public int getDe_type() {
        return de_type;
    }

    public void setDe_type(int de_type) {
        this.de_type = de_type;
    }

    public String getPayed_price() {
        return payed_price;
    }

    public void setPayed_price(String payed_price) {
        this.payed_price = payed_price;
    }

    public int getStatu() {
        return statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }

    public String getTotal_press() {
        return total_press;
    }

    public void setTotal_press(String total_press) {
        this.total_press = total_press;
    }

    public String getDeName() {
        if(de_type == 1){
            return "全包";
        }else if(de_type == 2){
            return "半包";
        }else if(de_type == 3){
            return "轻包";
        }else if(de_type == 10){
            return "单项需求";
        }else{
            return de_type+"";
        }
    }
}
