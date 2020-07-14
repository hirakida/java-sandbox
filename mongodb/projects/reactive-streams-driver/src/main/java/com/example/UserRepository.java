package com.example;

import java.util.List;

import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;

@Component
public class UserRepository {
    private static final String COLLECTION_NAME = "user";
    private static final String USER_ID_FIELD = "userId";
    private final MongoCollection<User> collection;

    public UserRepository(MongoDatabase database) {
        collection = database.getCollection(COLLECTION_NAME, User.class);
    }

    public Publisher<Void> drop() {
        return collection.drop();
    }

    public Publisher<String> createIndex() {
        return collection.createIndex(Indexes.ascending(USER_ID_FIELD),
                                      new IndexOptions().unique(true).background(true));
    }

    public Publisher<User> find() {
        return collection.find();
    }

    public Publisher<User> find(long userId) {
        return collection.find(Filters.eq(USER_ID_FIELD, userId));
    }

    public Publisher<InsertOneResult> insertOne(User user) {
        return collection.insertOne(user);
    }

    public Publisher<InsertManyResult> insertMany(List<User> users) {
        return collection.insertMany(users);
    }
}
