package com.walkud.self.module.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.walkud.self.R;
import com.walkud.self.module.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Walkud on 16/6/8.
 */

public class ClockService extends Service {

    private static final String TAG = ClockService.class.getSimpleName();

    // 将显示小时、分钟、秒钟的ImageView定义成数组
    private int[] digitViews = new int[]{R.id.time1, R.id.time2, R.id.time3, R.id.time4, R.id.time5};

    private Timer timer;

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {

            Log.d(TAG, "TimerTask run()");

            /** 获取并格式化当前时间 */
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
            String time = sdf.format(new Date());
            char[] times = time.toCharArray();
            /** 获得显示时间的View */
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.app_widget_clock);
            for (int i = 0; i < times.length; i++) {
                views.setTextViewText(digitViews[i], String.valueOf(times[i]));
            }
            /** 调用主界面 */
            Intent intent = new Intent(ClockService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), 0, intent, 0);
            views.setOnClickPendingIntent(R.id.clock_layout, pendingIntent);

            /** 更新时间的显示 */
            AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(getApplicationContext());
            // 将AppWidgetProvider子类实例包装成ComponentName对象
            ComponentName componentName = new ComponentName(
                    getApplicationContext(), TimeWidgetProvider.class);
            // 调用AppWidgetManager将remoteViews添加到ComponentName中
            appWidgetManager.updateAppWidget(componentName, views);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate()");

        // 定义计时器
        timer = new Timer();
        // 启动周期性调度
        timer.schedule(timerTask, 0, 1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand()");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
        timer.cancel();
        timer = null;
    }
}
