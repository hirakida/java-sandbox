package com.example.controller;

import javax.validation.constraints.NotEmpty;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.client.NotifyApiClient;
import com.example.model.NotifyResponse;
import com.example.model.StatusResponse;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NotifyApiController {
    private final NotifyApiClient notifyApiClient;
    private final OAuthSession session;

    @PostMapping("/notify")
    public NotifyResponse notify(@RequestBody @Validated NotifyRequest request) {
        return notifyApiClient.notify(session.getAccessToken(), request.getMessage());
    }

    @GetMapping("/status")
    public StatusResponse status() {
        return notifyApiClient.status(session.getAccessToken());
    }

    @Data
    public static class NotifyRequest {
        @NotEmpty
        private String message;
    }
}
