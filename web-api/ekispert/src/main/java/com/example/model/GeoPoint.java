package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoPoint {
    private String longi;
    private String lati;
    @JsonProperty("longi_d")
    private String longiD;
    @JsonProperty("lati_d")
    private String latiD;
    private String gcs;
}
