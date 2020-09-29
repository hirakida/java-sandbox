package com.example.repository;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class UserRepository {
    private static final String COLLECTION_NAME = "user";
    private static final String USER_ID_FIELD = "userId";
    private static final String NAME_FIELD = "name";
    private static final String AGE_FIELD = "age";
    private static final String CREATED_AT_FIELD = "createdAt";
    private final MongoCollection<User> collection;

    public UserRepository(MongoDatabase database, CodecRegistry codecRegistry) {
        collection = database.withCodecRegistry(codecRegistry)
                             .getCollection(COLLECTION_NAME, User.class);
    }

    @PostConstruct
    public void init() {
        collection.drop();
        collection.createIndex(Indexes.ascending(USER_ID_FIELD),
                               new IndexOptions().unique(true).background(true));
        collection.createIndex(Indexes.compoundIndex(Indexes.ascending(AGE_FIELD),
                                                     Indexes.ascending(CREATED_AT_FIELD)));
    }

    public long countDocuments() {
        return collection.countDocuments();
    }

    public FindIterable<User> find() {
        return collection.find()
                         .sort(Sorts.ascending(USER_ID_FIELD));
    }

    public FindIterable<User> find(long userId) {
        return collection.find(Filters.eq(USER_ID_FIELD, userId));
    }

    public FindIterable<User> find(String name) {
        return collection.find(Filters.regex(NAME_FIELD, name));
    }

    public BulkWriteResult bulkWrite(List<User> users) {
        List<WriteModel<User>> models = users.stream()
                                             .map(InsertOneModel::new)
                                             .collect(toList());
        return collection.bulkWrite(models, new BulkWriteOptions().ordered(true));
    }

    public void insertOne(User user) {
        collection.insertOne(user);
    }

    public void insertMany(List<User> users) {
        collection.insertMany(users);
    }

    public UpdateResult replaceOne(User user) {
        return collection.replaceOne(Filters.eq(USER_ID_FIELD, user.getUserId()), user);
    }

    public UpdateResult upsertOne(User user) {
        return collection.replaceOne(Filters.eq(USER_ID_FIELD, user.getUserId()), user,
                                     new ReplaceOptions().upsert(true));
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
