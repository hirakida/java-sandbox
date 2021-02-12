package com.example;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class StreamMain {

    public static void main(String[] args) {
        // 1 - 5
        List<Integer> list = Stream.of(1, 2, 3, 4, 5)
                                   .collect(toList());
        System.out.println(list);

        // 1 - 4
        list = IntStream.range(1, 5)
                        .boxed()
                        .collect(toList());
        System.out.println(list);

        // 1 - 5
        list = IntStream.rangeClosed(1, 5)
                        .boxed()
                        .collect(toList());
        System.out.println(list);

        // [1, 2, 3, 4, 5][6, 7, 8, 9, 10]
        final List<List<Integer>> list2 = Stream.of(List.of(1, 2, 3, 4, 5),
                                                    List.of(6, 7, 8, 9, 10))
                                                .collect(toList());
        System.out.println(list2);

        // Stream.concat
        // 1 - 9
        final List<Integer> list3 = Stream.concat(Stream.of(1, 2, 3, 4, 5),
                                                  Stream.of(6, 7, 8, 9, 10))
                                          .collect(toList());
        System.out.println(list3);

        // mapToObj
        final List<Map<Long, String>> list4 = LongStream.range(1, 5)
                                                        .mapToObj(x -> Map.of(x, String.valueOf(x)))
                                                        .collect(toList());
        System.out.println(list4);

        // toMap
        final List<Data> list5 = List.of(new Data(1L, "1"),
                                         new Data(2L, "2-1"),
                                         new Data(3L, "2-2"),
                                         new Data(4L, "3-1"),
                                         new Data(5L, "3-2"));
        final Map<Long, Data> map1 = list5.stream()
                                          .collect(toMap(Data::getId, Function.identity()));
        System.out.println(map1);
    }

    private static final class Data {
        private final Long id;
        private final String value;

        private Data(Long id, String value) {
            this.id = id;
            this.value = value;
        }

        public Long getId() {
            return id;
        }

        public String getValue() {
            return value;
        }
    }
}
