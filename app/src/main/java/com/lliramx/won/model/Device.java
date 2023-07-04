package com.lliramx.won.model;

public class Device {
    private final Long id;
    private final String alias;
    private final String hostname;
    private final String ipAddress;
    private final String macAddress;

    public Device(Long id, String alias, String ipAddress, String macAddress, String hostname) {
        this.id = id;
        this.alias = alias;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.hostname = hostname;
    }

    public String getAlias() {
        return alias;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return 9;
    }
}
