package cn.studyjams.s1.sj10.zhuliya.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.studyjams.s1.sj10.zhuliya.common.annotation.OExcul;
import io.realm.RealmObject;

/**
 * Created by jan on 16/4/26.
 */
public class GsonUtil {

    private static Gson gson;

    private GsonUtil() {
    }

    /**
     * 构建Gson
     *
     * @return
     */
    public static Gson buildGson() {
        if (gson == null) {
            synchronized (GsonUtil.class) {
                if (gson == null) {
                    //添加策略
                    return new GsonBuilder()
                            .addSerializationExclusionStrategy(new ExclusionStrategy() {
                                @Override
                                public boolean shouldSkipField(FieldAttributes f) {
                                    //排除对应字段
                                    return f.getDeclaringClass().equals(RealmObject.class) || f.getAnnotation(OExcul.class) != null;
                                }

                                @Override
                                public boolean shouldSkipClass(Class<?> clazz) {
                                    return false;
                                }
                            })
                            .serializeNulls()
                            .create();
                }
            }
        }
        return gson;
    }
}
