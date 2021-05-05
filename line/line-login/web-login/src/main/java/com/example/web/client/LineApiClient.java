package com.example.web.client;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.web.config.LineLoginProperties;
import com.example.web.model.AccessToken;
import com.example.web.model.AccessTokenVerify;
import com.example.web.model.Friendship;
import com.example.web.model.Profile;

@Component
public class LineApiClient {
    private static final String BASE_URL = "https://api.line.me";
    private final LineLoginProperties properties;
    private final RestTemplate restTemplate;

    public LineApiClient(LineLoginProperties properties, RestTemplateBuilder builder) {
        this.properties = properties;
        restTemplate = builder.setConnectTimeout(Duration.ofSeconds(5))
                              .setReadTimeout(Duration.ofSeconds(5))
                              .build();
    }

    /**
     * OAuth
     */
    public AccessToken issueAccessToken(String code, String codeVerifier) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                                         .path("/oauth2/v2.1/token")
                                         .toUriString();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", properties.getChannelId());
        body.add("client_secret", properties.getChannelSecret());
        body.add("redirect_uri", properties.getRedirectUri());
        body.add("code", code);
        body.add("code_verifier", codeVerifier);
        var request = new HttpEntity<>(body, getHeaders());

        return restTemplate.postForObject(url, request, AccessToken.class);
    }

    public AccessToken reissueAccessToken(String refreshToken) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                                         .path("/oauth2/v2.1/token")
                                         .toUriString();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "refresh_token");
        body.add("client_id", properties.getChannelId());
        body.add("client_secret", properties.getChannelSecret());
        body.add("refresh_token", refreshToken);
        var request = new HttpEntity<>(body, getHeaders());

        return restTemplate.postForObject(url, request, AccessToken.class);
    }

    public AccessTokenVerify verifyAccessToken(String accessToken) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                                         .path("/oauth2/v2.1/verify")
                                         .queryParam("access_token", accessToken)
                                         .toUriString();
        return restTemplate.getForObject(url, AccessTokenVerify.class);
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

    /**
     * Profile
     */
    public Profile getProfile(String accessToken) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                                         .path("/v2/profile")
                                         .toUriString();
        var request = new HttpEntity<>(getHeaders(accessToken));

        return restTemplate.exchange(url, HttpMethod.GET, request, Profile.class)
                           .getBody();
    }

    /**
     * Friendship
     */
    public Friendship getFriendship(String accessToken) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                                         .path("/friendship/v1/status")
                                         .toUriString();
        var request = new HttpEntity<>(getHeaders(accessToken));

        return restTemplate.exchange(url, HttpMethod.GET, request, Friendship.class)
                           .getBody();
    }

    private static HttpHeaders getHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        return headers;
    }

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
