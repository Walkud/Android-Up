package com.walkud.java8.typevalue;

/**
 * 更好的类型推断
 * Java 8编译器在类型推断方面有很大的提升，在很多场景下编译器可以推导出某个参数的数据类型，从而使得代码更为简洁。
 * Created by Walkud on 16/7/29.
 */
public class Value<T> {

    public static <T> T defaultValue() {
        return null;
    }

    public T getValue(T t, T defaultValue) {
        return (t == null) ? defaultValue : t;
    }


    public static class Book {

        public String name;

        public Book(String name) {
            this.name = name;
        }
    }


    public static void main(String[] args) {

        Value<String> value = new Value<>();
        String v = value.getValue("Walkud", Value.defaultValue());
        System.out.println(v);

        Value<Book> bookValue = new Value<>();
        Book book = bookValue.getValue(null, new Book("Java"));
        System.out.println(book.name);
    }

}
