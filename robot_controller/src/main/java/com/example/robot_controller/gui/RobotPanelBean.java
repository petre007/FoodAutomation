package com.example.robot_controller.gui;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RobotPanelBean {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Bean
    public void initRobotPanel() {
        new RobotPanel(kafkaTemplate);
    }

}
