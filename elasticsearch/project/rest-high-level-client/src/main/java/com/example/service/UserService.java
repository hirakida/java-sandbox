package com.example.service;

import static com.example.model.User.FIELD_EMAIL;
import static com.example.model.User.FIELD_NAME;
import static com.example.model.User.FIELD_USER_ID;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Service;

import com.example.ObjectMapperWrapper;
import com.example.client.RestClientWrapper;
import com.example.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String INDEX = "user";
    private final RestClientWrapper client;
    private final ObjectMapperWrapper objectMapper;

    public void bulkIndex(List<User> companies) {
        if (client.indexExists(INDEX)) {
            client.deleteIndex(INDEX);
        }

        List<String> sources = companies.stream()
                                        .map(objectMapper::writeValueAsString)
                                        .collect(toList());
        client.bulkIndex(INDEX, sources);
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

    private List<User> toUsers(SearchResponse response) {
        return Stream.of(response.getHits().getHits())
                     .map(hit -> objectMapper.readValue(hit.getSourceAsString(), User.class))
                     .collect(toList());
    }
}
