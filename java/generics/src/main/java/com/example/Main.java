package com.example;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Main {

    public static void main(String[] args) {
        System.out.println(Functions.getObjectName("abcde")); // class java.lang.String

        // List
        List<String> list1 = List.of("11111", "22222");
        Functions.print(list1);
        System.out.println(Functions.getObjectName(list1));

        List<CharSequence> list2 = List.of();
        Functions.print(list2);
        System.out.println(Functions.getObjectName(list2));

        System.out.println("size: " + Functions.getSize(list1));
        System.out.println("size: " + Functions.getSize(list2));
        System.out.println("size: " + Functions.getSize2(list1));
        System.out.println("size: " + Functions.getSize2(list2));
        System.out.println("size: " + Functions.getSize3(list1));
        System.out.println("size: " + Functions.getSize3(list2));
        System.out.println("size: " + Functions.getSize4(list2));
        System.out.println("size: " + Functions.getSize5(list1));
        System.out.println("size: " + Functions.getSize5(list2));

        // Set
        List<String> list3 = List.of("33333", "44444");
        Set<String> set = Functions.toSet(list3);
        Functions.print(set);
        System.out.println(Functions.getObjectName(set));
        Functions.print(Functions.toList(set));

        // Map
        Map<Integer, String> map = Map.of(1, "value1", 2, "value2");
        Functions.print(map);
        System.out.println(Functions.getObjectName(map));

        // Class
        Generics1<String> class1 = new Generics1<>("Generics Class1");
        System.out.println(class1.get1());
        System.out.println(class1.get2(11111));
        System.out.println(class1.get2(22222L));
        System.out.println(class1.get2("33333"));

        Generics2<String> class2 = new Generics2<>("Generics Class2");
        System.out.println(class2.get());
    }
}
