package com.walkud.java8.optional;

import java.util.Optional;

/**
 * Java应用中最常见的bug就是空值异常。
 * 在Java 8之前，Google Guava引入了Optionals类来解决NullPointerException，
 * 从而避免源码被各种null检查污染，以便开发者写出更加整洁的代码。Java 8也将Optional加入了官方库。
 * <p/>
 * Optional仅仅是一个容器：存放T类型的值或者null。它提供了一些有用的接口来避免显式的null检查
 * <p/>
 * 文／杜琪（简书作者）
 * 原文链接：http://www.jianshu.com/p/5b800057f2d8
 * 著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。
 * Created by Walkud on 16/8/1.
 */
public class OptionalTest {

    public static void main(String[] args) {

        nullable(null);

        System.out.println("\n-----------------------------------\n");
        
        nullable("ZhuZhu");
    }

    private static void nullable(String name) {
        Optional<String> fullName = Optional.ofNullable(name);
        System.out.println("Full Name is set? " + fullName.isPresent());

        ////此行在fullName的Value为null时，会抛出异常,因为JDK中对value做了null判断
//        System.out.println("Full Name " + fullName.get());
        System.out.println("Full Name " + fullName.orElse("[none]"));
        System.out.println("Full Name " + fullName.orElseGet(() -> "[none]"));
        try {
            System.out.println("Full Name " + fullName.orElseThrow(() -> new Exception("FullName is null")));
        } catch (Exception e) {
            System.out.println("Exception " + e.getMessage());
        }

        //map()方法可以将现有的Opetional实例的值转换成新的值
        System.out.println(fullName.map(s -> "Hey " + s + "!"));
        System.out.println(fullName.map(s -> "Hey " + s + "!").orElse("Hey Java!"));
    }
}
