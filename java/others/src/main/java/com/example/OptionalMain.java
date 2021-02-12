package com.example;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

public final class OptionalMain {

    public static void main(String[] args) {

        Optional<String> opt1 = Optional.of("AAA");
        Optional<String> opt2 = Optional.ofNullable(null);
        Optional<String> opt3 = Optional.ofNullable("BBB");
        Optional<String> opt4 = Optional.empty();
        System.out.println(opt1);   // Optional[AAA]
        System.out.println(opt2);   // Optional.empty
        System.out.println(opt3);   // Optional[VVV]
        System.out.println(opt4);   // Optional.empty

        // isPresent
        if (opt1.isPresent()) {
            System.out.println(opt1.get());
        }

        // orElse
        System.out.println(opt1.orElse("orElse"));
        System.out.println(opt4.orElse("orElse"));
        System.out.println(opt4.orElseGet(() -> "orElseGet " + LocalDateTime.now()));

        // orElseThrow
        System.out.println(opt1.orElseThrow(RuntimeException::new));
        System.out.println(opt1.orElseThrow());

        // or
        System.out.println(opt1.or(() -> Optional.of("or")));   // Optional[AAA]
        System.out.println(opt2.or(() -> Optional.of("or")));   // Optional[or]

        // ifPresent
        opt1.ifPresent(value -> System.out.println("ifPresent: " + value));

        // ifPresentOrElse
        opt1.ifPresentOrElse(value -> System.out.println("ifPresentOrElse: " + value),
                             () -> System.out.println("emptyAction"));
        opt2.ifPresentOrElse(value -> System.out.println("ifPresentOrElse: " + value),
                             () -> System.out.println("emptyAction"));

        // filter
        System.out.println("filter1: " + opt1.filter("AAA"::equals)); // Optional[AAA]
        System.out.println("filter2: " + opt1.filter("BBB"::equals)); // Optional.empty

        // map
        Optional<Integer> map1 = Optional.of(new Demo(1))
                                         .map(Demo::getCode);
        Optional<Demo> empty = Optional.empty();
        Optional<Integer> map2 = empty.map(Demo::getCode);
        System.out.println("map1: " + map1);                 // Optional[1]
        System.out.println("map2: " + map2);                 // Optional.empty

        // flatMap
        Optional<String> flatMap1 = opt1.flatMap(x -> Optional.of(x + "BBB"))
                                        .flatMap(x -> Optional.of(x + "CCC"));
        Optional<Object> map3 = opt1.map(x -> Optional.of(x + "BBB"))
                                    .map(x -> Optional.of(x + "CCC"));
        System.out.println("flatMap1: " + flatMap1);    // Optional[AAABBBCCC]
        System.out.println("map3: " + map3);            // Optional[Optional[Optional[AAABBB]CCC]]

        // stream
        Stream.of(opt1, opt2, opt3)
              .forEach(System.out::println);
    }

    private static String getDateTime() {
        return LocalDateTime.now().toString();
    }

    public static class Demo {
        private final int code;

        public Demo(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
