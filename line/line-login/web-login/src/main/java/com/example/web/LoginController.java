package com.example.web;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.web.config.LineLoginProperties;
import com.example.web.model.AccessToken;
import com.example.web.model.Friendship;
import com.example.web.model.LoginSession;
import com.example.web.model.Profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final SocialApiClient socialApiClient;
    private final LineLoginProperties properties;
    private final LoginSession session;

    @GetMapping("/")
    public String index(ModelMap model) {
        if (session.getAccessToken() == null) {
            String state = UUID.randomUUID().toString();
            session.setState(state);
            return "redirect:" + getAuthorizationUrl(state);
        }

        Profile profile = socialApiClient.getProfile(session.getAccessToken());
        Friendship friendship = socialApiClient.getFriendship(session.getAccessToken());
        model.addAttribute("accessToken", session.getAccessToken());
        model.addAttribute("refreshToken", session.getRefreshToken());
        model.addAttribute("profile", profile);
        model.addAttribute("friendship", friendship);
        return "index";
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code, @RequestParam String state,
                           @RequestParam(required = false) boolean friendship_status_changed) {
        log.info("code={} state={} friendship_status_changed={}", code, state, friendship_status_changed);
        if (state == null || !state.equals(session.getState())) {
            throw new ResponseStatusException(FORBIDDEN, String.format("Invalid state. %s, %s",
                                                                       session.getState(), state));
        }

        AccessToken accessToken = socialApiClient.issueAccessToken(code);
        log.info("{}", accessToken);
        session.setAccessToken(accessToken.getAccessToken());
        session.setRefreshToken(accessToken.getRefreshToken());
        return "redirect:/";
    }

    @PostMapping("/refresh_token")
    public String refreshToken() {
        if (session.getRefreshToken() == null) {
            throw new ResponseStatusException(FORBIDDEN, "RefreshToken not found");
        }

        AccessToken accessToken = socialApiClient.reissueAccessToken(session.getRefreshToken());
        log.info("{}", accessToken);
        session.setAccessToken(accessToken.getAccessToken());
        session.setRefreshToken(accessToken.getRefreshToken());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        if (session.getAccessToken() != null) {
            socialApiClient.revokeAccessToken(session.getAccessToken());
        }
        httpSession.invalidate();
        return "redirect:/";
    }

    private String getAuthorizationUrl(String state) {
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
