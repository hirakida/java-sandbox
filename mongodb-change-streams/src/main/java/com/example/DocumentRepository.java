package com.example;

import java.time.LocalDateTime;

import org.bson.Document;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DocumentRepository {
    private static final String DATABASE_NAME = "db1";
    private static final String COLLECTION_NAME = "change-streams";
    private static final String KEY_FIELD = "key1";
    @Getter
    private final MongoCollection<Document> collection;

    public DocumentRepository(MongoClient mongoClient) {
        collection = mongoClient.getDatabase(DATABASE_NAME)
                                .getCollection(COLLECTION_NAME);
    }

    public void drop() {
        collection.drop();
    }

    public String createIndex() {
        return collection.createIndex(Indexes.ascending(KEY_FIELD),
                                      new IndexOptions().unique(true).background(true));
    }

    public void insertOne(int key) {
        Document document = new Document();
        document.append(KEY_FIELD, key);
        document.append("created_at", LocalDateTime.now());
        collection.insertOne(document);
    }

    public UpdateResult updateOne(int key) {
        return collection.updateOne(Filters.eq(KEY_FIELD, key),
                                    Updates.set("updated_at", LocalDateTime.now()));
    }

    public DeleteResult deleteOne(int key) {
        return collection.deleteOne(Filters.eq(KEY_FIELD, key));
    }
}
