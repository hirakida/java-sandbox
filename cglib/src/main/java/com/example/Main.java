package com.example;

import net.sf.cglib.proxy.Enhancer;

public final class Main {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloService.class);
        enhancer.setCallback(new HelloInterceptor());

        HelloService service = (HelloService) enhancer.create();
        service.hello();
        service.hello("cglib");
    }
}
