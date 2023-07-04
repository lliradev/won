package com.lliramx.won;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lliramx.won.helper.PacketHelper;
import com.lliramx.won.model.Device;
import com.lliramx.won.util.Alert;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {

    private List<Device> devices;
    private final LayoutInflater inflater;
    private final Context context;

    public DeviceAdapter(List<Device> devices, Context context) {
        this.devices = devices;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DeviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.activity_device, null);
        return new ViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceAdapter.ViewHolder holder, int position) {
        holder.bindData(devices.get(position));
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final ImageView icon;
        private final TextView alias;
        private final TextView hostname;
        private final ImageButton power;

        ViewHolder(Context context, @NonNull View itemView) {
            super(itemView);
            this.context = context;
            icon = itemView.findViewById(R.id.imv_icon);
            alias = itemView.findViewById(R.id.txv_alias);
            hostname = itemView.findViewById(R.id.txv_hostname);
            power = itemView.findViewById(R.id.imb_power);
        }

        void bindData(final Device item) {
            Alert alert = new Alert(context);
            alias.setText(item.getAlias());
            hostname.setText(item.getHostname());
            power.setOnClickListener(v -> {
                Thread thread = new Thread(() -> {
                    PacketHelper packetHelper = new PacketHelper(context);
                    packetHelper.send(item.getIpAddress(), item.getMacAddress(), item.getPort());
                });
                thread.start();
                alert.show("Success", "Wake-on-LAN packet sent.");
            });
        }
    }
}
