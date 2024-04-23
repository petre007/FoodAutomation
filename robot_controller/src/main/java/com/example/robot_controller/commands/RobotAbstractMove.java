package com.example.robot_controller.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public abstract class RobotAbstractMove {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public final void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
