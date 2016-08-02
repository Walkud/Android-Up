package com.walkud.java8.base64;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64编码的支持已经被加入到Java 8官方库中
 * Created by Walkud on 16/8/2.
 */
public class Base64s {

    public static void main(String[] args) {
        String text = "Base64 finally in Java 8!";

        String encoded = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
        System.out.println(encoded);

        String decoded = new String(Base64.getDecoder().decode(encoded));
        System.out.println(decoded);

    }
}
