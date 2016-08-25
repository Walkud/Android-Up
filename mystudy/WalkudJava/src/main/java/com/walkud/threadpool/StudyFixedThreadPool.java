package com.walkud.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Walkud on 16/8/25.
 */

public class StudyFixedThreadPool {

    public static void main(String[] args) {

        List<Person> persons = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            persons.add(new Person("Dog" + i));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(final Person person :persons){
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                   System.out.println(person.name);
                }
            });
        }

    }


    public static class Person {

        public String name;

        public Person(String name) {
            this.name = name;
        }
    }
}
