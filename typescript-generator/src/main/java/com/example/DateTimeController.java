package com.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.DateTime;

@RestController
public class DateTimeController {
    @GetMapping("/datetime")
    public DateTime getDateTime() {
        return new DateTime(LocalDate.now(), LocalDateTime.now(), ZonedDateTime.now());
    }
}
