package com.example;

public class Hello implements HelloMBean {
    private static final int DEFAULT_CACHE_SIZE = 200;
    private String name = "Reginald";
    private int cacheSize = DEFAULT_CACHE_SIZE;

    @Override
    public void sayHello() {
        System.out.println("hello, world");
    }

    @Override
    public int add(int x, int y) {
        return x + y;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getCacheSize() {
        return cacheSize;
    }

    @Override
    public synchronized void setCacheSize(int size) {
        cacheSize = size;
        System.out.println("Cache size now " + cacheSize);
    }
}
