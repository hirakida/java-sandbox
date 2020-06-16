package com.example.service;

import static com.example.model.User.FIELD_SCORE;
import static com.example.model.User.FIELD_EMAIL;
import static com.example.model.User.FIELD_NAME;
import static com.example.model.User.FIELD_USER_ID;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Service;

import com.example.ObjectMapperWrapper;
import com.example.client.RestClientWrapper;
import com.example.model.Company;
import com.example.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String INDEX = "user";
    private final RestClientWrapper client;
    private final ObjectMapperWrapper objectMapper;

    public void bulkIndex(List<User> users) {
        if (client.indexExists(INDEX)) {
            client.deleteIndex(INDEX);
        }

        Map<String, String> sources = users.stream()
                                           .collect(toMap(user -> String.valueOf(user.getUserId()),
                                                          objectMapper::writeValueAsString));
        client.bulkIndex(INDEX, sources);
    }

    public void update(User user) {
        String source = objectMapper.writeValueAsString(user);
        client.update(INDEX, String.valueOf(user.getUserId()), source);
    }

    public List<User> searchByTerm(long userId) {
        SearchResponse response = client.searchByTerm(INDEX, FIELD_USER_ID, userId);
        return toUsers(response);
    }

    public List<User> searchByMultiMatch(String text) {
        SearchResponse response = client.searchByMultiMatch(INDEX, text, FIELD_USER_ID,
                                                            FIELD_NAME, FIELD_EMAIL);
        return toUsers(response);
    }

    public List<User> searchByScore() {
        SearchResponse response = client.searchByScore(INDEX, FIELD_SCORE);
        return toUsers(response);
    }

    public List<User> searchByScore(double score) {
        SearchResponse response = client.searchByScript(INDEX, FIELD_SCORE, String.valueOf(score));
        return toUsers(response);
    }

    private List<User> toUsers(SearchResponse response) {
        return Stream.of(response.getHits().getHits())
                     .map(hit -> objectMapper.readValue(hit.getSourceAsString(), User.class))
                     .collect(toList());
    }
}
