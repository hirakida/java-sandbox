package com.example.config;

import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseListenerImpl implements ResponseListener {

    @Override
    public void onSuccess(Response response) {
        log.info("onSuccess: {}", response);
    }

    @Override
    public void onFailure(Exception exception) {
        log.error("onFailure:", exception);
    }
}
