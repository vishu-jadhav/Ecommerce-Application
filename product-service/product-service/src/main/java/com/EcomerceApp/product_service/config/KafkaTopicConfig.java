package com.EcomerceApp.product_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    public NewTopic ProductEventTopic() {
        return TopicBuilder.name("product-created-event")  // topic name
                .partitions(3)                            // 3 partitions
                .replicas(3)                              // 3 replicas for fault tolerance
                .configs(Map.of("min.insync.replicas","2")) // minimum 2 replicas must acknowledge writes
                .build();
    }
}
