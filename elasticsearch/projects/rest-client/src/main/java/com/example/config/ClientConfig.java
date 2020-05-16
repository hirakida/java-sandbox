package com.example.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public RestClient restClient() {
        HttpHost httpHost = new HttpHost("localhost", 9200);
        return RestClient.builder(httpHost)
                         .build();
    }

    @Bean
    public ResponseListenerImpl responseListener() {
        return new ResponseListenerImpl();
    }
}
