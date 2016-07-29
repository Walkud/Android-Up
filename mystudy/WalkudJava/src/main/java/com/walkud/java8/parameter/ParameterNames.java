package com.walkud.java8.parameter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 参数名称
 * 为了在运行时获得Java程序中方法的参数名称，老一辈的Java程序员必须使用不同方法，
 * 例如Paranamer liberary。Java 8终于将这个特性规范化，在语言层面（使用反射API和Parameter.getName()方法）
 * 和字节码层面（使用新的javac编译器以及-parameters参数）提供支持。
 * <p/>
 * 文／杜琪（简书作者）
 * 原文链接：http://www.jianshu.com/p/5b800057f2d8
 * 著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。
 * <p/>
 * Created by Walkud on 16/7/29.
 */
public class ParameterNames {

    /**
     * 在Java 8中这个特性是默认关闭的，因此如果不带-parameters参数编译上述代码并运行
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Method method = ParameterNames.class.getMethod("main", String[].class);
            for (Parameter parameter : method.getParameters()) {
                System.out.println("Parameter:" + parameter);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
