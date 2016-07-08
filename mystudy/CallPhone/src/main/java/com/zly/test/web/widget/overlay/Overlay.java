package com.zly.test.web.widget.overlay;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

/**
 * 来电和去点蒙层基础类
 */
public abstract class Overlay {

    private static final String TAG = "Overlay";
    protected static final Object monitor = new Object();
    public static OverlayLayoutView mOverlay;

    protected static OverlayLayoutView init(Context context,
                                            WindowManager.LayoutParams params) {
        WindowManager wm = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        if (mOverlay == null) {
            final OverlayLayoutView overlay = new OverlayLayoutView(context);
            mOverlay = overlay;

            wm.addView(overlay, params);
        }
        return mOverlay;
    }

    public static void show(Context context, String number) {
        //
    }

    public static void hide(Context context) {
        //
    }

    /**
     * 挂断电话
     */
    public static synchronized void endCall(Context ctx) {
        TelephonyManager mTelMgr = (TelephonyManager) ctx
                .getSystemService(Service.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        try {
            Method getITelephonyMethod = c.getDeclaredMethod("getITelephony",
                    (Class[]) null);
            getITelephonyMethod.setAccessible(true);
            ITelephony iTelephony = null;
            Log.i(TAG, "End call.");
            iTelephony = (ITelephony) getITelephonyMethod.invoke(mTelMgr,
                    (Object[]) null);
            iTelephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "Fail to answer ring call.");
        }
    }

    /**
     * 接听电话
     */
    public static synchronized void answerRingingCall(Context ctx) {
        // 据说该方法只能用于Android2.3及2.3以上的版本上，但本人在2.2上测试可以使用
        try {
            // 插耳机
            Intent localIntent1 = new Intent(Intent.ACTION_HEADSET_PLUG);
            localIntent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            localIntent1.putExtra("state", 1);
            localIntent1.putExtra("microphone", 1);
            localIntent1.putExtra("name", "Headset");
            ctx.sendOrderedBroadcast(localIntent1,
                    "android.permission.CALL_PRIVILEGED");

            // 按下耳机按钮
            Intent localIntent2 = new Intent(Intent.ACTION_MEDIA_BUTTON);
            KeyEvent localKeyEvent1 = new KeyEvent(KeyEvent.ACTION_DOWN,
                    KeyEvent.KEYCODE_HEADSETHOOK);
            localIntent2.putExtra("android.intent.extra.KEY_EVENT",
                    localKeyEvent1);
            ctx.sendOrderedBroadcast(localIntent2,
                    "android.permission.CALL_PRIVILEGED");

            // 放开耳机按钮
            Intent localIntent3 = new Intent(Intent.ACTION_MEDIA_BUTTON);
            KeyEvent localKeyEvent2 = new KeyEvent(KeyEvent.ACTION_UP,
                    KeyEvent.KEYCODE_HEADSETHOOK);
            localIntent3.putExtra("android.intent.extra.KEY_EVENT",
                    localKeyEvent2);
            ctx.sendOrderedBroadcast(localIntent3,
                    "android.permission.CALL_PRIVILEGED");

            // 拔出耳机
            Intent localIntent4 = new Intent(Intent.ACTION_HEADSET_PLUG);
            localIntent4.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            localIntent4.putExtra("state", 0);
            localIntent4.putExtra("microphone", 1);
            localIntent4.putExtra("name", "Headset");
            ctx.sendOrderedBroadcast(localIntent4,
                    "android.permission.CALL_PRIVILEGED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 接听电话
     */
    public static synchronized void answerCall(Context ctx) {
        TelephonyManager mTelMgr = (TelephonyManager) ctx
                .getSystemService(Service.TELEPHONY_SERVICE);
        Class<TelephonyManager> c = TelephonyManager.class;
        try {
            Method getITelephonyMethod = c.getDeclaredMethod("getITelephony",
                    (Class[]) null);
            getITelephonyMethod.setAccessible(true);
            ITelephony iTelephony = null;
            iTelephony = (ITelephony) getITelephonyMethod.invoke(mTelMgr,
                    (Object[]) null);
            iTelephony.answerRingingCall();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "Fail to answer ring call.");
        }
    }
}
