package com.example;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static final String CONNECTION_STRING = "mongodb://user1:pwd1@localhost:27017/?authSource=admin";
    private static final String DATABASE_NAME = "db1";
    private static final String COLLECTION_NAME = "capped";
    private static final String TIMESTAMP_FIELD = "timestamp";

    @Override
    public void run(String... args) {
        MongoClient client = MongoClients.create(CONNECTION_STRING);
        MongoDatabase database = client.getDatabase(DATABASE_NAME);
        log.info("collectionNames: {}", toList(database.listCollectionNames()));

        MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);
        collection.drop();
        database.createCollection(COLLECTION_NAME,
                                  new CreateCollectionOptions().capped(true).sizeInBytes(1024000));
        collection.createIndex(Indexes.ascending(TIMESTAMP_FIELD), new IndexOptions().background(true));

        IntStream.rangeClosed(1, 10)
                 .mapToObj(i -> newDocument("data" + i))
                 .forEach(collection::insertOne);

        log.info("countDocuments: {}", collection.countDocuments());
        for (Document document : collection.find()) {
            log.info("{}", document);
        }
    }

    private static <T> List<T> toList(MongoIterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                            .collect(Collectors.toList());
    }

    private static Document newDocument(String value) {
        Document document = new Document();
        document.append("timestamp", Instant.now().toEpochMilli());
        document.append("value", value);
        return document;
    }
}
