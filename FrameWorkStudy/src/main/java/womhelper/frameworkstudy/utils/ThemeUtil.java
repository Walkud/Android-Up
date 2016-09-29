package womhelper.frameworkstudy.utils;

import womhelper.frameworkstudy.Const;

/**
 * 主题工具类
 * Created by Walkud on 16/9/26.
 */
public class ThemeUtil {

    /**
     * 夜间主题Key
     */
    private static final String PRE_NIGHT = "theme_night";

    /**
     * 设置为夜间模式
     */
    public static void setNight() {
        PrefUtil.putBoolean(PRE_NIGHT, true);
    }

    /**
     * 设置为白天模式
     */
    public static void setDay() {
        PrefUtil.putBoolean(PRE_NIGHT, false);
    }

    /**
     * 改变主题
     */
    public static void changeDayNight() {
        boolean change = !PrefUtil.getBoolean(PRE_NIGHT, false);
        PrefUtil.putBoolean(PRE_NIGHT, change);
    }

    /**
     * 是否为夜间
     *
     * @return
     */
    public static boolean isNight() {
        return PrefUtil.getBoolean(PRE_NIGHT, false);
    }

    /**
     * 获取缓存的主题Id值
     *
     * @return
     */
    public static int getThemeRes() {
        if (!isNight()) {
            return Const.Theme.RESOURCES_DAYTHEME;
        } else {
            return Const.Theme.RESOURCES_NIGHTTHEME;
        }
    }

}
