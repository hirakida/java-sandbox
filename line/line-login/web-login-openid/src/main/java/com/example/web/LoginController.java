package com.example.web;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.web.client.SocialApiClient;
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
    private final OAuthHelper oAuthHelper;
    private final JwtHelper jwtHelper;
    private final LoginSession session;

    @GetMapping("/")
    public String index(ModelMap model) {
        if (session.getAccessToken() == null) {
            String state = UUID.randomUUID().toString();
            String nonce = UUID.randomUUID().toString();
            String codeVerifier = oAuthHelper.getCodeVerifier();
            String codeChallenge = oAuthHelper.getCodeChallenge(codeVerifier);
            session.setState(state);
            session.setNonce(nonce);
            session.setCodeVerifier(codeVerifier);
            return "redirect:" + oAuthHelper.getAuthorizationUrl(state, nonce, codeChallenge);
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

        AccessToken accessToken = socialApiClient.issueAccessToken(code, session.getCodeVerifier());
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
        httpSession.invalidate();
        return "redirect:/";
    }
}
