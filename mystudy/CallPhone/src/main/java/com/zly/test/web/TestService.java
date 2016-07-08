package com.zly.test.web;

import java.util.HashSet;
import java.util.Iterator;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.zly.test.web.broadcast.PrefBroadcase;
import com.zly.test.web.contact.ContactActivity;
import com.zly.test.web.widget.overlay.TuchView;
import com.zly.test.web.widget.overlay.TuchView.TuchViewClickListener;
import com.zly.test.web.widget.overlay.util.CommonUtil;

public class TestService extends Service {

	private static final String TAG = "TestService";
	private static final int WHAT_TAKE_OVER = 210;// view点击

	private HashSet<String> mDialSet = new HashSet<String>();
	private HashSet<String> mContactsSet = new HashSet<String>();

	private ActivityManager mActivityManager;

	private PackageManager mPackageManager;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");
		int pid = android.os.Process.myPid();
		Log.d(TAG, "TestService onCreate:" + pid);

		mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		mPackageManager = getApplicationContext().getPackageManager();

		TuchView.init(this, new MyTuchViewClick());

		PrefBroadcase prefBroadcase = new PrefBroadcase();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(PrefBroadcase.PREF_ACTION);
		registerReceiver(prefBroadcase, intentFilter);

		Intent intent1 = new Intent();
		intent1.setAction("android.intent.action.CALL_BUTTON");
		addActivities(this, intent1, mDialSet, false);
		Intent intent2 = new Intent();
		intent2.setAction("android.intent.action.DIAL");
		addActivities(this, intent2, mDialSet, false);

		mDialSet.add("com.android.contacts.activities.DialtactsActivity");
		mDialSet.add("com.android.contacts.DialerActivity");
		mDialSet.add("com.android.contacts.DialtactsActivity");
		mDialSet.add("com.android.contacts.DialtactsContactsEntryActivityForDialpad");
		mDialSet.add("com.sec.android.app.contacts.RecntcallEntryActivity");

		Intent intent3 = new Intent();
		intent3.setAction("android.intent.action.VIEW");
		intent3.setType("vnd.android.cursor.dir/contact");
		addActivities(this, intent3, mContactsSet, false);

