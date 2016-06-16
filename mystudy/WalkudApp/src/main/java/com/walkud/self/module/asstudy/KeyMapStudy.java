package com.walkud.self.module.asstudy;

import java.util.ArrayList;

/**
 * Created by Walkud on 16/6/16.
 */

public class KeyMapStudy {


    /**
     * 编写正则表达式（Edit Regex）
     * 使用AS自带的正则表达式验证
     * 快捷键：Alt + Enter 或CMD+1
     *
     * @param s
     * @return
     */
    public boolean editRegex(String s) {
        return s.matches("");
    }

    /**
     * Enter和Tab进行代码补全的差别（Enter vs Tab for Code Completion）
     * 使用代码不全时 Enter与Tab的区别
     *
     * @param s
     */
    public void codeCompletion(String s) {

        //using Enter
        s.toString();

        //using Tab
        s.toString();

    }

    /**
     * 提取方法（Extract Method）
     * 将方法内代码块抽离为一个方法
     * 快捷键：Cmd + Alt + M(OS X)、Ctrl + Alt + M(Windows/Linux)；
     */
    public void methodExtract() {

        //初始化1
        String a = "";
        String b = "";


        //初始化2
        int c = 2;
        int d = 2;

    }

    /**
     * 用于调用paramsExtract方法,测试抽离参数,注意观察该方法变化
     */
    public void callParamsExtract() {
        paramsExtract();
    }

    /**
     * 提取参数（Extract Parameter）
     * 将方法内局部变量抽离为改方法参数
     * Cmd + Alt + P(OS X)、Ctrl + Alt + P(Windows/Linux)；
     */
    public void paramsExtract() {

        String hello = "hello";

        String world = "world";

    }

    /**
     * 提取变量（Extract Variable）
     * 快速声明变量,并取名
     * 快捷键：Cmd + Alt + V(OS X)、Ctrl + Alt + V(Windows/Linux)；
     * Cmd + Alt + L(OS X Eclipse)
     */
    public void variableExtract() {

        new Dog();

        new ArrayList<>();

    }


    /**
     * 合并行和文本（Join Lines and Literals）
     * 描述：这个操作比起在行末使劲按删除键爽多了！该操作遵守格式化规则，同时：
     * 合并两行注释，同时移除多余的//；
     * 合并多行字符串，移除+和双引号；
     * 合并字段的声明和初始化赋值；
     * <p>
     * 快捷键：Ctrl + Shift + J；
     *
     * @param s
     */
    public void joinLinesAndLiterals(String s) {

        int i;
        i = 1;

        if (s == null) {
            i = 0;
        }

        i = 5;
        //JoinLines

    }

    /**
     * 包裹代码（Surround With）
     * 该操作可以用特定代码结构包裹住选中的代码块，通常是if语句，循环，try/catch语句或者runnable语句。
     * 如果你没有选中任何东西，该操作会包裹当前一整行。
     * 快捷键：Cmd + Alt + T(OS X)、Ctrl + Alt + T(Windows/Linux)
     */
    public void surroundWith() {
        paramsExtract();
    }

}
