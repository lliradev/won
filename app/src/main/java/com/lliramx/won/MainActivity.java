package com.lliramx.won;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    private static final int PORT = 9;
    private EditText txtIp;
    private EditText txtMac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtIp = findViewById(R.id.txt_ip);
        txtMac = findViewById(R.id.txt_mac);
        Button btnWake = findViewById(R.id.btn_wake);

        btnWake.setOnClickListener(v -> {
            String ipAddress = txtIp.getText().toString();
            String macAddress = txtMac.getText().toString();

            Thread thread = new Thread(() -> wakeOnLan(ipAddress, macAddress));
            thread.start();
        });
    }

    private void wakeOnLan(String ipAddress, String macAddress) {
        if (ipAddress == null && macAddress == null) {
            System.out.println("Usage: java WakeOnLan <broadcast-ip> <mac-address>");
            System.out.println("Example: java WakeOnLan 192.168.0.255 00:0D:61:08:22:4A");
            System.out.println("Example: java WakeOnLan 192.168.0.255 00-0D-61-08-22-4A");
            System.exit(1);
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
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, PORT);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();

            System.out.println("Wake-on-LAN packet sent.");
        } catch (Exception e) {
            System.err.println("Failed to send Wake-on-LAN packet: " + e.getMessage());
            System.exit(1);
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
