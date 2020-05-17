package com.example.web;

import static com.example.config.LineLoginConfig.LOGIN_BASE_URL;
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

import com.example.client.SocialApiClient;
import com.example.config.LineLoginProperties;
import com.example.model.AccessTokenVerify;
import com.example.model.Friendship;
import com.example.model.IdTokenPayload;
import com.example.model.Profile;
import com.example.model.Token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LineLoginController {
    private final LineLoginProperties properties;
    private final SocialApiClient socialApiClient;
    private final JwtHelper jwtHelper;
    private final LoginSession session;

    @GetMapping("/")
    public String index(ModelMap model) {
        if (session.getToken() == null || session.getToken().getAccessToken() == null) {
            String state = UUID.randomUUID().toString();
            String nonce = UUID.randomUUID().toString();
            session.setState(state);
            session.setNonce(nonce);
            return "redirect:" + getAuthorizeUrl(state, nonce);
        }

        String accessToken = session.getToken().getAccessToken();
        Profile profile = socialApiClient.getProfile(accessToken);
        Friendship friendship = socialApiClient.getFriendship(accessToken);
        AccessTokenVerify accessTokenVerify = socialApiClient.verifyAccessToken(accessToken);
        model.addAttribute("token", session.getToken());
        model.addAttribute("profile", profile);
        model.addAttribute("friendship", friendship);
        model.addAttribute("accessTokenVerify", accessTokenVerify);

        if (session.getToken().getIdToken() != null) {
            String idToken = session.getToken().getIdToken();
            IdTokenPayload payload1 = jwtHelper.getPayload(idToken, session.getNonce());
            IdTokenPayload payload2 = socialApiClient.verifyIdToken(idToken, session.getNonce(),
                                                                    profile.getUserId());
            model.addAttribute("payload1", payload1);
            model.addAttribute("payload2", payload2);
        }
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

        Token token = socialApiClient.issueToken(code);
        session.setToken(token);
        return "redirect:/";
    }

    @PostMapping("/refresh_token")
    public String refreshToken() {
        if (session.getToken() == null || session.getToken().getRefreshToken() == null) {
            throw new ResponseStatusException(FORBIDDEN, "RefreshToken not found");
        }

        Token token = socialApiClient.reissueAccessToken(session.getToken().getRefreshToken());
        token.setIdToken(session.getToken().getIdToken());
        session.setToken(token);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        if (session.getToken() != null && session.getToken().getAccessToken() != null) {
            socialApiClient.revokeAccessToken(session.getToken().getAccessToken());
        }
        httpSession.invalidate();
        return "redirect:/";
    }

    private String getAuthorizeUrl(String state, String nonce) {
        return UriComponentsBuilder.fromHttpUrl(LOGIN_BASE_URL)
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
