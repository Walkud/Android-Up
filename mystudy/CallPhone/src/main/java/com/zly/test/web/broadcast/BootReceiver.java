package com.zly.test.web.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("BootReceiver", "BootReceiver  Action:" + intent.getAction());

//		Intent intent2 = new Intent();
//		intent2.setClass(context, TestService.class);
//		context.startService(intent2);
		
		
		
	}

}
