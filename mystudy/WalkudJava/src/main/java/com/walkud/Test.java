package com.walkud;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Walkud on 17/11/23.
 */

public class Test {

    public static void main(String[] args) {
        Set<Integer> EVENT_SET = new HashSet<>();
        List<Integer> list = Arrays.asList(1, 2, 3);
        EVENT_SET.add(1);
        for (int i : list) {

            if (EVENT_SET.contains(i)) {
                switch (i) {
                    case 1:
                        System.out.println("1");
                        break;
                    case 2:
                        System.out.println("2");
                        break;
                    case 3:
                        System.out.println("3");
                        break;
                }
            }
        }

    }
}
