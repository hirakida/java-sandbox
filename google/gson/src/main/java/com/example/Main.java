package com.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {

    public static void main(String[] args) {
        Gson gson1 = new Gson();
        Gson gson2 = new GsonBuilder().serializeNulls().create();

        User user1 = new User(1, "name1", "user1@example.com");
        User user2 = new User(2, "name2", null);

        System.out.println(gson1.toJson(user1));
        System.out.println(gson1.toJson(user2));
        System.out.println(gson2.toJson(user1));
        System.out.println(gson2.toJson(user2));
    }
}
