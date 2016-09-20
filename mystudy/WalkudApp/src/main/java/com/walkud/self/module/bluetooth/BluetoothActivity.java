package com.walkud.self.module.bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.walkud.self.R;
import com.walkud.self.module.BaseActivity;
import com.walkud.self.module.bluetooth.BluetoothHelper.BluetoothListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 蓝牙搜索界面
 */
public class BluetoothActivity extends BaseActivity implements View.OnClickListener, BluetoothListener {

    @Bind(R.id.unbondDevices)
    ListView mUnbondDevices;// 未绑定设置ListView
    @Bind(R.id.bondDevices)
    ListView mBondDevices;// 已绑定设置ListView
    @Bind(R.id.openBluetooth_tb)
    Button mSwitchBT;
    @Bind(R.id.searchDevices)
    Button mSearchDevices;

    private BluetoothHelper bluetoothHelper;

    private ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);
        init();

    }

    /**
     * 初始化
     */
    private void init() {
        bluetoothHelper = new BluetoothHelper(this);
        bluetoothHelper.registerReceiver(this);

        progressDialog = createDialog();

        mSwitchBT.setOnClickListener(this);
        mSearchDevices.setOnClickListener(this);

        List<BluetoothDevice> devices = bluetoothHelper.getBuletoothBondedDevices();
        if (devices.size() > 0) {
            updateListUI();
        }

        updateBtnUI(bluetoothHelper.isOpen());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothHelper.unRegisterReceiver(this);
    }


    /**
     * 蓝牙已打开
     */
    @Override
    public void opened() {
        updateBtnUI(true);
        //开启蓝牙后自动搜索设备
        bluetoothHelper.searchDevices();
    }

    /**
     * 蓝牙已关闭
     */
    @Override
    public void closed() {
        updateBtnUI(false);
    }

    /**
     * 更新UI
     *
     * @param isOpen 蓝牙是否打开
     */
    private void updateBtnUI(boolean isOpen) {
        if (isOpen) {
            mSwitchBT.setText("关闭");
            mSearchDevices.setEnabled(true);
        } else {
            mSwitchBT.setText("打开");
            mSearchDevices.setEnabled(false);
        }
    }

    /**
     * 开始搜索
     */
    @Override
    public void startSearch() {
        progressDialog.show();
    }

    /**
     * 搜索结束
     */
    @Override
    public void searchEnd() {
        progressDialog.dismiss();
        updateListUI();
    }

    /**
     * 更新列表UI
     */
    public void updateListUI() {
        mUnbondDevices.setAdapter(new BluetoothListAdapter(this, bluetoothHelper.getUnbondDevices()));
        mBondDevices.setAdapter(new BluetoothListAdapter(this, bluetoothHelper.getBondDevices()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.openBluetooth_tb:
                if (bluetoothHelper.isOpen()) {
                    bluetoothHelper.closeBluetooth();
                } else {
                    bluetoothHelper.openBluetooth(this);
                }
                break;
            case R.id.searchDevices:
                bluetoothHelper.searchDevices();
                break;
        }
    }

    /**
     * 创建进度Dialog
     *
     * @return
     */
    private ProgressDialog createDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在搜索蓝牙设备...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                bluetoothHelper.cancelSearchDevice();
            }
        });
        return dialog;
    }
}
