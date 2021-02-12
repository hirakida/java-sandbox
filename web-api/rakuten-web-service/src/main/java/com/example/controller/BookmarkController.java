package com.example.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.client.RakutenApiClient;
import com.example.model.AddFavoriteBookmark;
import com.example.model.FavoriteBookmark.Item;
import com.example.model.Token;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
    private final RakutenApiClient client;
    private final Token token;

    @GetMapping
    public List<Item> getBookmarks() {
        return client.getBookmark(getAccessToken()).getItems();
    }

    @PostMapping
    public AddFavoriteBookmark addBookmark(@RequestBody @Validated BookmarkRequest request) {
        return client.addBookmark(getAccessToken(), request.getItemCode());
    }

    @DeleteMapping("/{bookmarkId}")
    public void deleteBookmark(@PathVariable String bookmarkId) {
        client.deleteBookmark(getAccessToken(), bookmarkId);
    }

    private String getAccessToken() {
        String accessToken = token.getAccessToken();
        if (accessToken == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return accessToken;
    }

    @Data
    public static class BookmarkRequest {
        @NotNull
        private String itemCode;
    }
}
