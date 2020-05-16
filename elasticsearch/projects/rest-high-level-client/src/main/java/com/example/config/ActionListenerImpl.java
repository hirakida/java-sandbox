package com.example.config;

import org.elasticsearch.action.ActionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ActionListenerImpl<T> implements ActionListener<T> {

    @Override
    public void onResponse(T response) {
        log.info("onResponse: {}", response);
    }

    @Override
    public void onFailure(Exception e) {
        log.error("onFailure:", e);
    }
}
