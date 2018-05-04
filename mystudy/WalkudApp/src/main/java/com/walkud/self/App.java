package com.walkud.self;

import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

/**
 * Created by jan on 16/3/30.
 */
public class App extends MultiDexApplication {

    private static App app;

    private OkHttpClient mOkHttpClient;

    public static App getInstance() {
        return app;
    }

    /**
     * 让应用支持多DEX文件。在MultiDexApplication JavaDoc中描述了三种可选方法：
     * 1.在AndroidManifest.xml的application中声明android.support.multidex.MultiDexApplication；
     * 2.如果你已经有自己的Application类，让其继承MultiDexApplication；
     * 3.如果你的Application类已经继承自其它类，你不想/能修改它，那么可以重写attachBaseContext()方法：
     */
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this); //分包处理，解决64k问题
//    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        initStetho();
    }

    /**
     * 初始化Stetho调试工具
     */
    private void initStetho() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        mOkHttpClient = builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
