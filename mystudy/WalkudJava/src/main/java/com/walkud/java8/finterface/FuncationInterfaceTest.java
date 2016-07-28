package com.walkud.java8.finterface;

import java.util.function.Supplier;

/**
 * 函数接口
 * Lambda的设计者们为了让现有的功能与Lambda表达式良好兼容，考虑了很多方法，于是产生了函数接口这个概念。
 * 函数接口指的是只有一个函数的接口，这样的接口可以隐式转换为Lambda表达式。
 * java.lang.Runnable和java.util.concurrent.Callable是函数式接口的最佳例子。
 * 在实践中，函数式接口非常脆弱：只要某个开发者在该接口中添加一个函数，则该接口就不再是函数式接口进而导致编译失败。
 * 为了克服这种代码层面的脆弱性，并显式说明某个接口是函数式接口，
 * Java 8 提供了一个特殊的注解@FunctionalInterface（Java 库中的所有相关接口都已经带有这个注解了）
 * 文／杜琪（简书作者）
 * 原文链接：http://www.jianshu.com/p/5b800057f2d8
 * 著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。
 * Created by Walkud on 16/7/28.
 */
public class FuncationInterfaceTest {


    /**
     * 最简单的函数接口
     */
    @FunctionalInterface
    public interface Functional {

        void method();

        default void defaultMethod() {
            System.out.println("Functional defaultMethod");
        }
    }

    /**
     * 实现接口方法，但不复写默认方法
     */
    public static class FunctionalImpl implements Functional {

        @Override
        public void method() {
            System.out.println("FuncationImpl method");
        }
    }

    /**
     * 实现接口方法，并复写默认方法
     */
    public static class OverFunctionalImpl implements Functional {

        @Override
        public void method() {
            System.out.println("OverFuncationImpl method");
        }

        @Override
        public void defaultMethod() {
            System.out.println("OverFuncationImpl defaultMethod");
        }
    }

    /**
     * Java 8带来的另一个有趣的特性是在接口中可以定义静态方法
     */
    private interface FunctionalFactory {
        // Interfaces now allow static methods
        static Functional create(Supplier<Functional> supplier) {
            return supplier.get();
        }
    }


    public static void main(String[] args) {
        Functional functionalImpl = FunctionalFactory.create(FunctionalImpl::new);
        functionalImpl.method();
        functionalImpl.defaultMethod();


        Functional overFuncationalImpl = FunctionalFactory.create(OverFunctionalImpl::new);
        overFuncationalImpl.method();
        overFuncationalImpl.defaultMethod();

    }
}
