package com.walkud.utils.gson;

import com.alibaba.fastjson.parser.JSONLexer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.walkud.utils.DateUtil;

import java.lang.reflect.Type;
import java.util.Calendar;

/**
 * Created by Walkud on 17/1/16.
 */

public class LongDefault0Adapter implements JsonSerializer<Long>, JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            if (json.getAsString().equals("") || json.getAsString().equals("null")) {//定义为long类型,如果后台返回""或者null,则返回0
                return 0l;
            }
        } catch (Exception ignore) {
        }

        JSONLexer dateParser = null;

        try {

            String value = json.getAsString();

            dateParser = new JSONLexer(value);
            if (dateParser.scanISO8601DateIfMatch(false)) {
                Calendar calendar = dateParser.calendar;
                return calendar.getTimeInMillis();
            }

            return json.getAsLong();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        } finally {
            if (dateParser != null) {
                dateParser.close();
            }
        }
    }

    @Override
    public JsonElement serialize(Long src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}
