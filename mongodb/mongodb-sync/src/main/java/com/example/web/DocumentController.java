package com.example.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.repository.DocumentRepository;
import com.mongodb.client.FindIterable;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentRepository repository;

    @GetMapping("/documents")
    public List<Document> findAll() {
        return toList(repository.find());
    }

    @GetMapping("/documents/{key}")
    public List<Document> findById(@PathVariable long key) {
        return toList(repository.find(key));
    }

    @DeleteMapping("/documents/{key}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long key) {
        repository.deleteOne(key);
    }

    private static List<Document> toList(FindIterable<Document> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                            .collect(Collectors.toList());
    }
}
