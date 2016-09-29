package womhelper.frameworkstudy.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 手机信息 <br>
 * 当前程序是否后台运行 ,当前手机是否处于睡眠 ,当前网络是否已连接 <br>
 * 当前网络是否wifi状态 ,安装apk ,初始化view的高度 ,初始化view的高度后不可见<br>
 * 判断是否为手机 获取屏幕宽度 获取屏幕高度 获取设备的IMEI <br>
 * 获取设备的mac地址 获取当前应用的版本号 ,检测当前系统声音是否为正常模式<br>
 * 收集设备信息并以Properties返回 收集设备信息并以String返回
 */
public class DeviceUtil {

    private static final String TAG = DeviceUtil.class.getSimpleName();
    /**
     * 1：WIFI网络
     */
    public static final int NETTYPE_WIFI = 0x01;
    /**
     * 2：WAP网络
     */
    public static final int NETTYPE_CMWAP = 0x02;
    /**
     * 3：NET网络
     */
    public static final int NETTYPE_CMNET = 0x03;

    /**
     * 获取设备的系统版本
     *
     * @return
     */
    public static String getSysVersionRelease() {
        return VERSION.RELEASE;
    }

    /**
     * 手机系统Android版本号
     */
    public static String getSysVersion() {
        return String.valueOf(VERSION.SDK_INT);
    }

    /**
     * 手机机型
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * 手机IMEI号
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * 获取运营商编号（mnc）
     */
    public static String getMNC(Context context) {
        TelephonyManager tel = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tel.getNetworkOperator();
        operator = operator == null ? "" : operator;
        return operator;
    }

    /**
     * 获取屏幕参数
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 获取运营商接口编号
     *
     * @param context
     * @return
     */
    public static int getOperatorCode(Context context) {
        int result = 0;
        String operator = getMNC(context);
        if (operator.equals("46000") || operator.equals("46002")) {
            result = 3;
        } else if (operator.equals("46001")) {
            result = 1;
        } else if (operator.equals("46003")) {
            result = 2;
        } else {
            result = -1;
        }
        return result;
    }

    /**
     * 获取手机端IP地址
     *
     * @return
     */
    public static String getIpAddress() {
        String ip = "-1";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                String displayName = intf.getDisplayName();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        ip = inetAddress.getHostAddress().toString();
                        break;
                    }
                }
                if (!ip.equals("-1") && displayName.equals("ppp0")) {
                    break;
                }
            }
        } catch (SocketException ex) {
            Log.e("WifiPreference IpAddress", ex.toString());
        }
        return ip;
    }

    /**
     * 检测当前系统声音是否为正常模式
     *
     * @return
     */
    public static boolean isAudioNormal(Context context) {
        AudioManager mAudioManager = (AudioManager) context
                .getSystemService(Context.AUDIO_SERVICE);
        return mAudioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
    }

    /**
     * 判断是否网络类型
     *
     * @param context
     * @return
     */
    public static boolean isWIFINetworkType(Context context) {

        if (NETTYPE_WIFI == getNetworkType(context)) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
     */
    public static int getNetworkType(Context context) {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    /**
     * 是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * 判断手机是否处理睡眠
     *
     * @param context
     * @return
     */
    public static boolean isSleeping(Context context) {
        KeyguardManager kgMgr = (KeyguardManager) context
                .getSystemService(Context.KEYGUARD_SERVICE);
        boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
        Log.d(TAG, isSleeping ? "手机睡眠中.." : "手机未睡眠...");
        return isSleeping;
    }

    /**
     * 判断当前是否是wifi状态
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiNetworkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 安装apk
     *
     * @param context
     * @param file
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("application/vnd.android.package-archive");
        intent.setData(Uri.fromFile(file));
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    /**
     * 判断是否为手机
     *
     * @param context
     * @return
     * @author wangjie
     */
    public static boolean isPhone(Context context) {
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        int type = telephony.getPhoneType();
        if (type == TelephonyManager.PHONE_TYPE_NONE) {
            Log.i(TAG, "Current device is Tablet!");
            return false;
        } else {
            Log.i(TAG, "Current device is phone!");
            return true;
        }
    }

    /**
     * 获得设备的屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getDeviceWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getWidth();
    }

    /**
     * 获得设备的屏幕高度
     *
     * @param context
     * @return
     */
    public static int getDeviceHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        return manager.getDefaultDisplay().getHeight();
    }

    /**
     * 获取设备id（IMEI）
     *
     * @param context
     * @return
     * @author wangjie
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static String getDeviceIMEI(Context context) {
        String deviceId;
        if (isPhone(context)) {
            TelephonyManager telephony = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = telephony.getDeviceId();
        } else {
            deviceId = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

        }
        Log.d(TAG, "当前设备IMEI码: " + deviceId);
        return deviceId;
    }

    /**
     * 获取设备mac地址
     *
     * @param context
     * @return
     * @author wangjie
     */
    public static String getMacAddress(Context context) {
        String macAddress;
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        macAddress = info.getMacAddress();
        Log.d(TAG, "当前mac地址: " + (null == macAddress ? "null" : macAddress));
        if (null == macAddress) {
            return "";
        }
        macAddress = macAddress.replace(":", "");
        return macAddress;
    }

    /**
     * 获取当前应用程序的版本号
     *
     * @param context
     * @return
     * @author wangjie
     */
    public static String getAppVersion(Context context) {
        String version = "0";
        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "该应用的版本号: " + version);
        return version;
    }

    /**
     * 是否有SDCard
     *
     * @return
     */
    public static boolean haveSDCard() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * 隐藏软键盘
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void hideSoftInput(Context context) {
        View view = ((Activity) context).getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void hideSoftInput(Context context, EditText edit) {
        edit.clearFocus();
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
    }

    /**
     * 获取状态栏高度
     *
     * @param activity
     * @return
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    /**
     * 获取状态栏高度＋标题栏高度
     *
     * @param activity
     * @return
     */
    public static int getTopBarHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT)
                .getTop();
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static String getVersion(Context context) {
        String versionName = null;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            versionName = info.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return versionName;
    }

    /**
     * 获取版本Code
     *
     * @return
     */
    public static int getVersionCode(Context context) {
        int versionCode = -1;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            versionCode = info.versionCode;
        } catch (NameNotFoundException e) {
            Log.e(TAG, e.getLocalizedMessage(), e);
        }
        return versionCode;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     * @return
     */
    public static Map<String, String> collectDeviceInfo(Context ctx) {
        Map<String, String> infos = new HashMap<>();
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                String versionRelease = DeviceUtil.getSysVersionRelease();
                String versionSdkInt = DeviceUtil.getSysVersion();
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
                infos.put("versionRelease", versionRelease);
                infos.put("versionSdkInt", versionSdkInt);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
        return infos;
    }

    /**
     * 振动器
     */
    public static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(new long[]{100, 100}, -1);
        } else {
            Log.i(TAG, "Device not have vibrator");
        }
    }

    /**
     * 判断是否有Sim卡
     *
     * @param context
     * @return
     */
    public static boolean haveSim(Context context) {
        TelephonyManager manager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        if (manager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            return true;
        }

        return false;
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
