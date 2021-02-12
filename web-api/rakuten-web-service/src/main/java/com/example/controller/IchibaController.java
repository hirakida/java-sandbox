package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.client.RakutenApiClient;
import com.example.model.IchibaRanking;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ichiba")
@RequiredArgsConstructor
public class IchibaController {
    private final RakutenApiClient client;

    @GetMapping("/ranking")
    public IchibaRanking getIchibaRanking() {
        return client.getIchibaRanking();
    }
}
