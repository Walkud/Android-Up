package com.walkud.self.module.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class BluetoothHelper {

    public static final int REQUEST_CODE = 1;//开启蓝牙Request Code

    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDevice> unbondDevices; // 用于存放未配对蓝牙设备
    private ArrayList<BluetoothDevice> bondDevices;// 用于存放已配对蓝牙设备

    private BluetoothListener listener;

    public BluetoothHelper(BluetoothListener listener) {
        this.listener = listener;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.unbondDevices = new ArrayList<>();
        this.bondDevices = new ArrayList<>();
    }

    /**
     * 注册蓝牙广播监听
     */
    public void registerReceiver(Activity activity) {
        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        // 注册广播接收器，接收并处理搜索结果
        activity.registerReceiver(receiver, intentFilter);
    }

    /**
     * 注销蓝牙广播监听
     *
     * @param activity
     */
    public void unRegisterReceiver(Activity activity) {
        activity.unregisterReceiver(receiver);
    }

    /**
     * 打开蓝牙
     */
    public void openBluetooth(Activity activity) {
        Intent enableBtIntent = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableBtIntent, REQUEST_CODE);
    }

    /**
     * 获取未配对的蓝牙设备
     *
     * @return
     */
    public List<BluetoothDevice> getUnbondDevices() {
        return unbondDevices;
    }

    /**
     * 获取已配对的蓝牙设备
     *
     * @return
     */
    public List<BluetoothDevice> getBondDevices() {
        return bondDevices;
    }

    /**
     * 关闭蓝牙
     */
    public void closeBluetooth() {
        this.bluetoothAdapter.disable();
    }

    /**
     * 判断蓝牙是否打开
     *
     * @return boolean
     */
    public boolean isOpen() {
        return this.bluetoothAdapter.isEnabled();
    }

    /**
     * 获取蓝牙已绑定的设备
     */
    public List<BluetoothDevice> getBuletoothBondedDevices() {
        bondDevices.clear();
        Set<BluetoothDevice> devices = bluetoothAdapter
                .getBondedDevices();
        bondDevices.addAll(devices);
        return bondDevices;
    }

    /**
     * 搜索蓝牙设备
     */
    public void searchDevices() {
        //清空已绑定的设备
        this.bondDevices.clear();
        //清空未绑定的设备
        this.unbondDevices.clear();
        // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
        this.bluetoothAdapter.startDiscovery();
    }

    /**
     * 取消搜索蓝牙设备
     */
    public void cancelSearchDevice() {
        // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
        this.bluetoothAdapter.startDiscovery();
    }

    /**
     * 添加未绑定蓝牙设备到list集合
     *
     * @param device
     */
    public void addUnbondDevices(BluetoothDevice device) {
        if (!this.unbondDevices.contains(device)) {
            this.unbondDevices.add(device);
        }
    }

    /**
     * 添加已绑定蓝牙设备到list集合
     *
     * @param device
     */
    public void addBandDevices(BluetoothDevice device) {
        if (!this.bondDevices.contains(device)) {
            this.bondDevices.add(device);
        }
    }

    /**
     * 蓝牙广播接收器
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    addBandDevices(device);
                } else {
                    addUnbondDevices(device);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //开始搜索设置
                listener.startSearch();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                // 设备搜索完毕
                listener.searchEnd();
            }
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {//蓝牙状态改变
                if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                    // 打开蓝牙
                    listener.opened();
                } else if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
                    // 关闭蓝牙
                    listener.closed();
                }
            }
        }

    };

    /**
     * 蓝牙事件接口
     */
    public interface BluetoothListener {

        /**
         * 已打开
         */
        void opened();

        /**
         * 已关闭
         */
        void closed();

        /**
         * 开始搜索
         */
        void startSearch();

        /**
         * 已搜索完成
         */
        void searchEnd();

    }

}