package com.example.repository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.example.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserRepository {
    private static final String COLLECTION_NAME = "change-streams";
    private static final String USER_ID_FIELD = "userId";
    private static final String NAME_FIELD = "name";
    private static final String UPDATED_AT_FIELD = "updatedAt";
    private final MongoCollection<User> collection;

    public UserRepository(MongoDatabase database) {
        collection = database.getCollection(COLLECTION_NAME, User.class);
    }

    public MongoCollection<User> getCollection() {
        return collection;
    }

    public void drop() {
        collection.drop();
    }

    public String createIndex() {
        return collection.createIndex(Indexes.ascending(USER_ID_FIELD),
                                      new IndexOptions().unique(true).background(true));
    }

    public void insertOne(User user) {
        collection.insertOne(user);
    }

    public UpdateResult updateOne(User user) {
        return collection.updateOne(Filters.eq(USER_ID_FIELD, user.getUserId()),
                                    Updates.combine(Updates.set(NAME_FIELD, user.getName()),
                                                    Updates.set(UPDATED_AT_FIELD, LocalDateTime.now())));
    }

    public DeleteResult deleteOne(long userId) {
        return collection.deleteOne(Filters.eq(USER_ID_FIELD, userId));
    }
}
