package com.example;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PUT;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiController {
    private final ElasticsearchClient client;

    @GetMapping("/{path}")
    public JsonNode endpoint(@PathVariable String path) {
        return client.performRequest(GET.name(), path);
    }

    @GetMapping("/index/{index}")
    public JsonNode getIndex(@PathVariable String index) {
        return client.performRequest(GET.name(), index);
    }

    @PutMapping("/index/{index}")
    public void createIndex(@PathVariable String index) {
        client.performRequestAsync(PUT.name(), index);
    }

    @DeleteMapping("/index/{index}")
    public void deleteIndex(@PathVariable String index) {
        client.performRequestAsync(DELETE.name(), index);
    }
}
