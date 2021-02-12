package com.example;

import java.util.Properties;

public class PropertiesMain {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("put1", "100");
        properties.put("put2", 200);    // null
        properties.setProperty("set1", "100");
        properties.setProperty("set2", "200");

        System.out.println(properties.getProperty("put1"));     // 100
        System.out.println(properties.getProperty("put2"));     // null
        System.out.println(properties.getProperty("set1"));     // 100
        System.out.println(properties.getProperty("set2"));     // 200
    }
}
