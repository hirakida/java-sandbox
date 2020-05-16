package com.example;

import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.changestream.ChangeStreamDocument;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ChangeStreamListener<T> implements Runnable {
    private static final String OPERATION_TYPE = "operationType";
    private static final List<String> OPERATIONS = List.of("insert", "update", "delete");
    private final MongoCollection<T> collection;

    public ChangeStreamListener(MongoCollection<T> collection) {
        this.collection = collection;
    }

    @Override
    public void run() {
        List<Bson> pipeline = List.of(Aggregates.match(Filters.in(OPERATION_TYPE, OPERATIONS)));
        MongoCursor<ChangeStreamDocument<T>> cursor = collection.watch(pipeline).iterator();

        try (cursor) {
            while (cursor.hasNext()) {
                ChangeStreamDocument<T> document = cursor.next();
                log.info("{} {}", document.getOperationType(), document);
            }
        }
    }
}
