package com.example.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.config.LineNotifyProperties;
import com.example.model.Token;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OAuthClient {
    private static final String NOTIFY_BOT_BASE_URL = "https://notify-bot.line.me";
    private final RestTemplate restTemplate;
    private final LineNotifyProperties properties;

    public OAuthClient(RestTemplateBuilder builder, LineNotifyProperties properties) {
        restTemplate = builder.build();
        this.properties = properties;
    }

    public String getAuthorizeUrl(String state) {
        return UriComponentsBuilder.fromHttpUrl(NOTIFY_BOT_BASE_URL)
                                   .path("/oauth/authorize")
                                   .queryParam("response_type", "code")
                                   .queryParam("client_id", properties.getClientId())
                                   .queryParam("redirect_uri", properties.getRedirectUri())
                                   .queryParam("scope", "notify")
                                   .queryParam("state", state)
                                   .toUriString();
    }

    public Token issueToken(String code) {
        String url = UriComponentsBuilder.fromHttpUrl(NOTIFY_BOT_BASE_URL)
                                         .path("/oauth/token")
                                         .toUriString();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", properties.getClientId());
        body.add("client_secret", properties.getClientSecret());
        body.add("redirect_uri", properties.getRedirectUri());
        body.add("code", code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var request = new HttpEntity<>(body, headers);

        Token token = restTemplate.postForObject(url, request, Token.class);
        log.info("token: {}", token);
        return token;
    }
}
