package com.example.dell.form_draft_4;

public class nw_VehicleInfo{

    private String nw_vehicleNumber;
    private String nw_lastTime;
    private String nw_lastAddress;

    public nw_VehicleInfo(String nw_vehicleNumber, String nw_lastTime, String nw_lastAddress) {
        this.nw_vehicleNumber = nw_vehicleNumber;
        this.nw_lastTime = nw_lastTime;
        this.nw_lastAddress = nw_lastAddress;
    }

    public String getNw_vehicleNumber() {
        return nw_vehicleNumber;
    }

    public void setNw_vehicleNumber(String nw_vehicleNumber) {
        this.nw_vehicleNumber = nw_vehicleNumber;
    }

    public String getNw_lastTime() {
        return nw_lastTime;
    }

    public void setNw_lastTime(String nw_lastTime) {
        this.nw_lastTime = nw_lastTime;
    }

    public String getNw_lastAddress() {
        return nw_lastAddress;
    }

    public void setNw_lastAddress(String nw_lastAddress) {
        this.nw_lastAddress = nw_lastAddress;
    }
}


