package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.model.StationLightResponse;
import com.example.model.StationResponse;

import reactor.core.publisher.Mono;

@Component
public class EkispertApiClient {
    private static final String API_ENDPOINT = "https://api.ekispert.jp";
    private final String accessKey;
    private final WebClient webClient;

    public EkispertApiClient(@Value("${app.access-key}") String accessKey) {
        this.accessKey = accessKey;
        webClient = WebClient.create(API_ENDPOINT);
    }

    public Mono<StationResponse> getStation(String name) {
        return webClient.get()
                        .uri(builder -> builder.path("/v1/json/station")
                                               .queryParam("key", accessKey)
                                               .queryParam("name", name)
                                               .queryParam("type", "train")
                                               .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchangeToMono(response -> response.bodyToMono(StationResponse.class));
    }

    public Mono<StationLightResponse> getStationLight(String name) {
        return webClient.get()
                        .uri(builder -> builder.path("/v1/json/station/light")
                                               .queryParam("key", accessKey)
                                               .queryParam("name", name)
                                               .queryParam("type", "train")
                                               .build())
                        .accept(MediaType.APPLICATION_JSON)
                        .exchangeToMono(response -> response.bodyToMono(StationLightResponse.class));
    }
}
