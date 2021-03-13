package com.example.config;

import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient(@Value("${elasticsearch.host:localhost}") String host,
                                                   @Value("${elasticsearch.port:9200}") int port) {
        HttpHost httpHost = new HttpHost(host, port);
        RestClientBuilder builder = RestClient.builder(httpHost);
        return new RestHighLevelClient(builder);
    }

    @Bean
    public ActionListener<IndexResponse> actionListener() {
        return new ActionListenerImpl<>();
    }
}
