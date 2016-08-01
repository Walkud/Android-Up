package com.walkud.java8.steam;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 新增的Stream API（java.util.stream）将生成环境的函数式编程引入了Java库中。
 * 这是目前为止最大的一次对Java库的完善，以便开发者能够写出更加有效、更加简洁和紧凑的代码。
 * <p/>
 * Steam API极大得简化了集合操作（后面我们会看到不止是集合）
 * <p/>
 * 文／杜琪（简书作者）
 * 原文链接：http://www.jianshu.com/p/5b800057f2d8
 * 著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。
 * Created by Walkud on 16/8/1.
 */
public class Streams {

    private enum Status {
        OPEN, CLOSED
    }

    ;

    private static final class Task {
        private final Status status;
        private final Integer points;

        Task(final Status status, final Integer points) {
            this.status = status;
            this.points = points;
        }

        public Integer getPoints() {
            return points;
        }

        public Status getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return String.format("[%s, %d]", status, points);
        }
    }

    private static final List<Task> tasks = Arrays.asList(
            new Task(Status.OPEN, 5),
            new Task(Status.OPEN, 13),
            new Task(Status.CLOSED, 8)
    );


    /**
     * 更多stream Api
     * http://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
     *
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("\n----------------简单学习----------------\n");

        streamSum();

        System.out.println("\n----------------并行处理----------------\n");

        parallel();

        System.out.println("\n----------------分组----------------\n");

        groupingBy();

        System.out.println("\n---------------占比-----------------\n");

        weigth();

        System.out.println("\n---------------IO操作-----------------\n");

        file();
    }

    /**
     * 简单的学习使用
     */
    private static void streamSum() {

        long totalPointsOfOpenTasks = tasks.stream()//转化为Steam
                .filter(task -> task.getStatus() == Status.OPEN)//过滤掉所有Closed的Task
                .mapToInt(Task::getPoints)//使用方法引用调用getPoints，Java8特性,将task转换为Integer集合
//                .mapToInt(task -> task.getPoints())//也可以使用Lambda调用getPoints方法
                .sum();//最后计算总和


        System.out.println("Total points: " + totalPointsOfOpenTasks);
    }

    /**
     * steam的另一个价值是创造性地支持并行处理（parallel processing）
     */
    private static void parallel() {
        double value = tasks.stream()
                .parallel()
                .map(task -> task.getPoints())
                .reduce(0, Integer::sum);

        System.out.println("Total points (all tasks): " + value);

    }

    /**
     * 分组
     */
    private static void groupingBy() {
        final Map<Status, List<Task>> map = tasks
                .stream()
                .collect(Collectors.groupingBy(Task::getStatus));

        System.out.println(map);
    }

    /**
     * 计算集合中每个任务的点数在集合中所占的比重
     */
    private static void weigth() {

        //计算出所有任务点总和
        final double totalPoints = tasks.stream().
                mapToDouble(Task::getPoints)
                .sum();

//        List<String> result = tasks.stream()// Stream< String >
//                .mapToInt(Task::getPoints)//转换任务点集合 IntStream
//                .asLongStream()//转换为LongStream
//                .mapToDouble(point -> point / totalPoints)//计算每个任务的占比并转换为DoubleStream
//                .boxed()//// Stream< Double >
//                .mapToLong(point -> (long) (point * 100))// LongStream 按百分比显示
//                .mapToObj(point -> point + "%")//添加百分比符号 Stream< String>
//                .collect(Collectors.toList());// List< String >

        //简化代码行数，增加了代码逻辑理解难度
        List<String> result = tasks.stream()
                .mapToDouble(task -> task.getPoints() / totalPoints)//计算每个任务中的点数占比，并转换为DoubleStream
                .mapToObj(point -> (long) (point * 100) + "%")//将任务中点数占比已百分比显示
                .collect(Collectors.toList());//转换为List<String>


        //疑问 输出结果[19%, 50%, 30%]，总和只有99%，那1%到哪里去了
        System.out.println(result);

    }


    /**
     * 传统的IO操作（从文件或者网络一行一行得读取数据）
     */
    private static void file() {
        final Path path = new File("/Users/jan/Downloads/stream.txt").toPath();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            lines.onClose(() -> System.out.println("Done!")).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
