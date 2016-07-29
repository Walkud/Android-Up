package com.walkud.java8.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 重复注解示例
 * 自从Java 5中引入注解以来，这个特性开始变得非常流行，并在各个框架和项目中被广泛使用。不过，注解有一个很大的限制是：在同一个地方不能多次使用同一个注解。Java 8打破了这个限制，引入了重复注解的概念，允许在同一个地方多次使用同一个注解。
 * <p/>
 * 在Java 8中使用@Repeatable注解定义重复注解，实际上，这并不是语言层面的改进，而是编译器做的一个trick，底层的技术仍然相同
 * <p/>
 * 文／杜琪（简书作者）
 * 原文链接：http://www.jianshu.com/p/5b800057f2d8
 * 著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。
 * Created by Walkud on 16/7/29.
 */
public class RepeatingAnnotations {


    /**
     * 声明一个注解
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Repeatable(Fillters.class)
    public @interface Fillter {
        String value();
    }

    /**
     * 声明一个注解容器
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Fillters {
        Fillter[] value();
    }


    /**
     * 使用重复注解
     */
    @Fillter("Fillter1")
    @Fillter("Fillter2")
    public interface Filterable {

    }


    public static void main(String[] args) {

        for (Fillter fillter : Filterable.class.getAnnotationsByType(Fillter.class)) {
            System.out.println(fillter.value());
        }
    }

}
