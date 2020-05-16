package com.example.repository;

import javax.annotation.PostConstruct;

import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.stereotype.Component;

import com.example.model.Role;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

@Component
public class RoleRepository {
    private static final String COLLECTION_NAME = "role";
    private static final String ROLE_ID_FIELD = "roleId";
    private final MongoCollection<Role> collection;

    public RoleRepository(MongoDatabase database, CodecRegistry codecRegistry) {
        collection = database.withCodecRegistry(codecRegistry)
                             .getCollection(COLLECTION_NAME, Role.class);
    }

    @PostConstruct
    public void init() {
        collection.drop();
        collection.createIndex(Indexes.ascending(ROLE_ID_FIELD),
                               new IndexOptions().unique(true).background(true));
    }

    public long countDocuments() {
        return collection.countDocuments();
    }

    public FindIterable<Role> find() {
        return collection.find();
    }

    public FindIterable<Role> find(long roleId) {
        return collection.find(Filters.eq(ROLE_ID_FIELD, roleId));
    }

    public void insertOne(Role role) {
        collection.insertOne(role);
    }
}
