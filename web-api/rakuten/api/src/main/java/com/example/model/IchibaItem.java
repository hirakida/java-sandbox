package com.example.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IchibaItem {
    private long count;
    private long page;
    private long first;
    private long last;
    private long hits;
    private long carrier;
    private long pageCount;
    @JsonProperty("Items")
    private List<Item> items;
    @JsonProperty("GenreInformation")
    private List<GenreInformation> genreInformation;
    @JsonProperty("TagInformation")
    private List<TagInformation> tagInformation;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String itemName;
        private String catchcopy;
        private String itemCode;
        private long itemPrice;
        private String itemCaption;
        private String itemUrl;
        private String shopUrl;
        private List<String> smallImageUrls;
        private List<String> mediumImageUrls;
        private String affiliateUrl;
        private String shopAffiliateUrl;
        private short imageFlag;
        private short availability;
        private short taxFlag;
        private short postageFlag;
        private short creditCardFlag;
        private short shopOfTheYearFlag;
        private short shipOverseasFlag;
        private String shipOverseasArea;
        private short asurakuFlag;
        private String asurakuClosingTime;
        private String asurakuArea;
        private long affiliateRate;
        private String startTime;
        private String endTime;
        private long reviewCount;
        private float reviewAverage;
        private long pointRate;
        private String pointRateStartTime;
        private String pointRateEndTime;
        private short giftFlag;
        private String shopName;
        private String shopCode;
        private String genreId;
        private List<Long> tagIds;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GenreInformation {
        private List<ParentGenre> parent;
        private List<Genre> current;
        private List<Genre> children;

        @Data
        public static class ParentGenre {
            private String genreId;
            private String genreName;
            private String genreLevel;
        }

        @Data
        public static class Genre {
            private String genreId;
            private String genreName;
            private String itemCount;
            private String genreLevel;
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TagInformation {
        private String tagGroupName;
        private long tagGroupId;
        private List<Tag> tags;

        @Data
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Tag {
            private long tagId;
            private String tagName;
            private long parentTagId;
            private long itemCount;
        }
    }
}
