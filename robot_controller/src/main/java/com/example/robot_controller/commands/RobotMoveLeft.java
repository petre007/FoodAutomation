package com.example.robot_controller.commands;

import com.example.robot_controller.kafka.KafkaUtils;
import com.example.robot_controller.utils.RobotUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class RobotMoveLeft extends RobotAbstractMove implements RobotMove{

    public RobotMoveLeft(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public void makeMove() {
        log.info("Robot command: Left");
        super.kafkaTemplate.send(KafkaUtils.OUTPUT_DATA_PRODUCER,
                RobotUtils.MOVE_LEFT);
        super.kafkaTemplate.flush();
    }
}
