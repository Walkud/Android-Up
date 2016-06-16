package com.walkud.self.module.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 桌面Widgets
 * Created by Walkud on 16/6/8.
 */

public class TimeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Log.d("TimeWidgetProvider", "onUpdate()");
    }

    /**
     * 当用户从桌面上删除widgets的时候被调用
     */
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d("TimeWidgetProvider", "onDeleted()");
    }

    /**
     * 第一次往桌面添加Widgets的时候才会被调用，
     * 往后往桌面添加同类型的widgets时候不会被调用
     */
    @Override
    public void onEnabled(Context context) {
        Log.d("TimeWidgetProvider", "onEnabled()");
        //启动服务
        context.startService(new Intent(context, ClockService.class));
    }

    /**
     * 最后一个同类型widgets实例被删除的时候调用
     */
    @Override
    public void onDisabled(Context context) {
        Log.d("TimeWidgetProvider", "onDisabled()");
        //停止服务
        context.stopService(new Intent(context, ClockService.class));
    }
}
