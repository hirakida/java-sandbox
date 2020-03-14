package com.example.repository;

import static com.example.config.MongoConfig.DATABASE_NAME;

import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexModel;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

@Component
public class DocumentRepository {
    private static final String COLLECTION_NAME = "document";
    public static final String KEY1_FIELD = "key1";
    public static final String KEY2_FIELD = "key2";
    public static final String KEY3_FIELD = "key3";
    private final MongoCollection<Document> collection;

    public DocumentRepository(MongoClient mongoClient) {
        collection = mongoClient.getDatabase(DATABASE_NAME)
                                .getCollection(COLLECTION_NAME);
    }

    @PostConstruct
    public void init() {
        collection.drop();
        List<IndexModel> indexes =
                List.of(new IndexModel(Indexes.ascending(KEY1_FIELD),
                                       new IndexOptions().unique(true).background(true)),
                        new IndexModel(Indexes.ascending(KEY2_FIELD),
                                       new IndexOptions().unique(true).background(true)));
        collection.createIndexes(indexes);
    }

    public long countDocuments() {
        return collection.countDocuments();
    }

    public FindIterable<Document> find() {
        return collection.find();
    }

    public FindIterable<Document> findDesc() {
        return collection.find()
                         .sort(Sorts.descending(KEY1_FIELD));
    }

    public FindIterable<Document> find(long key1) {
        return collection.find(Filters.eq(KEY1_FIELD, key1));
    }

    public FindIterable<Document> find(long key2, long key3) {
        return collection.find(Filters.and(Filters.eq(KEY2_FIELD, key2),
                                           Filters.eq(KEY3_FIELD, key3)));
    }

    public void insertOne(Document document) {
        collection.insertOne(document);
    }

    public void insertMany(List<Document> documents) {
        collection.insertMany(documents);
    }

    public UpdateResult updateOne(long key1, String field, Object value) {
        return collection.updateOne(Filters.eq(KEY1_FIELD, key1),
                                    Updates.set(field, value));
    }

    public UpdateResult updateOne(long key1, String field1, Object value1, String field2, Object value2) {
        return collection.updateOne(Filters.eq(KEY1_FIELD, key1),
                                    Updates.combine(Updates.set(field1, value1),
                                                    Updates.set(field2, value2)));
    }

    public DeleteResult deleteOne(long key1) {
        return collection.deleteOne(Filters.eq(KEY1_FIELD, key1));
    }

    public DeleteResult deleteMany(String field, Object value) {
        return collection.deleteMany(Filters.eq(field, value));
    }
}
