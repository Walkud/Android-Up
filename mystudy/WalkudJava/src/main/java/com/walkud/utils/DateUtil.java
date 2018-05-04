package com.walkud.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {

    public static final Pattern p = Pattern.compile("(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

    public static boolean isDate(String str) {
        Matcher matcher = p.matcher(str);
        return matcher.matches();
    }


}
