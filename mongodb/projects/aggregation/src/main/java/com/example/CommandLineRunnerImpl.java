package com.example;

import java.util.stream.IntStream;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.model.Role;
import com.example.model.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static final String USER_ID_FIELD = "userId";
    private static final String ROLE_ID_FIELD = "roleId";
    private final MongoDatabase database;
    private final CodecRegistry codecRegistry;
    private final AggregateRepository aggregateRepository;

    @Override
    public void run(String... args) {
        MongoCollection<Role> roleCollection = database.withCodecRegistry(codecRegistry)
                                                       .getCollection("role", Role.class);
        MongoCollection<User> userCollection = database.withCodecRegistry(codecRegistry)
                                                       .getCollection("user", User.class);
        roleCollection.drop();
        userCollection.drop();
        roleCollection.createIndex(Indexes.ascending(ROLE_ID_FIELD),
                                   new IndexOptions().unique(true).background(true));
        userCollection.createIndex(Indexes.ascending(USER_ID_FIELD),
                                   new IndexOptions().unique(true).background(true));

        roleCollection.insertOne(new Role(0, "user"));
        roleCollection.insertOne(new Role(1, "admin"));
        IntStream.rangeClosed(1, 10)
                 .mapToObj(i -> new User(i, "user" + i, i * 10, i % 2))
                 .forEach(userCollection::insertOne);

        for (Document document : aggregateRepository.maxAge(20, 60)) {
            log.info("max: {}", document);
        }
        for (Document document : aggregateRepository.minAge(20, 60)) {
            log.info("min: {}", document);
        }
        for (Document document : aggregateRepository.avgAge()) {
            log.info("avg: {}", document);
        }
        for (Document document : aggregateRepository.sumAge()) {
            log.info("sum: {}", document);
        }
        for (Document document : aggregateRepository.lookup()) {
            log.info("lookup: {}", document);
        }
    }
}
