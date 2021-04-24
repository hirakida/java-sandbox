package com.example.web;

import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.web.config.LineLoginProperties;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuthHelper {
    private static final String AUTHORIZATION_BASE_URL = "https://access.line.me";
    private static final int CODE_VERIFIER_LENGTH = 43;
    private final LineLoginProperties properties;

    public String getAuthorizationUrl(String state, String codeChallenge) {
        return UriComponentsBuilder.fromHttpUrl(AUTHORIZATION_BASE_URL)
                                   .path("/oauth2/v2.1/authorize")
                                   .queryParam("response_type", "code")
                                   .queryParam("client_id", properties.getChannelId())
                                   .queryParam("redirect_uri", properties.getRedirectUri())
                                   .queryParam("state", state)
                                   .queryParam("scope", "profile")
                                   .queryParam("code_challenge", codeChallenge)
                                   .queryParam("code_challenge_method", "S256")
                                   .toUriString();
    }

    public String getCodeVerifier() {
        return RandomStringUtils.randomAlphabetic(CODE_VERIFIER_LENGTH);
    }

    public String getCodeChallenge(String codeVerifier) {
        byte[] digest = DigestUtils.sha256(codeVerifier);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }
}
