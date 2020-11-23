package com.example;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC_NAME = "topic1";

    @Override
    public void run(String... args) throws Exception {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                               "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                               "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<Integer, String> producer = new KafkaProducer<>(properties);
        CallbackImpl callback = new CallbackImpl();
        for (int i = 1; i <= 100; i++) {
            ProducerRecord<Integer, String> record = new ProducerRecord<>(TOPIC_NAME, i, "value" + i);
            producer.send(record, callback);
        }

        producer.close();
    }

    private static class CallbackImpl implements Callback {
        @Override
        public void onCompletion(RecordMetadata metadata, Exception e) {
            if (metadata == null) {
                log.error(e.getMessage(), e);
                return;
            }
            log.info("topic={} partition={} offset={} timestamp={}",
                     metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
        }
    }
}
