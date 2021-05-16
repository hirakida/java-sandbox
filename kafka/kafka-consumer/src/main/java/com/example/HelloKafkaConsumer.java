package com.example;

import static com.example.Constants.BOOTSTRAP_SERVERS;
import static com.example.Constants.HELLO_TOPIC_NAME;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class HelloKafkaConsumer implements Runnable {
    private final String groupId;

    @Override
    public void run() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        KafkaConsumer<String, Hello> consumer =
                new KafkaConsumer<>(properties, new StringDeserializer(), new JsonDeserializer<>(Hello.class));
        consumer.subscribe(List.of(HELLO_TOPIC_NAME));

        while (true) {
            ConsumerRecords<String, Hello> records = consumer.poll(Duration.ofMillis(1000));
            records.forEach(record -> {
                log.info("topic={} partition={} offset={} key={} value={}",
                         record.topic(), record.partition(), record.offset(), record.key(), record.value());

                TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
                OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(record.offset() + 1);
                consumer.commitSync(Map.of(topicPartition, offsetAndMetadata));
            });
        }
    }
}
