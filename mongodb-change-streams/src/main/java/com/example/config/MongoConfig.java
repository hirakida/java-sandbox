package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {
    private static final String URI =
            "mongodb://127.0.0.1:27017,127.0.0.1:27018,127.0.0.1:27019/?replicaSet=rs1";

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(URI);
    }
}
