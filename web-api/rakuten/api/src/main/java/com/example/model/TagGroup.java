package com.example.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagGroup {
    private List<Tag> tags;
    private int tagGroupId;
    private String tagGroupName;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Tag {
        private int parentTagId;
        private int tagId;
        private String tagName;
    }
}
