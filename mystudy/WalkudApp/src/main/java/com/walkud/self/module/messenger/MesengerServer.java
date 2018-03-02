package com.walkud.self.module.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

/**
 * Mesenger 进程间消息通信
 * Created by Walkud on 18/1/22.
 */

public class MesengerServer extends Service {

    public static final int SUM_NUM = 0x001;

    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {

            Message msgToClient = Message.obtain(msg);
            switch (msg.what) {
                case SUM_NUM:
                    try {
                        Thread.sleep(2000);
                        msgToClient.what = SUM_NUM;
                        msgToClient.arg2 = msg.arg1 + msg.arg2;
                        msgToClient.replyTo.send(msgToClient);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }

            super.handleMessage(msg);
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }
}
