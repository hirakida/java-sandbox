package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LiffController {
    private final LiffProperties properties;

    @GetMapping("/liff/{viewType:compact|tall|full}")
    public String index(@PathVariable String viewType, Model model) {
        model.addAttribute("liffId", properties.getLiffId(viewType));
        return "index";
    }
}
