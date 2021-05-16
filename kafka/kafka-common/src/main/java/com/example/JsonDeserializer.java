package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonDeserializer<T> implements Deserializer<T> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Class<T> valueType;

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return OBJECT_MAPPER.readValue(data, valueType);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
