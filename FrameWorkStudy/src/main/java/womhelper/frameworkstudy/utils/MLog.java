package womhelper.frameworkstudy.utils;

import com.orhanobut.logger.Logger;
import com.orhanobut.logger.Printer;

/**
 * 封装日志
 * Created by Walkud on 16/9/30.
 */

public class MLog {

    /**
     * 设置日志Tag
     *
     * @param tag
     * @return
     */
    public static Printer set(String tag) {
        return Logger.t(tag);
    }

    /**
     * 设置日志栈输出的方法数
     *
     * @param methodCount
     * @return
     */
    public static Printer set(int methodCount) {
        return Logger.t(null, methodCount);
    }

    /**
     * 设置Tag And 日志栈输出的方法数
     *
     * @param tag
     * @param methodCount
     * @return
     */
    public static Printer set(String tag, int methodCount) {
        return Logger.t(tag, methodCount);
    }

    /**
     * Debug
     *
     * @param msg
     */
    public static void d(String msg) {
        Logger.d(msg);
    }

    /**
     * Debug
     *
     * @param message
     * @param args
     */
    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    /**
     * Info
     *
     * @param message
     * @param args
     */
    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    /**
     * Verbose
     *
     * @param message
     * @param args
     */
    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

    /**
     * Warn
     *
     * @param message
     * @param args
     */
    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    /**
     * Wtf
     *
     * @param message
     * @param args
     */
    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

    /**
     * Error
     *
     * @param message
     * @param args
     */
    public static void e(String message, Object... args) {
        Logger.e(null, message, args);
    }

    /**
     * Error
     *
     * @param throwable
     * @param message
     * @param args
     */
    public static void e(Throwable throwable, String message, Object... args) {
        Logger.e(throwable, message, args);
    }

    /**
     * 格式化json串
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        Logger.json(json);
    }

    /**
     * 格式化Xml
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        Logger.xml(xml);
    }
}

