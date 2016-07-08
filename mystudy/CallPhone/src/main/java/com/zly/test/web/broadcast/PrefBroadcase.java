package com.zly.test.web.broadcast;

import com.zly.test.web.SharedPreferencesUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 接管设置广播
 */
public class PrefBroadcase extends BroadcastReceiver {

	public static final String PREF_ACTION = "com.wom.task.over.PrefBroadcase";

	@Override
	public void onReceive(Context context, Intent intent) {

		// 在当前进程中，改变用户设置
		if (PREF_ACTION.equals(intent.getAction())) {
			String key = intent.getStringExtra("PrefKey");
			boolean value = intent.getBooleanExtra("PrefValue", false);

			SharedPreferencesUtil.putBoolean(context, key, value);
		}

	}

}
