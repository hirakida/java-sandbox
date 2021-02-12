package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.client.OAuthClient;
import com.example.config.RwsConfig;
import com.example.config.RwsProperties;
import com.example.model.Token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final OAuthClient client;
    private final RwsProperties properties;
    private final Token token;

    @GetMapping("/")
    public String index() {
        if (token.getAccessToken() == null) {
            return "redirect:" + getAuthorizeUri();
        }
        return "index";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code) {
        Token token = client.getToken(code);
        log.info("{}", token);
        BeanUtils.copyProperties(token, this.token);
        return "redirect:/";
    }

    private String getAuthorizeUri() {
        return UriComponentsBuilder.fromHttpUrl(RwsConfig.RWS_URL)
                                   .path("/services/authorize")
                                   .queryParam("response_type", "code")
                                   .queryParam("client_id", properties.getApplicationId())
                                   .queryParam("redirect_uri", properties.getRedirectUri())
                                   .queryParam("scope",
                                               "rakuten_favoritebookmark_read,rakuten_favoritebookmark_update")
                                   .toUriString();
    }
}
