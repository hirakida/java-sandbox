package com.example.service;

import static com.example.model.User.FIELD_AGE;
import static com.example.model.User.FIELD_EMAIL;
import static com.example.model.User.FIELD_NAME;
import static com.example.model.User.FIELD_SCORE;
import static com.example.model.User.FIELD_USER_ID;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.example.client.ElasticsearchClient;
import com.example.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String INDEX = "user";
    private final AtomicInteger counter = new AtomicInteger(1);
    private final SecureRandom random = new SecureRandom();
    private final ElasticsearchClient client;
    private final ObjectMapperWrapper objectMapper;

    public void bulkIndex(int count) {
        Map<String, String> sources = IntStream.rangeClosed(1, count)
                                               .mapToObj(i -> createUser())
                                               .collect(toMap(user -> String.valueOf(user.getUserId()),
                                                              objectMapper::writeValueAsString));
        client.bulkIndex(INDEX, sources);
    }

    public void deleteIndex() {
        if (client.indexExists(INDEX)) {
            client.deleteIndex(INDEX);
        }
    }

    @Nullable
    public User get(String id) {
        GetResponse response = client.get(INDEX, id);
        if (response.isSourceEmpty()) {
            return null;
        }
        return objectMapper.readValue(response.getSourceAsString(), User.class);
    }

    public void update(long userId, double score) {
        SearchResponse response = client.searchByTerm(INDEX, FIELD_USER_ID, userId);
        List<User> users = toUsers(response);
        users.forEach(user -> {
            user.setScore(score);
            String source = objectMapper.writeValueAsString(user);
            client.update(INDEX, String.valueOf(user.getUserId()), source);
        });
    }

    public List<User> searchByMatchAll() {
        SearchResponse response = client.searchByMatchAll(INDEX, FIELD_USER_ID);
        return toUsers(response);
    }

    public List<User> searchByTerm(long userId) {
        SearchResponse response = client.searchByTerm(INDEX, FIELD_USER_ID, userId);
        return toUsers(response);
    }

    public List<User> searchByRange(long from, long to) {
        SearchResponse response = client.searchByRange(INDEX, FIELD_USER_ID, from, to);
        return toUsers(response);
    }

    public List<User> searchByBool(String text) {
        SearchResponse response = client.searchByBool(INDEX, Map.of(FIELD_NAME, text, FIELD_EMAIL, text));
        return toUsers(response);
    }

    public List<User> searchByMultiMatch(String text) {
        SearchResponse response = client.searchByMultiMatch(INDEX, text, FIELD_USER_ID,
                                                            FIELD_NAME, FIELD_EMAIL);
        return toUsers(response);
    }

    public List<User> searchByScore() {
        SearchResponse response = client.searchByFunctionScore(INDEX, FIELD_SCORE);
        return toUsers(response);
    }

    public List<User> searchByScript(int age) {
        SearchResponse response = client.searchByScript(INDEX, FIELD_AGE, String.valueOf(age));
        return toUsers(response);
    }

    private User createUser() {
        int id = counter.getAndIncrement();
        String name = "user" + id;
        String email = name + "@example.com";
        int age = id % 100;
        double score = random.nextDouble();
        return new User(id, name, email, age, score);
    }

    private List<User> toUsers(SearchResponse response) {
        return Stream.of(response.getHits().getHits())
                     .map(hit -> objectMapper.readValue(hit.getSourceAsString(), User.class))
                     .collect(toList());
    }
}
