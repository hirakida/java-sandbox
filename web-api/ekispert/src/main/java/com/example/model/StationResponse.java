package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationResponse {
    @JsonProperty("ResultSet")
    private ResultSet<?> resultSet;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResultSet<T> {
        private String apiVersion;
        private String engineVersion;
        private String max;
        private String offset;
        @JsonProperty("Point")
        private T point;
    }
}
