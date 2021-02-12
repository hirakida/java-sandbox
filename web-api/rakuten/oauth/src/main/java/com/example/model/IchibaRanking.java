package com.example.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IchibaRanking {
    private String title;
    private String lastBuildDate;
    @JsonProperty("Items")
    private List<RankingItem> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RankingItem {
        private int rank;
        private int carrier;
        private String itemName;
        private String catchcopy;
        private String itemCode;
        private String itemPrice;
        private String itemCaption;
        private String itemUrl;
        private String affiliateUrl;
        private int imageFlag;
        private List<String> smallImageUrls;
        private List<String> mediumImageUrls;
        private int availability;
        private int taxFlag;
        private int postageFlag;
        private int creditCardFlag;
        private int shopOfTheYearFlag;
        private int shipOverseasFlag;
        private String shipOverseasArea;
        private int asurakuFlag;
        private String asurakuClosingTime;
        private String asurakuArea;
        private String affiliateRate;
        private String startTime;
        private String endTime;
        private int reviewCount;
        private String reviewAverage;
        private int pointRate;
        private String pointRateStartTime;
        private String pointRateEndTime;
        private String shopName;
        private String shopCode;
        private String shopUrl;
        private String genreId;
    }
}
