package com.example;

import java.util.List;

import javax.annotation.PostConstruct;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CappedRepository {
    private static final String COLLECTION_NAME = "capped";
    private static final String TIMESTAMP_FIELD = "timestamp";
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public CappedRepository(MongoDatabase database) {
        this.database = database;
        collection = database.getCollection(COLLECTION_NAME);
    }

    @PostConstruct
    public void init() {
        if (!exists()) {
            database.createCollection(COLLECTION_NAME,
                                      new CreateCollectionOptions().capped(true).sizeInBytes(1024000));

            collection.createIndex(Indexes.ascending(TIMESTAMP_FIELD),
                                   new IndexOptions().background(true));
        }
    }

    public long countDocuments() {
        return collection.countDocuments();
    }

    public FindIterable<Document> find() {
        return collection.find();
    }

    public void insertOne(Document document) {
        collection.insertOne(document);
    }

    public void insertMany(List<Document> documents) {
        collection.insertMany(documents);
    }

    private boolean exists() {
        for (String name : database.listCollectionNames()) {
            if (COLLECTION_NAME.equals(name)) {
                return true;
            }
        }
        return false;
    }
}
