package com.walkud.self.module.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.walkud.self.R;
import com.walkud.self.module.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 学习进程间通信Messenger,可以不用写aild文件
 */
public class MessengerActivity extends BaseActivity implements View.OnClickListener {

    public static final int SUM_NUM = 0x001;

    @BindView(R.id.sum_btn)
    Button sumBtn;
    @BindView(R.id.text_view)
    TextView textView;

    private Messenger serviceMessenger;
    private Messenger clientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SUM_NUM:
                    textView.setText("和为：" + msg.arg2);
                    break;
            }

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        ButterKnife.bind(this);
        sumBtn.setOnClickListener(this);
        sumBtn.setEnabled(false);

        bindService();
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.sum_btn:
                sum();
                break;
        }
    }

    private void sum() {
        try {
            Message message = Message.obtain();
            message.what = SUM_NUM;
            message.arg1 = 3;
            message.arg2 = 5;
            message.replyTo = clientMessenger;
            serviceMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
            showToast("调用远程服务失败");
        }
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceMessenger = new Messenger(service);
            sumBtn.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceMessenger = null;
        }
    };

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.walkud.self.messenger.study");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setPackage(getPackageName());
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
