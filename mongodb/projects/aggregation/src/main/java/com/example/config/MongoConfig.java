package com.example.config;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

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
        return MongoClients.create(CONNECTION_STRING);
    }

    @Bean
    public CodecRegistry codecRegistry() {
        PojoCodecProvider provider = PojoCodecProvider.builder()
                                                      .automatic(true)
                                                      .build();
        return CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                                              CodecRegistries.fromProviders(provider));
    }
}
