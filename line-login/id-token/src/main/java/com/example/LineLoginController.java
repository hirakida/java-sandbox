package com.example;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.client.SocialApiClient;
import com.example.model.AccessToken;
import com.example.model.IdTokenPayload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LineLoginController {
    private final SocialApiClient socialApiClient;
    private final AuthorizationHelper authorizationHelper;
    private final JwtHelper jwtHelper;
    private final LineLoginSession session;

    @GetMapping("/")
    public String index(ModelMap model) {
        if (session.getAccessToken() == null) {
            String state = UUID.randomUUID().toString();
            String nonce = UUID.randomUUID().toString();
            session.setState(state);
            session.setNonce(nonce);
            return "redirect:" + authorizationHelper.getAuthorizationUrl(state, nonce);
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
        String userId = jwtHelper.getSubject(accessToken.getIdToken());
        IdTokenPayload payload = socialApiClient.verifyIdToken(accessToken.getIdToken(),
                                                               session.getNonce(),
                                                               userId);
        log.info("{}", payload);
        log.info("{}", jwtHelper.getPayload(accessToken.getIdToken()));

        session.setAccessToken(accessToken.getAccessToken());
        session.setPayload(payload);
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
}
