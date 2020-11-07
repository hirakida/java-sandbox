package com.example;

import java.util.function.Function;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class Main {

    public static void main(String[] args) {
        ClassLoader classLoader = Main.class.getClassLoader();

        Class<?> dynamicType1 = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(classLoader)
                .getLoaded();

        try {
            Object instance = dynamicType1.getDeclaredConstructor().newInstance();
            System.out.println(instance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Class<? extends Function> dynamicType2 = new ByteBuddy()
                .subclass(Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new GreetingInterceptor()))
                .make()
                .load(classLoader)
                .getLoaded();

        try {
            @SuppressWarnings("unchecked")
            Function<String, String> instance = dynamicType2.getDeclaredConstructor().newInstance();
            System.out.println(instance.apply("Byte Buddy"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
