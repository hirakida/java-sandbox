package com.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

public final class CacheMain {

    public static void main(String[] args) {
        cache();
        loadingCache();
    }

    private static void cache() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                                                  .maximumSize(100)
                                                  .expireAfterAccess(5, TimeUnit.SECONDS)
                                                  .expireAfterWrite(5, TimeUnit.SECONDS)
                                                  .build();
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        System.out.println("key1: " + cache.getIfPresent("key1"));
        System.out.println("key2: " + cache.getIfPresent("key2"));

        // invalidate
        cache.invalidate("key1");
        System.out.println("key1: " + cache.getIfPresent("key1"));    // null
        System.out.println("key2: " + cache.getIfPresent("key2"));

        // invalidateAll
        cache.invalidateAll();
        System.out.println("key2: " + cache.getIfPresent("key2"));    // null

        cache.put("key3", "value3");
        System.out.println("key3: " + cache.getIfPresent("key3"));

        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("key3: " + cache.getIfPresent("key3"));    // null
    }

    private static void loadingCache() {
        LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
                                                                .maximumSize(100)
                                                                .expireAfterAccess(5, TimeUnit.SECONDS)
                                                                .expireAfterWrite(5, TimeUnit.SECONDS)
                                                                .build(new CustomCacheLoader());
        try {
            System.out.println("LoadingCache key1: " + loadingCache.get("key1"));
            System.out.println("LoadingCache key2: " + loadingCache.get("key2"));
            System.out.println("LoadingCache key3: " + loadingCache.get("key3"));
            System.out.println("LoadingCache key4: " + loadingCache.get("key4"));    // null
            TimeUnit.SECONDS.sleep(6);
            System.out.println("LoadingCache key1: " + loadingCache.get("key1"));
        } catch (InterruptedException | ExecutionException e) {
            System.err.println(e.getMessage());
        }
    }
}
