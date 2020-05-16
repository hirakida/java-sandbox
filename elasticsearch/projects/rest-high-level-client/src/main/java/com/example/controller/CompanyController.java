package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Company;
import com.example.service.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/{companyId}")
    public List<Company> search(@PathVariable long companyId) {
        return companyService.searchByTerm(companyId);
    }

    @GetMapping(params = "text")
    public List<Company> searchByMultiMatch(@RequestParam String text) {
        return companyService.searchByMultiMatch(text);
    }

    @GetMapping(params = { "from", "to" })
    public List<Company> searchByRange(@RequestParam long from, @RequestParam long to) {
        return companyService.searchByRange(from, to);
    }

    @GetMapping(params = { "query=bool", "text" })
    public List<Company> searchByBool(@RequestParam String text) {
        return companyService.searchByBool(text);
    }

    @GetMapping(params = "query=score")
    public List<Company> searchByScore() {
        return companyService.searchByScore();
    }
}
