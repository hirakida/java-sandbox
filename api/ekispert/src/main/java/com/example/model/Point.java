package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Point {
    @JsonProperty("Station")
    private Station station;
    @JsonProperty("Prefecture")
    private Prefecture prefecture;
    @JsonProperty("GeoPoint")
    private GeoPoint geoPoint;
}
