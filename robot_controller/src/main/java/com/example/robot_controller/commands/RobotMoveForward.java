package com.example.robot_controller.commands;

import com.example.robot_controller.kafka.KafkaUtils;
import com.example.robot_controller.utils.RobotUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RobotMoveForward extends RobotAbstractMove implements RobotMove {
    public RobotMoveForward(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public void makeMove() {
        log.info("Robot command: Forward");
        super.kafkaTemplate.send(KafkaUtils.OUTPUT_DATA_PRODUCER,
                RobotUtils.MOVE_FORWARD);
        super.kafkaTemplate.flush();
    }
}
