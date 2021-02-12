package com.example.grouping;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.summarizingLong;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;

public final class GroupingMain {

    public static void main(String[] args) {
        final List<Data> list = List.of(new Data(1L, "1"),
                                        new Data(2L, "2-1"),
                                        new Data(2L, "2-2"),
                                        new Data(3L, "3-1"),
                                        new Data(3L, "3-2"));

        Map<Long, List<Data>> map1 = list.stream()
                                         .collect(groupingBy(Data::getId));
        System.out.println(map1);

        // mapping
        // {1=[1], 2=[2-1, 2-2], 3=[3-1, 3-2]}
        Map<Long, List<String>> map2 = list.stream()
                                           .collect(groupingBy(Data::getId,
                                                               mapping(Data::getValue, toList())));
        System.out.println(map2);

        // {1=1, 2=2-1,2-2, 3=3-1,3-2}
        Map<Long, String> map3 = list.stream()
                                     .collect(groupingBy(Data::getId,
                                                         mapping(Data::getValue, joining(","))));
        System.out.println(map3);

        // LongSummaryStatistics
        Map<Long, LongSummaryStatistics> map4 = list.stream()
                                                    .collect(groupingBy(Data::getId,
                                                                        summarizingLong(Data::getId)));
        System.out.println(map4);
    }
}
