package com.example.config;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

@Configuration
public class MongoConfig {
    private static final String CONNECTION_STRING = "mongodb://user1:pwd1@localhost:27017/?authSource=admin";
    private static final String DATABASE_NAME = "db1";

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    @Bean
    public MongoClient mongoClient() {
        PojoCodecProvider provider = PojoCodecProvider.builder()
                                                      .automatic(true)
                                                      .build();
        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                                               CodecRegistries.fromProviders(provider));

        MongoClientSettings settings =
                MongoClientSettings.builder()
                                   .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                                   .codecRegistry(codecRegistry)
                                   .build();
        return MongoClients.create(settings);
    }
}
