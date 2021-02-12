package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public final class ListMain {

    public static void main(String[] args) {

        List<Long> list1 = new ArrayList<>();
        list1.add(1L);
        list1.add(2L);
        list1.add(3L);
        System.out.println(list1);      // [1, 2, 3]

        System.out.println("contains: " + list1.contains(1L));

        // Array to List
        Integer[] array1 = { 1, 2, 3, 4 };
        List<Integer> list2 = Arrays.asList(array1);
        System.out.println(list2);

        List<Integer> list3 = List.of(array1);
        System.out.println(list3);

        // forEach
        list1.forEach(new Consumer<Long>() {
            @Override
            public void accept(Long l) {
                System.out.println(l);
            }
        });

        list1.forEach(System.out::println);

        // shuffle
        Collections.shuffle(list1);
        System.out.println(list1);

        Collections.shuffle(list1);
        System.out.println(list1);

        Collections.shuffle(list1);
        System.out.println(list1);
    }
}
