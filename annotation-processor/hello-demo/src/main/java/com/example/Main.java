package com.example;

@Hello
public class Main {

    public static void main(String[] args) {
        Model model = new Model();
        model.setMessage("Hello!");
        System.out.println(model.getMessage());
    }
}
