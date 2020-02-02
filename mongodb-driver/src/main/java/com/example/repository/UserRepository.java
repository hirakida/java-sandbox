package com.example.repository;

import static com.example.config.MongoConfig.DATABASE_NAME;

import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class UserRepository {
    private static final String COLLECTION_NAME = "user";
    private static final String USER_ID_FIELD = "userId";
    private static final String NAME_FIELD = "name";
    private final MongoCollection<User> collection;

    public UserRepository(MongoClient mongoClient, CodecRegistry codecRegistry) {
        collection = mongoClient.getDatabase(DATABASE_NAME)
                                .withCodecRegistry(codecRegistry)
                                .getCollection(COLLECTION_NAME, User.class);
    }

    public void drop() {
        collection.drop();
    }

    public String createIndex() {
        return collection.createIndex(Indexes.ascending(USER_ID_FIELD),
                                      new IndexOptions().unique(true).background(true));
    }

    public long countDocuments() {
        return collection.countDocuments();
    }

    public FindIterable<User> find() {
        return collection.find();
    }

    public FindIterable<User> find(long userId) {
        return collection.find(Filters.eq(USER_ID_FIELD, userId));
    }

    public void insertOne(User user) {
        collection.insertOne(user);
    }

    public void insertMany(List<User> users) {
        collection.insertMany(users);
    }

    public UpdateResult updateOne(User user) {
        return collection.updateOne(Filters.eq(USER_ID_FIELD, user.getUserId()),
                                    Updates.set(NAME_FIELD, user.getName()));
    }

    public User findOneAndUpdate(long userId, User user) {
        return collection.findOneAndUpdate(Filters.eq(USER_ID_FIELD, userId),
                                           Updates.set(NAME_FIELD, user.getName()));
    }

    public DeleteResult deleteOne(long userId) {
        return collection.deleteOne(Filters.eq(USER_ID_FIELD, userId));
    }

    public User findOneAndDelete(long userId) {
        return collection.findOneAndDelete(Filters.eq(USER_ID_FIELD, userId));
    }
}
