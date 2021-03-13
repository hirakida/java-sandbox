package com.example.service;

import static com.example.model.Company.FIELD_ADDRESS;
import static com.example.model.Company.FIELD_COMPANY_ID;
import static com.example.model.Company.FIELD_NAME;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.stereotype.Service;

import com.example.client.ElasticsearchClient;
import com.example.model.Company;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {
    private static final String INDEX = "company";
    private final AtomicInteger counter = new AtomicInteger(1);
    private final ElasticsearchClient client;
    private final ObjectMapperWrapper objectMapper;

    public void bulkIndex(int count) {
        List<String> sources = IntStream.rangeClosed(1, count)
                                        .mapToObj(i -> createCompany())
                                        .map(objectMapper::writeValueAsString)
                                        .collect(toList());
        client.bulkIndex(INDEX, sources);
    }

    public void deleteIndex() {
        if (client.indexExists(INDEX)) {
            client.deleteIndex(INDEX);
        }
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

    public List<Company> searchByScore(String text) {
        SearchResponse response = client.searchByFunctionScore(INDEX, FIELD_COMPANY_ID,
                                                               Map.of(FIELD_NAME, text, FIELD_ADDRESS, text));
        log.info("{}", response);
        return toCompanies(response);
    }

    private List<Company> toCompanies(SearchResponse response) {
        return Stream.of(response.getHits().getHits())
                     .map(hit -> objectMapper.readValue(hit.getSourceAsString(), Company.class))
                     .collect(toList());
    }

    private Company createCompany() {
        int id = counter.getAndIncrement();
        String name = "company" + id;
        String address = "address" + id + ' ' + id / 10 * 10;
        return new Company(id, name, address);
    }
}
