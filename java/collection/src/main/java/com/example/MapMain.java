package com.example;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public final class MapMain {

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();
        map.put("key1", 1);
        map.put("key2", 2);

        Integer value = map.get("key1");
        System.out.println(value);  // 1

        value = map.get("key3");
        System.out.println(value);  // null

        value = map.putIfAbsent("key3", 3);
        System.out.println(value);  // null

        value = map.putIfAbsent("key3", 3);
        System.out.println(value);  // 3

        value = map.getOrDefault("key4", 4);
        System.out.println(value);  // 4

        System.out.println("containsKey: " + map.containsKey("key1"));
        System.out.println("containsValue: " + map.containsValue(1));

        // replace
        value = map.replace("key1", 11);
        System.out.println(value);  // 1
        value = map.replace("key11", 11);
        System.out.println(value);  // null

        boolean result = map.replace("key2", 22, 222);
        System.out.println(result); // false
        result = map.replace("key2", 2, 22);
        System.out.println(result); // true

        // for
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String k = entry.getKey();
            Integer v = entry.getValue();
            System.out.println("for " + k + ':' + v);
        }

        // foreach
        Set<String> keys = map.keySet();
        keys.forEach(System.out::println);

        Collection<Integer> values = map.values();
        values.forEach(System.out::println);

        map.forEach(new BiConsumer<String, Integer>() {
            @Override
            public void accept(String key, Integer value) {
                System.out.println("forEach " + key + ':' + value);
            }
        });

        map.forEach((k, v) -> System.out.println("forEach " + k + ':' + v));
    }
}
