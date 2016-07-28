package com.walkud.java8.lambda;

import java.util.Arrays;

/**
 * Lambda 表达式学习
 * Created by Walkud on 16/7/27.
 *
 * @link {http://www.jianshu.com/p/5b800057f2d8}
 */

public class Lambda {

    public static void main(String[] args) {

        simpleExample();
        variableArea();
    }

    /**
     * 简单示例
     */
    public static void simpleExample() {
        //最简单的表达式
        Arrays.asList("a", "b", "c").forEach(e -> System.out.println(e));

        //表达式中显式的给出类型，这种方式好处是便于日后理解类型，但会额外增加代码量，注意括号位置
        Arrays.asList("a", "b", "c").forEach((String e) -> System.out.println(e));

        //表达式中复杂语句块（2行以上代码）可以使用花括号
        Arrays.asList("a", "b", "c").forEach(e -> {
            System.out.print("Hello ");
            System.out.println(e);
        });

    }

    /**
     * 局部变量在表达式中引用
     */
    public static void variableArea() {

        //局部变量引用，会将局部变量隐式的转换为final
        String separator = ",";
        Arrays.asList("a", "b", "c").forEach(e -> System.out.print(e + separator));

    }

}
