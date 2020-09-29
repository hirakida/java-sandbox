package com.example;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthorizationHelper {
    public static final String AUTHORIZATION_BASE_URL = "https://access.line.me";
    private final LineLoginProperties properties;

    public String getAuthorizationUrl(String state, String nonce) {
        return UriComponentsBuilder.fromHttpUrl(AUTHORIZATION_BASE_URL)
                                   .path("/oauth2/v2.1/authorize")
                                   .queryParam("response_type", "code")
                                   .queryParam("client_id", properties.getChannelId())
                                   .queryParam("redirect_uri", properties.getRedirectUri())
                                   .queryParam("state", state)
                                   .queryParam("scope", "profile openid")
                                   .queryParam("nonce", nonce)
//                                   .queryParam("prompt", "consent")
//                                   .queryParam("max_age", 60)
                                   .queryParam("bot_prompt", "normal")
                                   .toUriString();
    }
}
