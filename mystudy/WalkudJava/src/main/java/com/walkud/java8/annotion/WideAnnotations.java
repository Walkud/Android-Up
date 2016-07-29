package com.walkud.java8.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 拓宽注解的应用场景
 * Java 8拓宽了注解的应用场景。现在，注解几乎可以使用在任何元素上：
 * 局部变量、接口类型、超类和接口实现类，甚至可以用在函数的异常定义上
 * Created by Walkud on 16/7/29.
 */
public class WideAnnotations {


    /**
     * ElementType.TYPE_USER和ElementType.TYPE_PARAMETER是Java 8新增的两个注解
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
    public @interface NonEmpty {
    }

    public static class Holder<@NonEmpty T> extends @NonEmpty Object {

        public T method(@NonEmpty T t) throws @NonEmpty Exception {
            return t;
        }
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        final Holder<String> holder = new @NonEmpty Holder<>();
        @NonEmpty Collection<@NonEmpty String> strings = new ArrayList<>();

        try {
            String str = holder.method("Java");
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
