package com.example;

import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;

@Component
public class AggregateRepository {
    private static final String ROLE_ID_FIELD = "roleId";
    private static final String AGE_FIELD = "age";
    private final MongoCollection<Document> collection;

    public AggregateRepository(MongoDatabase database) {
        collection = database.getCollection("user");
    }

    public AggregateIterable<Document> maxAge(int gte, int lte) {
        Bson match = Aggregates.match(Filters.and(Filters.gte(AGE_FIELD, gte), Filters.lte(AGE_FIELD, lte)));
        Bson group = Aggregates.group('$' + ROLE_ID_FIELD, Accumulators.max("max_age", '$' + AGE_FIELD));
        return collection.aggregate(List.of(match, group));
    }

    public AggregateIterable<Document> minAge(int gte, int lte) {
        Bson match = Aggregates.match(Filters.and(Filters.gte(AGE_FIELD, gte), Filters.lte(AGE_FIELD, lte)));
        Bson group = Aggregates.group('$' + ROLE_ID_FIELD, Accumulators.min("min_age", '$' + AGE_FIELD));
        return collection.aggregate(List.of(match, group));
    }

    public AggregateIterable<Document> avgAge() {
        Bson group = Aggregates.group('$' + ROLE_ID_FIELD, Accumulators.avg("avg_age", '$' + AGE_FIELD));
        return collection.aggregate(List.of(group));
    }

    public AggregateIterable<Document> sumAge() {
        Bson group = Aggregates.group('$' + ROLE_ID_FIELD, Accumulators.sum("sum_age", '$' + AGE_FIELD));
        return collection.aggregate(List.of(group));
    }

    public AggregateIterable<Document> lookup() {
        Bson lookup = Aggregates.lookup("role", ROLE_ID_FIELD, ROLE_ID_FIELD, "role");
        Bson project = Aggregates.project(Projections.fields(Projections.excludeId(),
                                                             Projections.exclude(ROLE_ID_FIELD, "role._id")));
        return collection.aggregate(List.of(lookup, project));
    }
}
