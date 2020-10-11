package com.example.web;

import static com.example.web.config.LineLoginConfig.AUTHORIZATION_BASE_URL;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.web.config.LineLoginProperties;
import com.example.web.model.AccessToken;
import com.example.web.model.IdTokenPayload;
import com.example.web.model.LoginSession;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final SocialApiClient socialApiClient;
    private final LineLoginProperties properties;
    private final JwtHelper jwtHelper;
    private final LoginSession session;

    @GetMapping("/")
    public String index(ModelMap model) {
        if (session.getAccessToken() == null) {
            String state = UUID.randomUUID().toString();
            String nonce = UUID.randomUUID().toString();
            session.setState(state);
            session.setNonce(nonce);
            return "redirect:" + getAuthorizationUrl(state, nonce);
        }

        IdTokenPayload payload = session.getPayload();
        model.addAttribute("accessToken", session.getAccessToken());
        model.addAttribute("sub", payload.getSub());
        model.addAttribute("name", payload.getName());
        model.addAttribute("picture", payload.getPicture());
        model.addAttribute("payload", payload);
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
        IdTokenPayload payload = jwtHelper.getPayload(accessToken.getIdToken(), session.getNonce());
        log.info("{}", payload);
        session.setAccessToken(accessToken.getAccessToken());
        session.setPayload(payload);

        IdTokenPayload verified = socialApiClient.verifyIdToken(accessToken.getIdToken(), session.getNonce());
        log.info("{}", verified);

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

    private String getAuthorizationUrl(String state, String nonce) {
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
