package com.example.service;

import static com.example.model.Company.FIELD_ADDRESS;
import static com.example.model.Company.FIELD_COMPANY_ID;
import static com.example.model.Company.FIELD_NAME;
import static com.example.model.Company.FIELD_SCORE;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.example.ObjectMapperWrapper;
import com.example.client.RestClientWrapper;
import com.example.model.Company;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private static final String INDEX = "company";
    private final RestClientWrapper client;
    private final ObjectMapperWrapper objectMapper;

    public void bulkIndex(List<Company> companies) {
        if (client.indexExists(INDEX)) {
            client.deleteIndex(INDEX);
        }

        List<String> sources = companies.stream()
                                        .map(objectMapper::writeValueAsString)
                                        .collect(toList());
        client.bulkIndex(INDEX, sources);
    }

    @Nullable
    public Company get(String id) {
        GetResponse response = client.get(INDEX, id);
        if (response.isSourceEmpty()) {
            return null;
        }
        return objectMapper.readValue(response.getSourceAsString(), Company.class);
    }

    public List<Company> searchByTerm(long companyId) {
        SearchResponse response = client.searchByTerm(INDEX, FIELD_COMPANY_ID, companyId);
        return toCompanies(response);
    }

    public List<Company> searchByMultiMatch(String text) {
        SearchResponse response = client.searchByMultiMatch(INDEX, text, FIELD_COMPANY_ID, FIELD_NAME,
                                                            FIELD_ADDRESS);
        return toCompanies(response);
    }

    public List<Company> searchByRange(long from, long to) {
        SearchResponse response = client.searchByRange(INDEX, FIELD_COMPANY_ID, from, to);
        return toCompanies(response);
    }

    public List<Company> searchByBool(String text) {
        SearchResponse response = client.searchByBool(INDEX, text, FIELD_NAME, FIELD_ADDRESS);
        return toCompanies(response);
    }

    public List<Company> searchByScore() {
        SearchResponse response = client.searchByScore(INDEX, FIELD_SCORE);
        return toCompanies(response);
    }

    private List<Company> toCompanies(SearchResponse response) {
        return Stream.of(response.getHits().getHits())
                     .map(hit -> objectMapper.readValue(hit.getSourceAsString(), Company.class))
                     .collect(toList());
    }
}
