package com.example.flux.robot.stream.topics;

import com.example.flux.kafka.utils.KafkaUtils;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class RobotTopics {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name(KafkaUtils.ULTRASONIC_DATA_CONSUMER)
                .partitions(10)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name(KafkaUtils.ULTRASONIC_DATA_PRODUCER)
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topic3() {
        return TopicBuilder.name(KafkaUtils.ESP32_DATA_CONSUMER)
                .partitions(10)
                .replicas(2)
                .build();
    }

    @Bean
    public NewTopic topic4() {
        return TopicBuilder.name(KafkaUtils.ESP32_DATA_PRODUCER)
                .partitions(10)
                .replicas(1)
                .build();
    }


}
