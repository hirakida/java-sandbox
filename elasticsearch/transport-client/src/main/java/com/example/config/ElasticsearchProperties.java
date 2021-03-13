package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticsearchProperties {
    private String clusterName;
    private String host;
}
