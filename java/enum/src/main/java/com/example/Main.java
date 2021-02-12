package com.example;

import java.util.EnumSet;
import java.util.stream.Stream;

public final class Main {

    public static void main(String[] args) {

        // Enum1
        System.out.println(Enum1.FOO);                          // FOO
        System.out.println(Enum1.BAR);                          // BAR
        System.out.println("name: " + Enum1.FOO.name());        // FOO
        System.out.println("name: " + Enum1.BAR.name());        // BAR
        System.out.println("ordinal: " + Enum1.FOO.ordinal());  // 0
        System.out.println("ordinal: " + Enum1.BAR.ordinal());  // 1
        System.out.println("----------");

        // Enum2
        System.out.println(Enum2.FOO);
        System.out.println(Enum2.BAR);
        System.out.println("name: " + Enum2.FOO.name());        // FOO
        System.out.println("name: " + Enum2.BAR.name());        // BAR
        System.out.println("label: " + Enum2.FOO.getLabel());   // foo
        System.out.println("----------");

        // Enum3
        Enum3[] enums = Enum3.values();
        Stream.of(enums)
              .forEach(x -> {
                  System.out.println(x);
                  System.out.println("name: " + x.name());
                  System.out.println("ordinal: " + x.ordinal());
                  System.out.println("label: " + x.getLabel());
                  System.out.println("code: " + x.getCode());
                  System.out.println("----------");
              });

        EnumSet<Enum3> list = EnumSet.of(Enum3.FOO, Enum3.BAR);
        // contains
        System.out.println("contains: " + list.contains(Enum3.FOO)); // true
        System.out.println("contains: " + list.contains(Enum3.BAZ)); // false

        // Operation
        System.out.println("plus: " + Operation.PLUS.apply(1, 1));
    }
}
