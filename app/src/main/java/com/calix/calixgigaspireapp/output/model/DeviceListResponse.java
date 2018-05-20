package com.calix.calixgigaspireapp.output.model;

import java.io.Serializable;
import java.util.ArrayList;

public class DeviceListResponse implements Serializable {

    ArrayList<DeviceEntity> devices;

    public DeviceListResponse(){
        devices = new ArrayList<>();
    }

    public ArrayList<DeviceEntity> getDevices() {
        return devices;
    }

    public DeviceListResponse setDevices(ArrayList<DeviceEntity> devices) {
        this.devices = devices;
        return this;
    }
}
