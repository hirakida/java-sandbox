package com.example;

import static com.example.User.FIELD_EMAIL;
import static com.example.User.FIELD_NAME;
import static com.example.User.FIELD_SCORE;
import static com.example.User.FIELD_USER_ID;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String INDEX = "user";
    private final AtomicInteger counter = new AtomicInteger(1);
    private final SecureRandom random = new SecureRandom();
    private final ElasticsearchClient client;
    private final ObjectMapper objectMapper;

    public void bulkIndex(int count) {
        List<String> sources = IntStream.rangeClosed(1, count)
                                        .mapToObj(i -> createUser())
                                        .map(this::writeValueAsString)
                                        .collect(toList());
        client.bulkIndex(INDEX, sources);
    }

    public void deleteIndex() {
        if (client.indexExists(INDEX)) {
            client.deleteIndex(INDEX);
        }
    }

    public List<User> searchByText(String text) {
        SearchResponse response = client.searchByMultiMatch(INDEX, text, FIELD_USER_ID,
                                                            FIELD_NAME, FIELD_EMAIL);
        return toUsers(response);
    }

    public List<User> searchByScore() {
        SearchResponse response = client.searchByScore(INDEX, FIELD_SCORE);
        return toUsers(response);
    }

    private User createUser() {
        int id = counter.getAndIncrement();
        String name = "user" + id;
        String email = name + "@example.com";
        double score = random.nextDouble();
        return new User(id, name, email, score);
    }

    private List<User> toUsers(SearchResponse response) {
        return Stream.of(response.getHits().getHits())
                     .map(hit -> readValue(hit.getSourceAsString()))
                     .collect(toList());
    }

    private User readValue(String content) {
        try {
            return objectMapper.readValue(content, User.class);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private String writeValueAsString(User value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
