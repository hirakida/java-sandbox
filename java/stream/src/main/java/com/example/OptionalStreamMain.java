package com.example;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class OptionalStreamMain {

    public static void main(String[] args) {

        List<Optional<String>> list = List.of(Optional.of("test1"),
                                              Optional.ofNullable(null),
                                              Optional.of("test2"),
                                              Optional.empty(),
                                              Optional.of("test3"));
        System.out.println(list);

        List<String> list1 = list.stream()
                                 .filter(Optional::isPresent)
                                 .map(Optional::get)
                                 .collect(toList());
        System.out.println(list1);

        List<String> list2 = list.stream()
                                 .flatMap(value -> value.map(Stream::of)
                                                        .orElse(Stream.empty()))
                                 .collect(toList());
        System.out.println(list2);

        // Java 9
        List<String> list3 = list.stream()
                                 .flatMap(Optional::stream)
                                 .collect(toList());
        System.out.println(list3);

        System.out.println(Optional.of("hello").stream().collect(toList()));
        System.out.println(Optional.empty().stream().collect(toList()));
        System.out.println(Optional.ofNullable(null).stream().collect(toList()));
    }
}
