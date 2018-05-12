package com.fenazola.mxcome.entry.project;

import java.io.Serializable;

/**
 * Created by zm on 2017/2/16.
 */

public class HouseEntry implements Serializable {

    private String carpetArea;
    private String galley; //厨
    private String garage; //车库
    private String hall; //厅
    private String outdoorArea;
    private String room; //室
    private String toilet; //卫生间


    public String getCarpetArea() {
        return carpetArea;
    }

    public void setCarpetArea(String carpetArea) {
        this.carpetArea = carpetArea;
    }

    public String getGalley() {
        return galley;
    }

    public void setGalley(String galley) {
        this.galley = galley;
    }

    public String getGarage() {
        return garage;
    }

    public void setGarage(String garage) {
        this.garage = garage;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getOutdoorArea() {
        return outdoorArea;
    }

    public void setOutdoorArea(String outdoorArea) {
        this.outdoorArea = outdoorArea;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getToilet() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet = toilet;
    }
}
