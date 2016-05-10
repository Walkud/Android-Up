package com.walkud.self;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.walkud.self.utils.RealmUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;

/**
 * Created by jan on 16/3/30.
 */
public class App extends Application {

    private static App app;

    private OkHttpClient mOkHttpClient;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        initRealm();

        initStetho();
    }


    /**
     * 初始化数据库
     */
    private void initRealm() {
        RealmConfiguration config = RealmUtil.getConfig(this);
        Log.d("App", "RealmConfiguration info : " + config.toString());
        Realm.setDefaultConfiguration(config);
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
