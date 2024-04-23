package com.example.robot_controller.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public abstract class RobotAbstractMove {

    protected final KafkaTemplate<String, String> kafkaTemplate;

}
