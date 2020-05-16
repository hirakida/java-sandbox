package com.example.client;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestClientWrapper {
    private final RestClient client;
    private final ResponseListener responseListener;
    private final ObjectMapper objectMapper;

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
}
