package com.walkud.self.module.asstudy;

/**
 * Created by Walkud on 16/6/16.
 */

public class AsDebugStudy {


    public static Dog getDog() {
        return new Dog();
    }


    public static void main(String[] args) {


        analyzeDataFlowToHere();

        //取消注释会出现异常,用于测试分析堆栈信息
//        analyzeStacktrace();

        conditionalBreakpoints();

        evaluateExpression();

        loggingBreakpoints();

        temporaryBreakpoints();
    }


    /**
     * 分析传入数据流（Analyze data flow to here）
     * 这个操作将会根据当前选中的变量、参数或者字段，分析出其传递到此处的路径。
     * 当你进入某段陌生的代码，试图明白某个参数是怎么传递到此处的时候，这是一个非常有用的操作。
     * 调用：Menu → Analyze → Analyze Data Flow to Here
     * 快捷键：无，可以在设置中指定。
     * 相反的操作：分析传出数据流（Analyze data flow from here），这个将会分析当前选中的变量往下传递的路径，直到结束。
     */
    public static void analyzeDataFlowToHere() {

        Dog dog = getDog();

        String dogName = dog.getName();

        System.out.println("Dog Name:" + dogName);
    }

    /**
     * 堆栈追踪分析（Analyze Stacktrace）
     * 描述： 这个操作读取一份堆栈追踪信息，并且使它像logcat中那样可以点击。当你从bug报告中或者终端复制了一份堆栈追踪，使用该操作可以很方便地调试。
     * 调用：Menu → Analyze → Analyze Stacktrace
     * 快捷键：无，可以在设置中指定。
     * 更多：通过使用“ProGuard Unscramble Plugin”插件，也可以分析混淆过的堆栈追踪。
     */
    public static void analyzeStacktrace() {
        Dog dog = getDog();
        dog.throwException();
    }

    /**
     * 条件断点（Conditional Breakpoints）
     * 描述：简单说，就是当设定的条件满足时，才会触发断点。你可以基于当前范围输入一个java布尔表达式，
     * 并且条件输入框内是支持代码补全的。
     * 调用：右键(或者control+单击)需要填写表达式的断点，然后输入布尔表达式。
     */
    public static void conditionalBreakpoints() {
        for (int i = 0; i < 10; i++) {
            System.out.println("value:" + i);
        }
    }

    /**
     * 计算表达式（Evaluate Expression）
     * 描述：这个操作可以用来查看变量的内容并且计算几乎任何有效的java表达式。需要注意的是，
     * 如果你修改了变量的状态，这个状态在你恢复代码执行后依然会保留。
     * 快捷键：处在断点状态时，光标放在变量处，按Alt + F8，即可显示计算表达式对话框。
     * <p>
     * ME:COMMAND+U
     */
    public static void evaluateExpression() {
        Dog dog = getDog();

        String name = "小黑";
        boolean isSame = dog.isSame(name);
        System.out.println(isSame);
    }

    /**
     * 日志断点（Logging Breakpoints）
     * 描述：这是一种打印日志而不是暂停的断点，当你想打印一些日志信息但是不想添加log代码后重新部署项目，这是一个非常有用的操作。
     * 调用：在断点上右键，取消Suspend的勾选，然后勾选上Log evaluated Expression，并在输入框中输入你要打印的日志信息。
     */
    public static void loggingBreakpoints() {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += i;
        }
    }

    /**
     * 临时断点（Temporary Breakpoints）
     * 描述：通过该操作可以添加一个断点，这个断点会在第一次被命中的时候自动移除。
     * 快捷键：Alt + 鼠标左键 点击代码左侧（鼠标）、Cmd + Alt + Shift + F8(OS X)、Ctrl + Alt + Shift + F8(Windows/Linux)
     * 选择 Remove once hit
     */
    public static void temporaryBreakpoints() {
        String s = "0";
        s = "1";
        System.out.println("s:" + s);
    }
}
