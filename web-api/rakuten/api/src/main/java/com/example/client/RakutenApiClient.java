package com.example.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.model.IchibaGenre;
import com.example.model.IchibaItem;
import com.example.model.IchibaRanking;
import com.example.model.IchibaTag;

@Component
public class RakutenApiClient {
    private static final String URL = "https://app.rakuten.co.jp";
    private final RestTemplate restTemplate;
    private final String applicationId;

    public RakutenApiClient(RestTemplateBuilder builder,
                            @Value("${application-id}") String applicationId) {
        restTemplate = builder.build();
        this.applicationId = applicationId;
    }

    public IchibaRanking getIchibaRanking() {
        String url = UriComponentsBuilder.fromHttpUrl(URL)
                                         .path("/services/api/IchibaItem/Ranking/20170628")
                                         .queryParam("formatVersion", 2)
                                         .queryParam("applicationId", applicationId)
                                         .toUriString();
        return restTemplate.getForObject(url, IchibaRanking.class);
    }

    public IchibaItem getIchibaItem(String keyword, int genreId) {
        String url = UriComponentsBuilder.fromHttpUrl(URL)
                                         .path("/services/api/IchibaItem/Search/20170706")
                                         .queryParam("formatVersion", 2)
                                         .queryParam("applicationId", applicationId)
                                         .queryParam("keyword", "{keyword}")
                                         .queryParam("genreId", genreId)
                                         .build(false)
                                         .toUriString();
        return restTemplate.getForObject(url, IchibaItem.class, keyword);
    }

    public IchibaGenre getIchibaGenre(int genreId) {
        String url = UriComponentsBuilder.fromHttpUrl(URL)
                                         .path("/services/api/IchibaGenre/Search/20140222")
                                         .queryParam("formatVersion", 2)
                                         .queryParam("applicationId", applicationId)
                                         .queryParam("genreId", genreId)
                                         .toUriString();
        return restTemplate.getForObject(url, IchibaGenre.class);
    }

    public IchibaTag getIchibaTag(int tagId) {
        String url = UriComponentsBuilder.fromHttpUrl(URL)
                                         .path("/services/api/IchibaTag/Search/20140222")
                                         .queryParam("formatVersion", 2)
                                         .queryParam("applicationId", applicationId)
                                         .queryParam("tagId", tagId)
                                         .toUriString();
        return restTemplate.getForObject(url, IchibaTag.class);
    }
}
