package cn.studyjams.s1.sj10.zhuliya.utils;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

/**
 * Created by jan on 16/3/30.
 */
public class RealmUtil {

    /**
     * 获取自定义配置
     *
     * @param context
     * @return
     */
    public static RealmConfiguration getConfig(Context context) {
//        byte[] key = new byte[64];
//        new SecureRandom().nextBytes(key);//设置加密Key
        return new RealmConfiguration.Builder(context)
                .name("sj10.realm")
//                .encryptionKey(key)
//                .setModules(new MySchemaModule())//未知
//                .migration(new MyMigration())//迁移
                .build();
    }

}
