package com.walkud.gson;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Walkud on 17/7/27.
 */

public class GsonMain {

    public static void main(String[] args) {
//        String data = "{\"success\":true,\"msg\":\"成功\",\"ecode\":0,\"data\":[]}";
        String data = "{\"success\":true,\"msg\":\"成功\",\"ecode\":0,\"data\":{\"dt\":\"\",\"fav\":0,\"fc\":\"f_1943\",\"fl\":0,\"fn\":\"61d9ce07cd115db809479f3614e87a3.dat\",\"fs\":0,\"ft\":0,\"tr\":0}}";
        UdtResult<JsonObject> udtResult = new Gson().fromJson(data, new TypeToken<UdtResult<JsonObject>>() {
        }.getType());

        System.out.println(udtResult.getData().toString());
    }
}
