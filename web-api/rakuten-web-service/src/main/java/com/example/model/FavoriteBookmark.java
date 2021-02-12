package com.example.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FavoriteBookmark {
    private Summary summary;
    private List<Item> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Summary {
        private int count;
        private int hits;
        private int pageCount;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String bookmarkId;
        private String itemCode;
        private String productId;
        private String shopName;
        private String shopUrl;
        private String itemName;
        private String itemUrl;
        private String smallImageUrl;
        private String mediumImageUrl;
        private int reviewCount;
        private String reviewUrl;
        private int pointRate;
        private String reviewAverage;
        private int postageFlag;
        private int taxFlag;
        private String affiliateUrl;
    }
}
