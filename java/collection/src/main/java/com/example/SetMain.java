package com.example;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetMain {

    public static void main(String[] args) {
        hashSet();
        treeSet();
    }

    private static void hashSet() {
        Set<String> set = new HashSet<>();
        set.add("1");
        set.add("z");
        set.add("5");
        set.add("2");
        set.add("a");
        set.add(null);
        System.out.println(set);    // [null, 1, a, 2, 5, z]

        set.remove("a");
        System.out.println(set);

        set.clear();
        System.out.println(set);
    }

    private static void treeSet() {
        Set<String> set = new TreeSet<>();
        set.add("1");
        set.add("z");
        set.add("5");
        set.add("2");
        set.add("a");
//        set.add(null);
        System.out.println(set);    // [1, 2, 5, a, z]
    }
}
