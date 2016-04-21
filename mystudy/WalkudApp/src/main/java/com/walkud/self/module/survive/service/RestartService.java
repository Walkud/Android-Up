package com.walkud.self.module.survive.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by jan on 16/4/20.
 */
public class RestartService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            int pid = intent.getIntExtra("Pid", 0);

            Log.d("RestartService", "onStartCommand KillProcess:" + pid);
            if (pid != 0) {
                android.os.Process.killProcess(pid);
            }

            Intent grayIntent = new Intent(this, GrayService.class);
            startService(grayIntent);
        }
        stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }
}
