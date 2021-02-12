package com.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class Main {

    public static void main(String[] args) throws Exception {
        String str1 = "AAA";
        Class<String> clazz1 = String.class;
        Class<? extends String> clazz2 = str1.getClass();
        Class<?> clazz3 = Class.forName("java.lang.String");
        System.out.println(clazz1);
        System.out.println(clazz2);
        System.out.println(clazz3);

        // constructor
        Constructor<?> constructor = clazz1.getConstructor(String.class);
        String str2 = (String) constructor.newInstance("BBB");
        System.out.println(str2);

        // method
        Class<?> clazz4 = Class.forName("java.lang.StringBuilder");
        Method length = clazz4.getMethod("length");
        Method append = clazz4.getMethod("append", String.class);
        StringBuilder builder = (StringBuilder) clazz4.getConstructor().newInstance();
        System.out.println("length: " + length.invoke(builder));
        append.invoke(builder, "CCC");
        System.out.println("length: " + length.invoke(builder));

        // field
        Field field = Demo.class.getField("value");
        System.out.println(field);
        System.out.println("canAccess: " + field.canAccess(new Demo(10)));

        arrayDemo();
        collectionDemo();
        annotationDemo();
    }

    private static void arrayDemo() {
        Class<String[]> clazz1 = String[].class;
        System.out.println(clazz1);

        String[] array = { "aaa", "bbb" };
        Class<? extends String[]> clazz2 = array.getClass();
        System.out.println(clazz2);

        Class<?> clazz3;
        try {
            clazz3 = Class.forName("[Ljava.lang.String;");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(clazz3);
    }

    private static void collectionDemo() {
        Class<List> clazz1 = List.class;
        System.out.println(clazz1);

        Class<ArrayList> clazz2 = ArrayList.class;
        System.out.println(clazz2);

        List<String> list1 = List.of("aaa", "bbb");
        Class<? extends List> clazz3 = list1.getClass();
        System.out.println(clazz3);

        Class<?> clazz4;
        try {
            clazz4 = Class.forName("java.util.List");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(clazz4);
    }

    private static void annotationDemo() {
        Class<SuppressWarnings> clazz1 = SuppressWarnings.class;
        System.out.println(clazz1);

        Class<?> clazz2;
        try {
            clazz2 = Class.forName("java.lang.SuppressWarnings");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(clazz2);
    }

    public static class Demo {
        public int value;

        public Demo(int value) {
            this.value = value;
        }
    }
}
