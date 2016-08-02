package com.walkud.java8.parallelarrays;

import java.time.Clock;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Java8版本新增了很多新的方法，用于支持并行数组处理。
 * 最重要的方法是parallelSort()，可以显著加快多核机器上的数组排序
 * Created by Walkud on 16/8/2.
 */
public class ParallelArrays {


    public static void main(String[] args) {

        normalOut();

        parraleSetAll();

    }


    /**
     * 正常输出
     */
    private static void normalOut() {
        long[] arrLong = new long[20000];

        long startTime = Clock.systemDefaultZone().millis();

        for (int i = 0; i < arrLong.length; i++) {
            arrLong[i] = ThreadLocalRandom.current().nextLong(1000000);
        }

        Arrays.stream(arrLong).limit(10).forEach(index -> System.out.print(index + " "));

        long endTime = Clock.systemDefaultZone().millis();
        System.out.println("\nNormal out Time : " + (endTime - startTime));
    }

    /**
     * 学习parraleSetAll
     */
    private static void parraleSetAll() {
        long[] arrLong = new long[20000];

        long startTime = Clock.systemDefaultZone().millis();

        Arrays.parallelSetAll(arrLong, index -> ThreadLocalRandom.current().nextLong(1000000));
        Arrays.stream(arrLong).limit(10).forEach(index -> System.out.print(index + " "));

        long endTime = Clock.systemDefaultZone().millis();
        System.out.println("\nParraleSetAll Time : " + (endTime - startTime));


        Arrays.parallelSort(arrLong);
        Arrays.stream(arrLong).limit(10).forEach(index -> System.out.print(index + " "));

    }


}
