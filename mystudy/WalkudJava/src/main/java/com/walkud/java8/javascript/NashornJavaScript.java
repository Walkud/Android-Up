package com.walkud.java8.javascript;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Java 8提供了新的Nashorn JavaScript引擎，使得我们可以在JVM上开发和运行JS应用。
 * Nashorn JavaScript引擎是javax.script.ScriptEngine的另一个实现版本，
 * 这类Script引擎遵循相同的规则，允许Java和JavaScript交互使用
 * 文／杜琪（简书作者）
 * 原文链接：http://www.jianshu.com/p/5b800057f2d8
 * 著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。
 * Created by Walkud on 16/8/2.
 */
public class NashornJavaScript {

    public static void main(String[] args) throws ScriptException {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        ScriptEngine engine = engineManager.getEngineByName("javascript");//EngineName大小写不限

        System.out.println(engine.getClass().getName());
        System.out.println("Result:" + engine.eval("function f() { return 1; }; f() + 1;"));

    }
}
