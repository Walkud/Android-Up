package com.walkud.self.module.asstudy;

/**
 * Created by Walkud on 16/6/16.
 */

public class Dog {

    private String name;

    public Dog() {
        this("Dog");
    }

    public Dog(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean haveName() {
        return this.name != null && this.name.length() != 0;
    }

    public boolean isSame(String name) {
        return this.name.equals(name);
    }

    /**
     * 测试抛出异常
     */
    public void throwException() {
        executeCode();
    }

    /**
     * 执行异常代码
     */
    private void executeCode() {
        String s = null;
        s.toString();
    }
}
