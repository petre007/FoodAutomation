package com.example.flux.robot.stream.consumer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class RobotConsumer {

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("data_from_arduino")
                .partitions(10)
                .replicas(1)
                .build();
    }

    @KafkaListener(id = "myId", topics = "data_from_arduino")
    public void listen(String in) {
        System.out.println(in);
    }

}
