package com.example.controller;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.client.OAuthClient;
import com.example.config.LineNotifyProperties;
import com.example.model.Token;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OAuthController {
    private final OAuthClient oAuthClient;
    private final LineNotifyProperties properties;
    private final OAuthSession session;

    @GetMapping("/")
    public String index(Model model) {
        if (properties.getAccessToken() != null) {
            session.setAccessToken(properties.getAccessToken());
        }
        model.addAttribute("accessToken", session.getAccessToken());
        return "index";
    }

    @GetMapping("/authorize")
    public String authorize() {
        if (session.getAccessToken() == null) {
            String state = UUID.randomUUID().toString();
            session.setState(state);
            return "redirect:" + oAuthClient.getAuthorizeUrl(state);
        }
        return "redirect:/";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code, @RequestParam String state) {
        if (state == null || !state.equals(session.getState())) {
            throw new ResponseStatusException(FORBIDDEN, String.format("Invalid state. %s %s",
                                                                       session.getState(), state));
        }

        Token token = oAuthClient.issueToken(code);
        session.setAccessToken(token.getAccessToken());
        return "redirect:/";
    }
}
