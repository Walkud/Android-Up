package com.walkud.self;

import android.app.Application;

import com.walkud.self.utils.RealmUtil;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jan on 16/3/30.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initRealm();
    }


    /**
     * 初始化数据库
     */
    private void initRealm() {
        RealmConfiguration config = RealmUtil.getConfig(this);
        Realm.setDefaultConfiguration(config);
    }
}
