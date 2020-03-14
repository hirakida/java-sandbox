package com.example.config;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {
    public static final String DATABASE_NAME = "db1";

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
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
