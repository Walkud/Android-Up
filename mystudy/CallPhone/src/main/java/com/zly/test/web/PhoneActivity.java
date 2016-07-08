package com.zly.test.web;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.zly.test.web.broadcast.PrefBroadcase;

public class PhoneActivity extends Activity {

	public final static String MY_PHONE_STATE = TelephonyManager.ACTION_PHONE_STATE_CHANGED;

	private static final String TAG = "PhoneActivity";

	private Button button;

	private ToggleButton phoneToggleBtn, contactToggleBtn;

	private EditText editText;

	private ActivityManager mActivityManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone);
		int pid = android.os.Process.myPid();
		Log.d(TAG, "onCreate:" + pid);

		mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		boolean bool = SharedPreferencesUtil.getBoolean(this,
				AppKeys.KEY_PHONE_TASK_OVER, false);

		Log.d(TAG, "bool:" + bool);

		Intent intent = new Intent(this, TestService.class);
		startService(intent);

		phoneToggleBtn = (ToggleButton) findViewById(R.id.phone_toggle_btn);
		phoneToggleBtn.setChecked(SharedPreferencesUtil.getBoolean(this,
				AppKeys.KEY_PHONE_TASK_OVER, false));

		phoneToggleBtn
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						SharedPreferencesUtil.putBoolean(
								getApplicationContext(),
								AppKeys.KEY_PHONE_TASK_OVER, arg1);
						prefBroadcast(AppKeys.KEY_PHONE_TASK_OVER, arg1);
					}
				});

		contactToggleBtn = (ToggleButton) findViewById(R.id.contact_toggle_btn);
		contactToggleBtn.setChecked(SharedPreferencesUtil.getBoolean(this,
				AppKeys.KEY_CONTACT_TASK_OVER, false));

		contactToggleBtn
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton arg0,
							boolean arg1) {
						SharedPreferencesUtil.putBoolean(
								getApplicationContext(),
								AppKeys.KEY_CONTACT_TASK_OVER, arg1);
						prefBroadcast(AppKeys.KEY_CONTACT_TASK_OVER, arg1);
					}
				});
		editText = (EditText) findViewById(R.id.phone_number_edit);
		button = (Button) findViewById(R.id.out_call_btn);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				try {
					String phoneNumber = editText.getText().toString();
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + phoneNumber));
					startActivity(intent);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "出错", Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		int pid = android.os.Process.myPid();
		Log.d(TAG, "onNewIntent:" + pid);
	}

	@Override
	protected void onResume() {
		super.onResume();

		String packageName = getIntent().getStringExtra("PackageName");
		if (packageName != null) {
			mActivityManager.killBackgroundProcesses(packageName);
		}

	}

	/**
	 * 发送设置Key Value广播
	 * 
	 * @param key
	 * @param value
	 */
	private void prefBroadcast(String key, boolean value) {
		Intent bIntent = new Intent();
		bIntent.setAction(PrefBroadcase.PREF_ACTION);
		bIntent.putExtra("PrefKey", key);
		bIntent.putExtra("PrefValue", value);
		sendBroadcast(bIntent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 注意
			intent.addCategory(Intent.CATEGORY_HOME);
			this.startActivity(intent);

			finish();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
