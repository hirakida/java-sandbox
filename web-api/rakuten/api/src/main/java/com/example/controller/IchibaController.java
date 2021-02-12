package com.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.client.RakutenApiClient;
import com.example.model.IchibaGenre;
import com.example.model.IchibaItem;
import com.example.model.IchibaRanking;
import com.example.model.IchibaTag;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ichiba")
@RequiredArgsConstructor
public class IchibaController {
    private final RakutenApiClient client;

    @GetMapping("/ranking")
    public IchibaRanking getIchibaRanking() {
        return client.getIchibaRanking();
    }

    @GetMapping("/item")
    public IchibaItem getIchibaItem(@RequestParam String keyword, @RequestParam int genreId) {
        return client.getIchibaItem(keyword, genreId);
    }

    @GetMapping("/genres/{genreId}")
    public IchibaGenre getIchibaGenre(@PathVariable int genreId) {
        return client.getIchibaGenre(genreId);
    }

    @GetMapping("/tags/{tagId}")
    public IchibaTag getIchibaTag(@PathVariable int tagId) {
        return client.getIchibaTag(tagId);
    }
}
