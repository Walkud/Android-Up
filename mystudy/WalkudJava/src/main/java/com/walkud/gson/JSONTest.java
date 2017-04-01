package com.walkud.gson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Walkud on 17/4/1.
 */

public class JSONTest {

    public static void main(String[] args) {
        String str = "{\"success\":true,\"ecode\":0,\"msg\":\"success\",\"data\":{\"finish\":false,\"ip\":\"192.168.3.37\",\"name\":\"我的盒子\",\"code\":\"3daf8164-ea72-3436-8dde-3f0dbbac94d6\"}}";

        Gson gson = new Gson();
        UdtResult gosnUdtResult = gson.fromJson(str, new TypeToken<UdtResult<BoxInfo>>() {
        }.getType());
        System.out.println("Gson TypeToken:" + gosnUdtResult.toString());

        UdtResult<BoxInfo> fastUdtResult = JSON.parseObject(str
                , new TypeReference<UdtResult<BoxInfo>>() {
                }.getType());

        System.out.println("FastJson TypeReference:" + fastUdtResult.toString());

    }
}
