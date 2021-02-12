package com.example.sort;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.nullsFirst;
import static java.util.Comparator.nullsLast;
import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toList;

import java.util.List;

public final class SortMain {

    public static void main(String[] args) {
        sortInteger();
        sortObject();
        sortNullable();
    }

    private static void sortInteger() {
        final List<Integer> list = List.of(1, 3, 5, 4, 2);

        // naturalOrder
        // 1,2,3,4,5
        List<Integer> after = list.stream()
                                  .sorted(naturalOrder())
                                  .collect(toList());
        System.out.println(after);

        // reverseOrder
        // 5,4,3,2,1
        after = list.stream()
                    .sorted(reverseOrder())
                    .collect(toList());
        System.out.println(after);
    }

    private static void sortObject() {
        final List<Data1> data1 = List.of(new Data1(2),
                                          new Data1(1),
                                          new Data1(5),
                                          new Data1(4),
                                          new Data1(3));

        // comparing
        // code 1, 2, 3, 4, 5
        List<Data1> after = data1.stream()
                                 .sorted(comparing(Data1::getCode))
                                 .collect(toList());
        System.out.println(after);

        // comparingInt
        after = data1.stream()
                     .sorted(comparingInt(Data1::getCode))
                     .collect(toList());
        System.out.println(after);

        after = data1.stream()
                     .sorted((x1, x2) -> x1.diff(x2))
                     .collect(toList());
        System.out.println(after);

        after = data1.stream()
                     .sorted(Data1::diff)
                     .collect(toList());
        System.out.println(after);

        // reversed
        // code 5, 4, 3, 2, 1
        after = data1.stream()
                     .sorted(comparing(Data1::getCode).reversed())
                     .collect(toList());
        System.out.println(after);

        final List<Data2> data2 = List.of(new Data2(1, 1),
                                          new Data2(2, 3),
                                          new Data2(2, 2),
                                          new Data2(3, 5),
                                          new Data2(3, 4));

        // 1-1, 2-3, 2-2, 3-5, 3-4
        List<Data2> after2 = data2.stream()
                                  .sorted(comparing(Data2::getCode1))
                                  .collect(toList());
        System.out.println(after2);

        // thenComparing
        // 1-1, 2-2, 2-3, 3-4, 3-5
        after2 = data2.stream()
                      .sorted(comparing(Data2::getCode1).thenComparing(Data2::getCode2))
                      .collect(toList());
        System.out.println(after2);
    }

    private static void sortNullable() {
        final List<Data1> data1 = List.of(new Data1(1),
                                          new Data1(null),
                                          new Data1(2),
                                          new Data1(3),
                                          new Data1(4));

        // nullsFirst
        // null, 1, 2, 3, 4
        List<Data1> after = data1.stream()
                                 .sorted(comparing(Data1::getCode, nullsFirst(naturalOrder())))
                                 .collect(toList());
        System.out.println(after);

        // nullsLast
        // 1, 2, 3, 4, null
        after = data1.stream()
                     .sorted(comparing(Data1::getCode, nullsLast(naturalOrder())))
                     .collect(toList());
        System.out.println(after);
    }
}
