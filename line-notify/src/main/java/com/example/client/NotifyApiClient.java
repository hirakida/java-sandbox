package com.example.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.model.NotifyResponse;
import com.example.model.RateLimit;
import com.example.model.Status;
import com.example.model.StatusResponse;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NotifyApiClient {
    private static final String NOTIFY_API_BASE_URL = "https://notify-api.line.me";
    private final RestTemplate restTemplate;

    public NotifyApiClient(RestTemplateBuilder builder) {
        restTemplate = builder.build();
    }

    public NotifyResponse notify(String accessToken, String message) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("message", message);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var request = new HttpEntity<>(body, headers);

        return restTemplate.postForObject(getUrl("/api/notify"), request, NotifyResponse.class);
    }

    public StatusResponse status(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        HttpEntity<Void> request = new HttpEntity<>(null, headers);

        ResponseEntity<Status> response = restTemplate.exchange(getUrl("/api/status"),
                                                                HttpMethod.GET, request, Status.class);
        Status status = response.getBody();
        RateLimit rateLimit = getRateLimit(response.getHeaders());
        log.info("getStatus: {} rateLimit: {}", status, rateLimit);
        return new StatusResponse(status, rateLimit);
    }

    public NotifyResponse revoke(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<Void> request = new HttpEntity<>(null, headers);

        return restTemplate.postForObject(getUrl("/api/revoke"), request, NotifyResponse.class);
    }

    private static String getUrl(String path) {
        return UriComponentsBuilder.fromHttpUrl(NOTIFY_API_BASE_URL)
                                   .path(path)
                                   .toUriString();
    }

    private static RateLimit getRateLimit(HttpHeaders headers) {
        RateLimit rateLimit = new RateLimit();
        rateLimit.setLimit(headers.getFirst("X-RateLimit-Limit"));
        rateLimit.setRemaining(headers.getFirst("X-RateLimit-Remaining"));
        rateLimit.setImageLimit(headers.getFirst("X-RateLimit-ImageLimit"));
        rateLimit.setImageRemaining(headers.getFirst("X-RateLimit-ImageRemaining"));
        rateLimit.setReset(headers.getFirst("X-RateLimit-Reset"));
        return rateLimit;
    }
}
