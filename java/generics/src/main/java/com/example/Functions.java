package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Functions {

    public static <T> String getObjectName(T obj) {
        return obj.getClass().toString();
    }

    public static String getObjectName(List<?> list) {
        return list.getClass().toString();
    }

    public static String getObjectName(Set<?> list) {
        return list.getClass().toString();
    }

    public static String getObjectName(Map<?, ?> list) {
        return list.getClass().toString();
    }

    public static <T> void print(List<T> list) {
        System.out.println(list);
    }

    public static <T> void print(Set<T> set) {
        set.forEach(System.out::println);
    }

    public static <K, V> void print(Map<K, V> map) {
        map.forEach((key, value) -> System.out.println(key + ":" + value));
    }

    public static <T> Set<T> toSet(List<T> list) {
        return new HashSet<>(list);
    }

    public static <T> List<T> toList(Set<T> set) {
        return new ArrayList<>(set);
    }

    // extends
    public static <T> int getSize(List<? extends T> list) {
        return list.size();
    }

    public static <T extends CharSequence> int getSize2(List<T> list) {
        return list.size();
    }

    public static int getSize3(List<? extends CharSequence> list) {
        return list.size();
    }

    // super
    public static int getSize4(List<? super CharSequence> list) {
        return list.size();
    }

    public static <T> int getSize5(List<? super T> list) {
        return list.size();
    }
}
