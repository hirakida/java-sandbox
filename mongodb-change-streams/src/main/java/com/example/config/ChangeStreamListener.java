package com.example.config;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ChangeStreamListener {
    private static final String OPERATION_TYPE = "operationType";
    private static final List<String> OPERATIONS = List.of("insert", "update", "delete");

    public void watch(MongoCollection<Document> collection) {
        List<Bson> pipeline = List.of(Aggregates.match(Filters.in(OPERATION_TYPE, OPERATIONS)));
        MongoCursor<ChangeStreamDocument<Document>> cursor = collection.watch(pipeline).iterator();

        try (cursor) {
            while (cursor.hasNext()) {
                ChangeStreamDocument<Document> document = cursor.next();
                log.info("{} {}", document.getOperationType(), document);
            }
        }
    }
}
