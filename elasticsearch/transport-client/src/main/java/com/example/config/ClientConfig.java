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
    @SuppressWarnings("deprecation")
    @Bean
    public TransportClient transportClient(ElasticsearchProperties properties) {
        Settings settings = Settings.builder()
                                    .put("cluster.name", properties.getClusterName())
                                    .build();

        InetAddress address;
        try {
            address = InetAddress.getByName(properties.getHost());
        } catch (UnknownHostException e) {
            throw new UncheckedIOException(e);
        }
        TransportAddress transportAddress = new TransportAddress(address, 9300);

        return new PreBuiltTransportClient(settings)
                .addTransportAddress(transportAddress);
    }
}
