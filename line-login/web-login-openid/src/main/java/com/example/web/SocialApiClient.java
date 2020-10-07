package com.example.web;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.web.config.LineLoginProperties;
import com.example.web.model.AccessToken;
import com.example.web.model.IdTokenPayload;

@Component
public class SocialApiClient {
    private static final String BASE_URL = "https://api.line.me";
    private final LineLoginProperties properties;
    private final RestTemplate restTemplate;

    SocialApiClient(LineLoginProperties properties, RestTemplateBuilder builder) {
        this.properties = properties;
        restTemplate = builder.build();
    }

    public AccessToken issueAccessToken(String code) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                                         .path("/oauth2/v2.1/token")
                                         .toUriString();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", properties.getChannelId());
        body.add("client_secret", properties.getChannelSecret());
        body.add("redirect_uri", properties.getRedirectUri());
        body.add("code", code);
        var request = new HttpEntity<>(body, getHeaders());

        return restTemplate.postForObject(url, request, AccessToken.class);
    }

    public void revokeAccessToken(String accessToken) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                                         .path("/oauth2/v2.1/revoke")
                                         .toUriString();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("access_token", accessToken);
        body.add("client_id", properties.getChannelId());
        body.add("client_secret", properties.getChannelSecret());
        var request = new HttpEntity<>(body, getHeaders());

        restTemplate.postForObject(url, request, Void.class);
    }

    public IdTokenPayload verifyIdToken(String idToken, String nonce) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                                         .path("/oauth2/v2.1/verify")
                                         .toUriString();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("id_token", idToken);
        body.add("client_id", properties.getChannelId());
        body.add("nonce", nonce);
        var request = new HttpEntity<>(body, getHeaders());

        return restTemplate.postForObject(url, request, IdTokenPayload.class);
    }

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
