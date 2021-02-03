package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.StationLightResponse;
import com.example.model.StationResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class EkispertController {
    private final EkispertApiClient client;

    @GetMapping("/station")
    public Mono<StationResponse> station(@RequestParam String name) {
        return client.getStation(name);
    }

    @GetMapping("/station/light")
    public Mono<StationLightResponse> stationLight(@RequestParam String name) {
        return client.getStationLight(name);
    }
}
