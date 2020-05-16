package com.example.config;

import java.io.UncheckedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    private static final String CLUSTER_NAME = "es-docker-cluster";

    @SuppressWarnings("deprecation")
    @Bean
    public TransportClient transportClient() {
        Settings settings = Settings.builder()
                                    .put("cluster.name", CLUSTER_NAME)
                                    .build();
        TransportAddress transportAddress = new TransportAddress(getLocalHost(), 9300);

        return new PreBuiltTransportClient(settings)
                .addTransportAddress(transportAddress);
    }

    private static InetAddress getLocalHost() {
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new UncheckedIOException(e);
        }
    }
}
