package com.example.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.config.RwsConfig;
import com.example.config.RwsProperties;
import com.example.model.Token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthClient {
    private final RestTemplate restTemplate;
    private final RwsProperties properties;

    public Token getToken(String code) {
        String url = UriComponentsBuilder.fromHttpUrl(RwsConfig.RWS_URL)
                                         .path("/services/token")
                                         .toUriString();
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", properties.getApplicationId());
        body.add("client_secret", properties.getApplicationSecret());
        body.add("code", code);
        body.add("redirect_uri", properties.getRedirectUri());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return restTemplate.postForObject(url, new HttpEntity<>(body, headers), Token.class);
    }
}
