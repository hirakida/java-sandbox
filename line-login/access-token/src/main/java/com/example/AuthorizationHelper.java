package com.example;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorizationHelper {
    private final LineLoginProperties properties;

    public String getAuthorizationUrl(String state) {
        return UriComponentsBuilder.fromHttpUrl("https://access.line.me")
                                   .path("/oauth2/v2.1/authorize")
                                   .queryParam("response_type", "code")
                                   .queryParam("client_id", properties.getChannelId())
                                   .queryParam("redirect_uri", properties.getRedirectUri())
                                   .queryParam("state", state)
                                   .queryParam("scope", "profile")
                                   .toUriString();
    }
}
