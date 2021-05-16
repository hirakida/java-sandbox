package com.example;

import static com.example.Constants.BOOTSTRAP_SERVERS;
import static com.example.Constants.HELLO_TOPIC_NAME;
import static com.example.Constants.STRING1_TOPIC_NAME;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        KafkaProducer<String, String> stringKafkaProducer = createStringKafkaProducer();
        KafkaProducer<String, Hello> helloKafkaProducer = createHelloKafkaProducer();
        CallbackImpl callback = new CallbackImpl();

        for (int i = 1; i <= 10; i++) {
            String key = String.valueOf(i);
            ProducerRecord<String, String> record1 = new ProducerRecord<>(STRING1_TOPIC_NAME, key, "Hello!");
            stringKafkaProducer.send(record1, callback);

            ProducerRecord<String, Hello> record2 =
                    new ProducerRecord<>(HELLO_TOPIC_NAME, key, new Hello(i, "Hello!"));
            helloKafkaProducer.send(record2, callback);
        }
    }

    private static KafkaProducer<String, String> createStringKafkaProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                               "org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                               "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(properties);
    }

    private static KafkaProducer<String, Hello> createHelloKafkaProducer() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        return new KafkaProducer<>(properties, new StringSerializer(), new JsonSerializer<>());
    }

    private static class CallbackImpl implements Callback {
        @Override
        public void onCompletion(RecordMetadata metadata, Exception e) {
            if (e != null) {
                log.error(e.getMessage(), e);
                return;
            }
            log.info("topic={} partition={} offset={} timestamp={}",
                     metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
        }
    }
}
