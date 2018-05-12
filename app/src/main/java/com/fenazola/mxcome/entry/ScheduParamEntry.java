package com.fenazola.mxcome.entry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zm on 2017/2/16.
 */

public class ScheduParamEntry implements Serializable {

    private int request_code; // 1：轻包， 2：半包， 3：全包
    private List<SchemeEntry> mData;

    public int getRequest_code() {
        return request_code;
    }

    public void setRequest_code(int request_code) {
        this.request_code = request_code;
    }

    public List<SchemeEntry> getmData() {
        return mData;
    }

    public void setmData(List<SchemeEntry> mData) {
        this.mData = mData;
    }

    public List<SchemeEntry> getWorker(){
        List<SchemeEntry> worker = new ArrayList<>();
        for(SchemeEntry item : mData){
            if(item.isWorker()){
                worker.add(item);
            }
        }
        return worker;
    }

    public List<SchemeEntry> getMaterial(){
        List<SchemeEntry> material = new ArrayList<>();
        for(SchemeEntry item : mData){
            if(item.isMaterial()){
                material.add(item);
            }
        }
        return material;
    }

    public ArrayList<SchemeEntry> getMaterialEnable(){
        ArrayList<SchemeEntry> material = new ArrayList<>();
        for(SchemeEntry item : mData){
            if(item.isMaterial() && item.isEnable()){
                material.add(item);
            }
        }
        return material;
    }

    public ArrayList<Integer> getMaterialDisable(){
        ArrayList<Integer> clumIds = new ArrayList<>();
        for(SchemeEntry item : mData){
            if(item.isMaterial() && !item.isEnable()){
                clumIds.add(item.getClum_id());
            }
        }
        return clumIds;
    }

}
