package com.walkud.self.module.survive.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.clock.daemon.BinderPool;
import com.clock.daemon.GrayServiceHelper;
import com.walkud.self.R;
import com.walkud.self.module.MainActivity;
import com.walkud.self.module.survive.receiver.WakeReceiver;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 灰色保活手法创建的Service进程
 *
 * @author Clock
 * @since 2016-04-12
 */
public class GrayService extends Service {

    public final static int GRAY_BINDER_CODE = 1234;

    private final static String TAG = GrayService.class.getSimpleName();
    /**
     * 定时唤醒的时间间隔，5分钟
     */
//    private final static int ALARM_INTERVAL = 5 * 60 * 1000;
    private final static int ALARM_INTERVAL = 20 * 1000;
    private final static int WAKE_REQUEST_CODE = 6666;

    public final static int GRAY_SERVICE_ID = -10010;

    private IBinder mBinderPool = new BinderPool.Stub() {
        @Override
        public IBinder getBinderHelper(int binderCode) throws RemoteException {
            if (binderCode == GRAY_BINDER_CODE) {
                return mGrayServiceHelper;
            } else {
                return null;
            }
        }
    };

    private Timer timer = new Timer();
    private int count = 0;

    private static int setp = 0;

    private IBinder mGrayServiceHelper = new GrayServiceHelper.Stub() {
        @Override
        public void say(String something) throws RemoteException {
            Log.i(TAG, "GrayService say: " + something);
        }

        @Override
        public int getSetp() throws RemoteException {
            return setp;
        }
    };

    @Override
    public void onCreate() {
        Log.i(TAG, "GrayService->onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "GrayService->onStartCommand");
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, getNoti(this));
        }

        //发送唤醒广播来促使挂掉的UI进程重新启动起来
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent alarmIntent = new Intent();
//        alarmIntent.setAction(WakeReceiver.GRAY_WAKE_ACTION);
//        alarmIntent.putExtra("Flag", "AlarmManager");
//        PendingIntent operation = PendingIntent.getBroadcast(this, WAKE_REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_INTERVAL, operation);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setp++;
                Log.d(TAG, "进程保活触发 count:" + count + ",setp:" + setp);
//                Intent wakeIntent = new Intent();
//                wakeIntent.putExtra("Flag", "Timer");
//                wakeIntent.setAction(WakeReceiver.GRAY_WAKE_ACTION);
//                sendBroadcast(wakeIntent);
//                if (count++ >= 5) {
//                    count = 0;
//                    Intent i = new Intent(GrayService.this, RestartService.class);
//                    i.putExtra("Pid", android.os.Process.myPid());
//                    startService(i);
//                }

            }
        }, 3000, 3000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "GrayService->onDestroy");
        super.onDestroy();
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {

        @Override
        public void onCreate() {
            Log.i(TAG, "InnerService -> onCreate");
            super.onCreate();
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.i(TAG, "InnerService -> onStartCommand");
            startForeground(GRAY_SERVICE_ID, getNoti(this));
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }

        @Override
        public void onDestroy() {
            Log.i(TAG, "InnerService -> onDestroy");
            super.onDestroy();
        }
    }

    private static Notification getNoti(Context context) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Survive Title")
                .setContentText("Survive ContentText");
        mBuilder.setTicker("Survive Ticker");//第一次提示消息的时候显示在通知栏上
        mBuilder.setNumber(12);
        return mBuilder.build();
    }
}
