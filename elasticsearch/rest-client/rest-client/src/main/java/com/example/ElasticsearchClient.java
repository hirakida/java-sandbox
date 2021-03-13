package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
public class ElasticsearchClient {
    private final RestClient client;
    private final ResponseListener responseListener;
    private final ObjectMapper objectMapper;

    public ElasticsearchClient(@Value("${elasticsearch.host:localhost}") String host,
                               @Value("${elasticsearch.port:9200}") int port,
                               ObjectMapper objectMapper) {
        client = RestClient.builder(new HttpHost(host, port)).build();
        responseListener = new ResponseListenerImpl();
        this.objectMapper = objectMapper;
    }

    public JsonNode performRequest(String method, String endpoint) {
        Request request = new Request(method, endpoint);
        try {
            Response response = client.performRequest(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            return objectMapper.readValue(responseBody, JsonNode.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Cancellable performRequestAsync(String method, String endpoint) {
        Request request = new Request(method, endpoint);
        return client.performRequestAsync(request, responseListener);
    }

    @Slf4j
    private static class ResponseListenerImpl implements ResponseListener {
        @Override
        public void onSuccess(Response response) {
            log.info("onSuccess: {}", response);
        }

        @Override
        public void onFailure(Exception exception) {
            log.error("onFailure:", exception);
        }
    }
}
