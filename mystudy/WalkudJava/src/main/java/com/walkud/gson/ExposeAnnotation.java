package com.walkud.gson;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * Gson @Expose排除策略
 * Created by jan on 16/4/6.
 */
public class ExposeAnnotation {

    public static void main(String[] args) {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()//排除没有Expose注解字段
                .create();

        ExposeUser eu = new ExposeUser();
        eu.name = "Gson";
        eu.age = 22;
        eu.sex = "男";
        eu.height = 175;
        eu.weight = 66.5f;
        eu.isLogin = true;

        String json = gson.toJson(eu);

        System.out.println(json);

        json = "{\"expStatic\":\"5\",\"name\":\"Gson\",\"age\":22,\"sex\":\"男\",\"height\":175,\"weight\":66.5,\"isLogin\":true}";

        ExposeUser deserEu = gson.fromJson(json, ExposeUser.class);
        System.out.println(deserEu.toString());

    }


    /**
     * 实体
     */
    static class ExposeUser {

        public static int expStatic;//Gson默认情况会排除带有static关键字字段

        @Expose
        public String name;//姓名
        @Expose
        public int age;//年龄
        @Expose
        public String sex;//性别
        @Expose
        public int height;//身高
        @Expose
        public float weight;//体重
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
