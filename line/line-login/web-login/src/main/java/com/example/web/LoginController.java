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

import com.example.web.client.LineApiClient;
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
    private final LineApiClient lineApiClient;
    private final OAuthHelper oAuthHelper;
    private final LoginSession session;

    @GetMapping("/")
    public String index(ModelMap model) {
        if (session.getAccessToken() == null) {
            String state = UUID.randomUUID().toString();
            String codeVerifier = oAuthHelper.getCodeVerifier();
            String codeChallenge = oAuthHelper.getCodeChallenge(codeVerifier);
            session.setState(state);
            session.setCodeVerifier(codeVerifier);
            return "redirect:" + oAuthHelper.getAuthorizationUrl(state, codeChallenge);
        }

        Profile profile = lineApiClient.getProfile(session.getAccessToken());
        Friendship friendship = lineApiClient.getFriendship(session.getAccessToken());
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

        AccessToken accessToken = lineApiClient.issueAccessToken(code, session.getCodeVerifier());
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

        AccessToken accessToken = lineApiClient.reissueAccessToken(session.getRefreshToken());
        log.info("{}", accessToken);
        session.setAccessToken(accessToken.getAccessToken());
        session.setRefreshToken(accessToken.getRefreshToken());
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        if (session.getAccessToken() != null) {
            lineApiClient.revokeAccessToken(session.getAccessToken());
        }
        httpSession.invalidate();
        return "redirect:/";
    }
}
