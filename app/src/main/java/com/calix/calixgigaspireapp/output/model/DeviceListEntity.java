package com.calix.calixgigaspireapp.output.model;

import java.io.Serializable;

public class DeviceListEntity implements Serializable {

    private String deviceId;
    private String name;
    private SpeedEntity speed;
    private double signalStrength;
    private RouterEntity router;
    private SpeedEntity networkUsage;
    private int type;
    private int subType;
    private boolean connected2network;
    private String ipAddress;
    private String band;
    private int channel;
    private String ifType;

    public DeviceListEntity(){
        this.speed = new SpeedEntity();
    }

    public String getDeviceId() {
        return deviceId;
    }

    public DeviceListEntity setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public String getName() {
        return name;
    }

    public DeviceListEntity setName(String name) {
        this.name = name;
        return this;
    }

    public SpeedEntity getSpeed() {
        return speed;
    }

    public DeviceListEntity setSpeed(SpeedEntity speed) {
        this.speed = speed;
        return this;
    }

    public double getSignalStrength() {
        return signalStrength;
    }

    public DeviceListEntity setSignalStrength(double signalStrength) {
        this.signalStrength = signalStrength;
        return this;
    }

    public RouterEntity getRouter() {
        return router;
    }

    public DeviceListEntity setRouter(RouterEntity router) {
        this.router = router;
        return this;
    }

    public SpeedEntity getNetworkUsage() {
        return networkUsage;
    }

    public DeviceListEntity setNetworkUsage(SpeedEntity networkUsage) {
        this.networkUsage = networkUsage;
        return this;
    }

    public int getType() {
        return type;
    }

    public DeviceListEntity setType(int type) {
        this.type = type;
        return this;
    }

    public int getSubType() {
        return subType;
    }

    public DeviceListEntity setSubType(int subType) {
        this.subType = subType;
        return this;
    }

    public boolean isConnected2network() {
        return connected2network;
    }

    public DeviceListEntity setConnected2network(boolean connected2network) {
        this.connected2network = connected2network;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public DeviceListEntity setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public String getBand() {
        return band;
    }

    public DeviceListEntity setBand(String band) {
        this.band = band;
        return this;
    }

    public int getChannel() {
        return channel;
    }

    public DeviceListEntity setChannel(int channel) {
        this.channel = channel;
        return this;
    }

    public String getIfType() {
        return ifType;
    }

    public DeviceListEntity setIfType(String ifType) {
        this.ifType = ifType;
        return this;
    }
}
