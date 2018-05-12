package com.fenazola.mxcome.entry;

import com.fenazola.mxcome.utils.DataCache;

import java.io.Serializable;

/**
 * Created by XDONE on 2017/4/20.
 */

public class WatiPayEntry implements Serializable {

    private long add_time;  //订单时间
    private String adress;  //湖南省长沙市金马路"
    private String de_type;  //1全包,2半包,3轻包,10表示单项需求
    private String decora_name;  //工程名称
    private String end_work_time;  //施工结束时间
    private String mxcome_no;  //MXCOME号
    private String one_level_type;  //
    private String two_level_type;  //
    private String three_level_type;  //
    private String order_id;  //订单ID
    private String pid;  //工程ID
    private int order_statu;  //订单状态： 0订单创建未支付  1 已经支付 2被取消  3已施工
    private int is_timeout;  //是否超时 0 未超时、1 超时
    private int timeout;  //超时时间
    private String title;  //主题
    private String user_id;  //用户ID
    private String work_time;  //施工时间

    public long getAdd_time() {
        return add_time;
    }

    public void setAdd_time(long add_time) {
        this.add_time = add_time;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDe_type() {
        return de_type;
    }

    public void setDe_type(String de_type) {
        this.de_type = de_type;
    }

    public String getDecora_name() {
        return decora_name;
    }

    public void setDecora_name(String decora_name) {
        this.decora_name = decora_name;
    }

    public String getEnd_work_time() {
        return end_work_time;
    }

    public void setEnd_work_time(String end_work_time) {
        this.end_work_time = end_work_time;
    }

    public int getIs_timeout() {
        return is_timeout;
    }

    public void setIs_timeout(int is_timeout) {
        this.is_timeout = is_timeout;
    }

    public String getMxcome_no() {
        return mxcome_no;
    }

    public void setMxcome_no(String mxcome_no) {
        this.mxcome_no = mxcome_no;
    }

    public String getOne_level_type() {
        return one_level_type;
    }

    public void setOne_level_type(String one_level_type) {
        this.one_level_type = one_level_type;
    }

    public String getTwo_level_type() {
        return two_level_type;
    }

    public void setTwo_level_type(String two_level_type) {
        this.two_level_type = two_level_type;
    }

    public String getThree_level_type() {
        return three_level_type;
    }

    public void setThree_level_type(String three_level_type) {
        this.three_level_type = three_level_type;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getOrder_statu() {
        return order_statu;
    }

    public void setOrder_statu(int order_statu) {
        this.order_statu = order_statu;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getProjectOrderInfo(){
        return DataCache.dictMap.get(one_level_type) + " " + DataCache.dictMap.get(three_level_type);
    }
}
