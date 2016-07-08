package com.zly.test.web;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.zly.test.web.widget.overlay.OverlayView;
import com.zly.test.web.widget.overlay.util.ShowPref;

public class PhoneStateReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneStateReceiver";

    /**
     * 电话管理
     */
    private TelephonyManager telMgr = null;

    private static final Object monitor = new Object();

    @Override
    public void onReceive(Context context, Intent intent) {
        final Context ctx = context;

        Log.i(TAG, "Intent.ACTION:" + intent.getAction());

        if (intent.getAction().equals(Intent.ACTION_DIAL)) {
            Log.i(TAG, "Intent.ACTION_DIAL:" + Intent.ACTION_DIAL);
        } else if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)) {// 如果是拨打电话
            final String number = intent
                    .getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            Log.i(TAG, "call OUT:" + number);
            synchronized (monitor) {
                showSwitch(ctx, number);
            }
        } else {

            telMgr = (TelephonyManager) ctx
                    .getSystemService(Service.TELEPHONY_SERVICE);
            final String number = intent
                    .getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            switch (telMgr.getCallState()) {
                case TelephonyManager.CALL_STATE_RINGING:// 来电响铃
                    Log.i(TAG, "number:" + number);
                    // if (!getPhoneNum(context).contains(number)) {
                    // SharedPreferences phonenumSP = context
                    // .getSharedPreferences("in_phone_num",
                    // Context.MODE_PRIVATE);
                    // Editor editor = phonenumSP.edit();
                    // editor.putString(number, number);
                    // editor.commit();
                    // Log.i(TAG, "endCall:" + number);
                    // OverlayView.endCall(ctx);
                    // return;
                    // }

                    synchronized (monitor) {
                        showSwitch(ctx, number);
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:// 接听电话
                    Log.i(TAG, "CALL_STATE_OFFHOOK:");
                    synchronized (monitor) {
                        showSwitch(ctx, number);
                    }
                    break;
                case TelephonyManager.CALL_STATE_IDLE:// 挂断电话
                    synchronized (monitor) {
                        closeWindow(ctx);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void showSwitch(final Context ctx, final String number) {
        final ShowPref pref = ShowPref.getInstance(ctx);
        int showType = pref.loadInt(ShowPref.SHOW_TYPE,ShowPref.TYPE_HALF_DIALOG);

        // debug
//        showType = ShowPref.TYPE_HALF_DIALOG;

        Log.d(TAG,"showSwitch");

        synchronized (monitor) {
            switch (showType) {
                case ShowPref.TYPE_FULL_DIALOG:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showWindow(ctx, number, getPeople(ctx, number), 100);
                        }
                    }, 10);
                    break;
                case ShowPref.TYPE_HALF_DIALOG:// 非满屏Dialog
                default:// 默认显示半屏dialog
                    int value = pref.loadInt(ShowPref.TYPE_HALF_VALUE,
                            ShowPref.TYPE_HALF_DIALOG_DEFAULT);
                    final int percent = value >= 25 ? (value <= 75 ? value : 75)
                            : 25;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showWindow(ctx, number, getPeople(ctx, number), percent);
                        }
                    }, 1000);
                    break;
            }
        }
    }

    /**
     * 显示来电弹窗
     *
     * @param ctx    上下文对象
     * @param number 电话号码
     */
    private void showWindow(Context ctx, String number, String name,
                            int percentScreen) {

        Log.d(TAG,"showWindow");

        OverlayView.showInCall(ctx, number, name, percentScreen);
    }

    /**
     * 关闭来电弹窗
     *
     * @param ctx 上下文对象
     */
    private void closeWindow(final Context ctx) {
        final ShowPref pref = ShowPref.getInstance(ctx);
        int showType = pref.loadInt(ShowPref.SHOW_TYPE,ShowPref.TYPE_HALF_DIALOG);

//        // debug
//        showType = ShowPref.TYPE_HALF_DIALOG;

        Log.d(TAG,"closeWindow");

        switch (showType) {
            case ShowPref.TYPE_FULL_DIALOG:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        OverlayView.hide(ctx);
                    }
                }, 1500);
                break;
            case ShowPref.TYPE_HALF_DIALOG:
            default:// 默认会显示半屏的dialog
                OverlayView.hide(ctx);
                break;
        }

    }

    /*
     * 根据电话号码取得联系人姓名
     */
    public String getPeople(Context context, String phoneNumber) {
        try {
            String[] projection = {ContactsContract.PhoneLookup.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER};

            Log.d(TAG, "getPeople ---------");

            // 将自己添加到 msPeers 中
            Cursor cursor = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    projection, // Which columns to return.
                    ContactsContract.CommonDataKinds.Phone.NUMBER + " = '"
                            + phoneNumber + "'", // WHERE clause.
                    null, // WHERE clause value substitution
                    null); // Sort order.

            String name = "";

            if (cursor != null) {
                Log.d(TAG, "getPeople cursor.getCount() = " + cursor.getCount());
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);

                    // 取得联系人名字
                    int nameFieldColumnIndex = cursor
                            .getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
                    name = cursor.getString(nameFieldColumnIndex);
                    Log.i("Contacts", "" + name + " .... "
                            + nameFieldColumnIndex);
                }
            }

            if (TextUtils.isEmpty(name)) {
                return "陌生人";
            }

            return name;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "未知";
    }
}
