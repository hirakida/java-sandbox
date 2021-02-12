package com.example.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IchibaGenre {
    private List<Genre> parents;
    private Genre current;
    private List<Genre> children;
    private List<Genre> brothers;
    private List<TagGroup> tagGroups;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Genre {
        private int genreId;
        private String genreName;
        private int genreLevel;
    }
}
