package com.example;

public interface HelloMBean {
    void sayHello();

    int add(int x, int y);

    String getName();

    void setName(String name);

    int getCacheSize();

    void setCacheSize(int size);
}
