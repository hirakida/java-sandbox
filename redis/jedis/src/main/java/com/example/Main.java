package com.example;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);

        // String
        log.info("set {}", jedis.set("key1", "str1"));
        log.info("get {}", jedis.get("key1"));
        log.info("del {}", jedis.del("key1"));
        log.info("get {}", jedis.get("key1"));

        log.info("mset {}", jedis.mset("key1", "str1", "key2", "str2", "key3", "str3"));
        log.info("mget {}", jedis.mget("key1", "key2", "key3"));

        // Hash
        Map<String, String> map = new HashMap<>();
        map.put("key1", "val1");
        map.put("key2", "val2");
        map.put("key3", "val3");

        log.info("hmset {}", jedis.hmset("hash1", map));
        log.info("hmget {}", jedis.hmget("hash1", "key1", "key2", "key3"));

        jedis.close();
    }
}
