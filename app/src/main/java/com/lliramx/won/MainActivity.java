package com.lliramx.won;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lliramx.won.helper.DeviceHelper;
import com.lliramx.won.model.Device;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAdapter();
    }

    private void initAdapter() {
        List<Device> devices = DeviceHelper.retrieveDevices();

        DeviceAdapter deviceAdapter = new DeviceAdapter(devices, this);
        RecyclerView recyclerView = findViewById(R.id.rcv_devices);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(deviceAdapter);
    }
}
