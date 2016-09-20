package com.walkud.self.module.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.walkud.self.R;

import java.util.List;

/**
 * 蓝牙列表适配器
 * Created by Walkud on 16/9/20.
 */
public class BluetoothListAdapter extends BaseAdapter {

    public List<BluetoothDevice> devices;
    public LayoutInflater inflater;

    public BluetoothListAdapter(Activity activity, List<BluetoothDevice> devices) {
        this.devices = devices;
        this.inflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public BluetoothDevice getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.bluetooth_list_item, null);
        }

        BluetoothDevice device = getItem(position);
        ((TextView) convertView).setText(device.getName());

        return convertView;
    }
}
