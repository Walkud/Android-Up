package com.zly.test.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.zly.test.web.broadcast.PrefBroadcase;

public class TaskOverMaskingActivity extends Activity implements
		OnClickListener {

	private static final String TAG = "TaskOverMaskingActivity";

	private Button button1, button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置无Title
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_task_over_masking);
		int pid = android.os.Process.myPid();
		Log.d(TAG, "onCreate:" + pid);

		button1 = (Button) findViewById(R.id.task_over_btn);
		button2 = (Button) findViewById(R.id.not_task_over_btn);
		button1.setOnClickListener(this);
		button2.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent();
		Intent bIntent = new Intent();
		boolean value = false;
		switch (arg0.getId()) {
		case R.id.task_over_btn:// 接管
			value = true;
			SharedPreferencesUtil.putBoolean(getApplicationContext(),
					AppKeys.KEY_PHONE_TASK_OVER, value);

			intent.setClass(this, PhoneActivity.class);
			startActivity(intent);

			finish();
			break;
		case R.id.not_task_over_btn:// 放弃接管
			value = false;
			SharedPreferencesUtil.putBoolean(getApplicationContext(),
					AppKeys.KEY_PHONE_TASK_OVER, value);

			intent.setAction("android.intent.action.DIAL");
			intent.setClassName("com.android.contacts",
					"com.android.contacts.DialtactsActivity");
			startActivity(intent);

			finish();
			break;
		}

		// 发送设置Key Value广播
		bIntent.setAction(PrefBroadcase.PREF_ACTION);
		bIntent.putExtra("PrefKey", AppKeys.KEY_PHONE_TASK_OVER);
		bIntent.putExtra("PrefValue", value);
		sendBroadcast(bIntent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 屏蔽返回键
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
