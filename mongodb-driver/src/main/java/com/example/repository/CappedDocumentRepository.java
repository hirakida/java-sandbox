package com.example.repository;

import static com.example.config.MongoConfig.DATABASE_NAME;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CappedDocumentRepository {
    private static final String COLLECTION_NAME = "capped";
    private static final String KEY1_FIELD = "key1";
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public CappedDocumentRepository(MongoClient mongoClient) {
        database = mongoClient.getDatabase(DATABASE_NAME);
        collection = database.getCollection(COLLECTION_NAME);
    }

    public void drop() {
        collection.drop();
        CreateCollectionOptions options = new CreateCollectionOptions().capped(true)
                                                                       .sizeInBytes(1024000);
        database.createCollection(COLLECTION_NAME, options);
    }

    public String createIndex() {
        return collection.createIndex(Indexes.ascending(KEY1_FIELD),
                                      new IndexOptions().unique(true).background(true));
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
}
