package com.example;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlers {

    @ExceptionHandler
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.warn("{}", e.getMessage(), e);
        return new ResponseEntity<>(null, null, NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception e) {
        log.warn("{}", e.getMessage(), e);
        return new ResponseEntity<>(null, null, INTERNAL_SERVER_ERROR);
    }
}
