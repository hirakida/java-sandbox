package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public void bulkIndex(@RequestParam(defaultValue = "10") int count) {
        companyService.bulkIndex(count);
    }

    @DeleteMapping
    public void deleteIndex() {
        companyService.deleteIndex();
    }

    @GetMapping("/{companyId}")
    public List<Company> search(@PathVariable long companyId) {
        return companyService.searchByTerm(companyId);
    }

    @GetMapping(params = "text")
    public List<Company> searchByMultiMatch(@RequestParam String text) {
        return companyService.searchByMultiMatch(text);
    }

    @GetMapping(params = "query=score")
    public List<Company> searchByScore(@RequestParam String text) {
        return companyService.searchByScore(text);
    }
}
