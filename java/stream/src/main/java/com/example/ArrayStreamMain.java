package com.example;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class ArrayStreamMain {

    public static void main(String[] args) {
        final Integer[] integerArray = { 1, 2, 3, 4, 5 };

        Arrays.stream(integerArray)
              .forEach(System.out::print);
        System.out.println();

        Stream.of(integerArray)
              .peek(System.out::print)
              .forEach(System.out::print);
        System.out.println();

        // Primitive Array
        final int[] intArray = { 1, 2, 3, 4, 5 };
        List<Integer> list1 = IntStream.of(intArray)
                                       .boxed()
                                       .collect(toList());
        System.out.println(list1);

        final long[] longArray = { 6L, 7L, 8L, 9L, 10L };
        List<Long> list2 = LongStream.of(longArray)
                                     .boxed()
                                     .collect(toList());
        System.out.println(list2);

        // List to Array
        List<Integer> list3 = List.of(1, 2, 3, 4, 5);
        List<Integer> list4 = List.of(6, 7, 8, 9, 10);

        int[] array1 = list3.stream()
                            .mapToInt(x -> x)
                            .toArray();
        long[] array2 = list4.stream()
                             .mapToLong(x -> x)
                             .toArray();
        String[] array3 = list3.stream()
                               .map(Object::toString)
                               .toArray(String[]::new);

        System.out.println("array: " + Arrays.toString(array1));
        System.out.println("array: " + Arrays.toString(array2));
        System.out.println("array: " + Arrays.toString(array3));
    }
}
