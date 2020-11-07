package com.example;

import java.util.Map;

import com.google.common.cache.CacheLoader;

public class CustomCacheLoader extends CacheLoader<String, String> {
    private static final Map<String, String> CACHE = Map.of("key1", "value1",
                                                            "key2", "value2",
                                                            "key3", "value3");

    @Override
    public String load(String key) throws Exception {
        System.out.println("load: " + key);
        return CACHE.getOrDefault(key, "");
    }
}
