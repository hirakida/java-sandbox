package com.example.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.config.RwsConfig;
import com.example.config.RwsProperties;
import com.example.model.AddFavoriteBookmark;
import com.example.model.DeleteFavoriteBookmark;
import com.example.model.FavoriteBookmark;
import com.example.model.IchibaRanking;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RakutenApiClient {
    private final RestTemplate restTemplate;
    private final RwsProperties properties;

    public FavoriteBookmark getBookmark(String accessToken) {
        String url = UriComponentsBuilder.fromHttpUrl(RwsConfig.RWS_URL)
                                         .path("/services/api/FavoriteBookmark/List/20170426")
                                         .queryParam("formatVersion", 2)
                                         .queryParam("hits", 30)
                                         .queryParam("access_token", accessToken)
                                         .toUriString();
        return restTemplate.getForObject(url, FavoriteBookmark.class);
    }

    public AddFavoriteBookmark addBookmark(String accessToken, String itemCode) {
        String url = UriComponentsBuilder.fromHttpUrl(RwsConfig.RWS_URL)
                                         .path("/services/api/FavoriteBookmark/Add/20120627")
                                         .queryParam("access_token", accessToken)
                                         .queryParam("itemCode", itemCode)
                                         .toUriString();
        return restTemplate.postForObject(url, null, AddFavoriteBookmark.class);
    }

    public DeleteFavoriteBookmark deleteBookmark(String accessToken, String bookmarkId) {
        String url = UriComponentsBuilder.fromHttpUrl(RwsConfig.RWS_URL)
                                         .path("/services/api/FavoriteBookmark/Delete/20120627")
                                         .queryParam("access_token", accessToken)
                                         .queryParam("bookmarkId", bookmarkId)
                                         .toUriString();
        return restTemplate.postForObject(url, null, DeleteFavoriteBookmark.class);
    }

    public IchibaRanking getIchibaRanking() {
        String url = UriComponentsBuilder.fromHttpUrl(RwsConfig.RWS_URL)
                                         .path("/services/api/IchibaItem/Ranking/20170628")
                                         .queryParam("formatVersion", 2)
                                         .queryParam("applicationId", properties.getApplicationId())
                                         .toUriString();
        return restTemplate.getForObject(url, IchibaRanking.class);
    }
}
