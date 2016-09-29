package womhelper.frameworkstudy.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreferences工具类
 * Created by laucherish on 16/3/30.
 */
public class PrefUtil {

    private static final String PRE_NAME = "FrameWorkStudy";

    private static SharedPreferences getSharedPreferences() {
        return AppContextUtil.getInstance()
                .getSharedPreferences(PRE_NAME, Context.MODE_PRIVATE);
    }

    public static void putString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    public static String getString(String key,
                                   String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }


    public static void putBoolean(String key, Boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }

    public static Boolean getBoolean(String key,
                                     Boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public static void putLong(String key, long value) {
        getSharedPreferences().edit().putLong(key, value).apply();
    }

    public static long getLong(String key, long defaultValue) {
        return getSharedPreferences().getLong(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public static void putInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).apply();
    }
}
