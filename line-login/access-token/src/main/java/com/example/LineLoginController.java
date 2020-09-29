package com.example;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.example.client.SocialApiClient;
import com.example.model.AccessToken;
import com.example.model.AccessTokenVerify;
import com.example.model.Friendship;
import com.example.model.Profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LineLoginController {
    private final AuthorizationHelper authorizationHelper;
    private final SocialApiClient socialApiClient;
    private final LineLoginSession session;

    @GetMapping("/")
    public String index(ModelMap model) {
        if (session.getAccessToken() == null) {
            String state = UUID.randomUUID().toString();
            session.setState(state);
            return "redirect:" + authorizationHelper.getAuthorizationUrl(state);
        }

        AccessTokenVerify verify = socialApiClient.verifyAccessToken(session.getAccessToken());
        log.info("{}", verify);

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
        if (session.getAccessToken() == null) {
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
}
