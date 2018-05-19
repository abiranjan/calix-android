package com.calix.calixgigaspireapp.output.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DeviceListResponse implements Serializable {

    ArrayList<DeviceListEntity> devices;

    public DeviceListResponse(){
        devices = new ArrayList<>();
    }

    public ArrayList<DeviceListEntity> getDevices() {
        return devices;
    }

    public DeviceListResponse setDevices(ArrayList<DeviceListEntity> devices) {
        this.devices = devices;
        return this;
    }
}
