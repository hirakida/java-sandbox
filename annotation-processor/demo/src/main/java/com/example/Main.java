package com.example;

public class Main {

    public static void main(String[] args) {
        Model1 model1 = new Model1();
        model1.setMessage("Hello!");
        System.out.println(model1.getMessage());

        Model2 model2 = new Model2();
        model2.setMessage("Hello!");
        System.out.println(model2.getMessage());
    }
}
