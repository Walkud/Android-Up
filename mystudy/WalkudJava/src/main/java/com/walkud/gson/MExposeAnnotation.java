package com.walkud.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.walkud.gson.annotations.MyExclus;

import java.lang.annotation.Annotation;

/**
 * Created by jan on 16/4/6.
 */
public class MExposeAnnotation {

    public static void main(String[] args) {

        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new MyExclusionStrategy())
                .create();

        MExposeUser eu = new MExposeUser();
        eu.name = "Gson";
        eu.age = 22;
        eu.sex = "男";
        eu.height = 175;
        eu.weight = 66.5f;
        eu.isLogin = true;

        String json = gson.toJson(eu);


        System.out.println(json);

        json = "{\"expStatic\":\"5\",\"name\":\"Gson\",\"age\":22,\"sex\":\"男\",\"height\":175,\"weight\":66.5,\"isLogin\":true}";

        MExposeUser deserEu = gson.fromJson(json, MExposeUser.class);
        System.out.println(deserEu.toString());

    }

    /**
     * 自定义排除策略
     */
    static class MyExclusionStrategy implements ExclusionStrategy {

        /**
         * 需要跳过的字段
         *
         * @param f
         * @return
         */
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            //如果字段带有MyExclus注解，则跳过
            return f.getAnnotation(MyExclus.class) != null;
        }

        /**
         * 需要跳过的类
         *
         * @param clazz
         * @return
         */
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return Integer.class == clazz;
        }
    }


    /**
     * 实体
     */
    static class MExposeUser {

        public static int expStatic;//Gson默认情况会排除带有static关键字字段

        public String name;//姓名
        public int age;//年龄
        public String sex;//性别
        public Integer height;//身高
        public float weight;//体重
        @MyExclus
        public boolean isLogin;//是否登录

        @Override
        public String toString() {
            return "{\"expStatic\":\"" + expStatic
                    + "\",\"name\":\"" + name
                    + "\",\"age\":\"" + age
                    + "\",\"sex\":\"" + sex
                    + "\",\"height\":\"" + height
                    + "\",\"weight\":\"" + weight
                    + "\",\"isLogin\":\"" + isLogin + "\"}";
        }
    }
}
