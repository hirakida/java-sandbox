package com.example;

import static com.example.Constants.BOOTSTRAP_SERVERS;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class StringKafkaConsumer implements Runnable {
    private final String topic;
    private final String groupId;
    private final boolean autoCommit;
    private final boolean seekToBeginning;

    @Override
    public void run() {
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                               "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                               "org.apache.kafka.common.serialization.StringDeserializer");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, String.valueOf(autoCommit));
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        if (seekToBeginning) {
            List<TopicPartition> partitions = new ArrayList<>();
            for (PartitionInfo partitionInfo : consumer.partitionsFor(topic)) {
                partitions.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
            }
            consumer.assign(partitions);
            consumer.seekToBeginning(consumer.assignment());
        } else {
            consumer.subscribe(List.of(topic));
        }

        while (true) {
            if (autoCommit) {
                autoCommit(consumer);
            } else {
                manualCommit(consumer);
            }
        }
    }

    private void autoCommit(KafkaConsumer<String, String> consumer) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        records.forEach(record -> {
            log.info("group={} topic={} partition={} offset={} key={} value={}",
                     groupId, record.topic(), record.partition(), record.offset(), record.key(),
                     record.value());
        });
    }

    private void manualCommit(KafkaConsumer<String, String> consumer) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
        for (TopicPartition partition : records.partitions()) {
            List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
            for (ConsumerRecord<String, String> record : partitionRecords) {
                log.info("group={} topic={} partition={} offset={} key={} value={}",
                         groupId, record.topic(), record.partition(), record.offset(), record.key(),
                         record.value());
            }

            long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
            OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(lastOffset + 1);
            consumer.commitSync(Map.of(partition, offsetAndMetadata));
        }
    }
}
