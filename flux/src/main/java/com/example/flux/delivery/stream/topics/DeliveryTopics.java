package com.example.flux.delivery.stream.topics;

import com.example.flux.kafka.utils.KafkaUtils;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class DeliveryTopics {

    @Bean
    public NewTopic topicDeliveringState() {
        return TopicBuilder.name(KafkaUtils.ORDERS_DELIVERING)
                .partitions(10)
                .replicas(1)
                .build();
    }

}
