package com.lliramx.won.helper;

import com.lliramx.won.model.Device;

import java.util.ArrayList;
import java.util.List;

public class DeviceHelper {

    private DeviceHelper() {
    }

    public static List<Device> retrieveDevices() {
        List<Device> devices = new ArrayList<>();
        devices.add(new Device(1L, "Acer Ethernet", "192.168.0.255",
            "A8-1E-84-34-68-04", "DESKTOP-ROHTTE2"));
        return devices;
    }
}
