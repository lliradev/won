package com.lliramx.won.helper;

import android.content.Context;
import android.util.Log;

import com.lliramx.won.util.Alert;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PacketHelper {

    private final Context context;

    public PacketHelper(Context context) {
        this.context = context;
    }

    public void send(String ipAddress, String macAddress, int port) {
        Alert alert = new Alert(context);
        if (ipAddress == null) {
            alert.show("Warning", "IP Address must be null");
            return;
        }
        if (macAddress == null) {
            alert.show("Warning", "MAC Address must be null");
            return;
        }
        try {
            byte[] macBytes = getMacBytes(macAddress);
            byte[] bytes = new byte[6 + 16 * macBytes.length];

            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            // @TODO: Para que funciona esto?
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }

            InetAddress address = InetAddress.getByName(ipAddress);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
            // @TODO: Improve
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();

            Log.i("Success", "Wake-on-LAN packet sent.");
        } catch (IOException e) {
            Log.e("Failed", e.getMessage());
            alert.show("Failed", "Failed to send Wake-on-LAN packet: " +
                e.getMessage());
        }
    }

    private byte[] getMacBytes(String macStr) throws IllegalArgumentException {
        if (macStr == null) throw new IllegalArgumentException("Invalid MAC address.");
        byte[] bytes = new byte[6];
        String[] hex = macStr.split("([:\\-])");
        if (hex.length != 6) throw new IllegalArgumentException("Invalid MAC address.");
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }
}
