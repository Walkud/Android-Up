package com.walkud.self.module.survive.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.walkud.self.R;
import com.walkud.self.module.survive.SurviveActivity;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 正常的系统前台进程，会在系统通知栏显示一个Notification通知图标
 *
 * @author clock
 * @since 2016-04-12
 */
public class WhiteService extends Service {

    private final static String TAG = WhiteService.class.getSimpleName();

    private final static int FOREGROUND_ID = 1000;

    private Timer timer = new Timer();

    private NotificationManager mNotificationManager;
    private Notification mNotification;

    @Override
    public void onCreate() {
        Log.i(TAG, "WhiteService->onCreate");
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "WhiteService->onStartCommand");

        startForeground(FOREGROUND_ID, getNoti(0));

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Log.d(TAG, "WhiteService 活着");

                handler.sendEmptyMessage(0);
            }
        }, 3000, 5000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "WhiteService->onDestroy");
        super.onDestroy();
    }

    private Notification getNoti(int count) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Foreground");
        builder.setContentText("I am a foreground service count:" + count);
        builder.setContentInfo("Content Info");
        builder.setWhen(System.currentTimeMillis());
        Intent activityIntent = new Intent(this, SurviveActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        return builder.build();
    }

    private Handler handler = new Handler() {

        private int count = 0;

        @Override
        public void handleMessage(Message msg) {

            mNotification = getNoti(++count);
            // 最后别忘了通知一下,否则不会更新
            mNotificationManager.notify(FOREGROUND_ID, mNotification);

        }
    };
}
