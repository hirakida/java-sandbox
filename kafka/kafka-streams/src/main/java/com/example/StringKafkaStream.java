package com.example;

import static com.example.Constants.BOOTSTRAP_SERVERS;
import static com.example.Constants.STRING1_TOPIC_NAME;
import static com.example.Constants.STRING2_TOPIC_NAME;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringKafkaStream implements Runnable {
    private static final String applicationId = "stream1";

    @Override
    public void run() {
        Properties properties = new Properties();
        properties.setProperty(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);
        properties.setProperty(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                               Serdes.String().getClass().getName());
        properties.setProperty(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                               Serdes.String().getClass().getName());

        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(STRING1_TOPIC_NAME)
               .peek((key, value) -> log.info("key={} value={}", key, value))
               .to(STRING2_TOPIC_NAME);

        KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        streams.start();
    }
}