		mContactsSet.add("com.meizu.mzsnssyncservice.ui.SnsTabActivity");
	}

	/**
	 * 添加需要接管的应用包名
	 * 
	 * @param paramContext
	 * @param paramIntent
	 * @param paramHashSet
	 * @param paramBoolean
	 */
	private void addActivities(Context paramContext, Intent paramIntent,
			HashSet paramHashSet, boolean paramBoolean) {
		Iterator localIterator = paramContext.getPackageManager()
				.queryIntentActivities(paramIntent, 65536).iterator();
		while (localIterator.hasNext()) {
			String str = ((ResolveInfo) localIterator.next()).activityInfo.name;
			Log.d(TAG, "test:" + str);
			if ((paramBoolean) || (str.startsWith("com.android"))
					|| (str.startsWith("com.sonyericsson"))
					|| (str.startsWith("com.yulong"))
					|| (str.startsWith("com.google"))
					|| (str.startsWith("com.htc")))
				paramHashSet.add(str);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 接管Handler
	 */
	private Handler mTakeOverHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case WHAT_TAKE_OVER:
				takeOver();
			}
		}
	};

	/**
	 * 判断是否接管
	 * 
	 * @return
	 */
	private boolean isTakeOver() {
		ComponentName componetName = CommonUtil.getTopActivity(this);

		Log.d(TAG, "componetName:" + componetName);
		if (componetName != null) {

			String className = componetName.getClassName();
			String packageName = componetName.getPackageName();

			Log.d(TAG, "getPackageName():" + getPackageName() + ",packageName:"
					+ packageName);

			// 如果为本应用则不接管
			if (getPackageName().equals(packageName)) {
				return false;
			}

			Log.d(TAG, "className:" + className);
			Log.d(TAG,
					"!(mDialSet.contains(className):"
							+ !mDialSet.contains(className));

			// 判断当前界面是否为需要接管的界面，如果是则不接管
			return !(mDialSet.contains(className) || mContactsSet
					.contains(className));
		}

		return false;
	}

	/**
	 * 接管
	 */
	private void takeOver() {
		try {
			ComponentName componetName = CommonUtil.getTopActivity(this);
			if (componetName != null) {

				String className = componetName.getClassName();
				final String packageName = componetName.getPackageName();
				Log.d(TAG, "takeOver():" + className);

				boolean bool = SharedPreferencesUtil.getBoolean(this,
						AppKeys.KEY_PHONE_TASK_OVER, false);

				// 判断是否为电话应用
				if (mDialSet.contains(className)) {
					mTakeOverHandler.removeMessages(WHAT_TAKE_OVER);
					Log.d(TAG, "mDialSet:" + "mClassName:" + className
							+ ",packageName:" + packageName);
					// 判断是否显示电话接管蒙层
					if (SharedPreferencesUtil.getBoolean(
							getApplicationContext(),
							AppKeys.KEY_PHONE_TASK_OVER_MASKING, true)) {

						Log.d(TAG, "mDialSet:KEY_PHONE_TASK_OVER_MASKING");
						// 先返回到HOME界面
						Intent home = new Intent(Intent.ACTION_MAIN);
						home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						home.addCategory(Intent.CATEGORY_HOME);
						startActivity(home);

						// 跳转只接管提示蒙层
						Intent intent = new Intent();
						intent.setClass(this, TaskOverMaskingActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);

						// 关闭蒙层提示
						SharedPreferencesUtil.putBoolean(
								getApplicationContext(),
								AppKeys.KEY_PHONE_TASK_OVER_MASKING, false);

					} else if (SharedPreferencesUtil.getBoolean(
							getApplicationContext(),
							AppKeys.KEY_PHONE_TASK_OVER, false)) {

						Log.d(TAG, "mDialSet:KEY_PHONE_TASK_OVER");

						// 判断是否开启电话接管设置
						Intent intent = new Intent();
						intent.setComponent(new ComponentName(this,
								"com.zly.test.web.TPhoneActivity"));
						intent.addCategory("android.intent.category.DEFAULT");

						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
					}

				} else if (mContactsSet.contains(className)) {// 判断是否为通讯录应用
					// 判断是否开启通讯录接管设置
					if (SharedPreferencesUtil.getBoolean(
							getApplicationContext(),
							AppKeys.KEY_CONTACT_TASK_OVER, false)) {
						mTakeOverHandler.removeMessages(WHAT_TAKE_OVER);
						Log.d(TAG, "mContactsSet");
						// 跳转通讯录
						Intent intent = new Intent();
						intent.setClass(this, ContactActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
					}

				}

			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
	}

	/**
	 * 屏幕点击事件
	 */
	private class MyTuchViewClick implements TuchViewClickListener {

		@Override
		public void tuchClick() {
			boolean isTakeOver = isTakeOver();
			Log.d(TAG, "isTakeOver：" + isTakeOver);
			// 判断是否接管&&是否存在接管
			if (isTakeOver && !mTakeOverHandler.hasMessages(WHAT_TAKE_OVER)) {
				for (int i = 0;; i += 20) {
					if (i > 200) {
						mTakeOverHandler.sendEmptyMessageDelayed(
								WHAT_TAKE_OVER, 400);
						break;
					}
					mTakeOverHandler.sendEmptyMessageDelayed(WHAT_TAKE_OVER, i);
				}
			}
		}
	}

	// /**
	// * 获取包信息
	// *
	// * @param packageName
	// * @return
	// */
	// public PackageInfo getPackageInfo(String packageName) {
	// PackageInfo packageInfo = null;
	// try {
	// packageInfo = mPackageManager.getPackageInfo(packageName,
	// PackageManager.GET_ACTIVITIES);
	// } catch (NameNotFoundException e) {
	// // TODO: 异常处理
	// }
	// return packageInfo;
	// }
	//
	// public Intent getIntent(PackageInfo pkginfo) {
	// Intent intent = new Intent();
	// try {
	// intent = mPackageManager
	// .getLaunchIntentForPackage(pkginfo.packageName);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return intent;
	// }
}
