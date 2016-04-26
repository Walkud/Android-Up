package cn.studyjams.s1.sj10.zhuliya;

import android.app.Application;

import cn.studyjams.s1.sj10.zhuliya.utils.RealmUtil;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jan on 16/4/25.
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
