package com.walkud.java8.methodquote;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * 方法引用使得开发者可以直接引用现存的方法、Java类的构造方法或者实例对象。
 * 方法引用和Lambda表达式配合使用，使得java类的构造方法看起来紧凑而简洁，
 * 没有很多复杂的模板代码。
 * <p>
 * Created by Walkud on 16/7/29.
 */
public class Car {

    //引用构造器，注意构造器没有参数
    public static Car getCar(Supplier<Car> supplier) {
        return supplier.get();
    }

    /**
     * 引用静态方法
     *
     * @param car
     */
    public static void collide(Car car) {
        System.out.println("Collide " + car.toString());
    }

    /**
     * 引用带参成员方法
     *
     * @param another
     */
    public void follow(Car another) {
        System.out.println("Following the " + another.toString());
    }

    /**
     * 引用无参成员方法
     */
    public void repair() {
        System.out.println("Repaired " + this.toString());
    }


    public static void main(String[] args) {

        //第一种方法引用的类型是构造器引用，语法是Class::new，
        //或者更一般的形式：Class<T>::new。注意：这个构造器没有参数。
        Car car1 = getCar(Car::new);
        Car car2 = getCar(Car::new);

        List<Car> cars = Arrays.asList(car1, car2);

        //第二种方法引用的类型是静态方法引用，语法是Class::static_method。
        //注意：这个方法接受一个Car类型的参数。
        cars.forEach(Car::collide);

        //第三种方法引用的类型是某个实例对象的成员方法的引用，语法是instance::method。
        //注意：这个方法接受一个Car类型的参数。
        Car police = getCar(Car::new);
        cars.forEach(police::follow);

        //第四种方法引用的类型是某个类的成员方法的引用，语法是Class::method，
        //注意，这个方法没有定义入参。
        cars.forEach(Car::repair);


    }

}
