package com.example.repository;

import static com.example.config.MongoConfig.DATABASE_NAME;
import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.mongodb.MongoClient;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.Projections;
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
    private static final String ROLE_ID_FIELD = "roleId";
    private static final String AGE_FIELD = "age";
    private final MongoCollection<User> collection;
    private final MongoCollection<Document> aggregateCollection;

    public UserRepository(MongoClient mongoClient, CodecRegistry codecRegistry) {
        collection = mongoClient.getDatabase(DATABASE_NAME)
                                .withCodecRegistry(codecRegistry)
                                .getCollection(COLLECTION_NAME, User.class);
        aggregateCollection = mongoClient.getDatabase(DATABASE_NAME)
                                         .getCollection(COLLECTION_NAME);
    }

    @PostConstruct
    public void init() {
        collection.drop();
        collection.createIndex(Indexes.ascending(USER_ID_FIELD),
                               new IndexOptions().unique(true).background(true));
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

    public AggregateIterable<Document> avg() {
        Bson group = Aggregates.group('$' + ROLE_ID_FIELD, Accumulators.avg("avg_age", '$' + AGE_FIELD));
        return aggregateCollection.aggregate(List.of(group));
    }

    public AggregateIterable<Document> max() {
        Bson group = Aggregates.group('$' + ROLE_ID_FIELD, Accumulators.max("max_age", '$' + AGE_FIELD));
        return aggregateCollection.aggregate(List.of(group));
    }

    public AggregateIterable<Document> min() {
        Bson group = Aggregates.group('$' + ROLE_ID_FIELD, Accumulators.min("min_age", '$' + AGE_FIELD));
        return aggregateCollection.aggregate(List.of(group));
    }

    public AggregateIterable<Document> sum() {
        Bson group = Aggregates.group('$' + ROLE_ID_FIELD, Accumulators.sum("sum_age", '$' + AGE_FIELD));
        return aggregateCollection.aggregate(List.of(group));
    }

    public AggregateIterable<Document> lookup() {
        Bson lookup = Aggregates.lookup("role", "roleId", "roleId", "role");
        Bson project =
                Aggregates.project(Projections.fields(
                        Projections.excludeId(),
                        Projections.exclude("roleId", "createdAt", "role._id")));
        return aggregateCollection.aggregate(List.of(lookup, project));
    }
}
