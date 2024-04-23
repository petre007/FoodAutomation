package com.example.robot_controller.commands;

import com.example.robot_controller.kafka.KafkaUtils;
import com.example.robot_controller.utils.RobotUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
public class RobotMoveStop extends RobotAbstractMove implements RobotMove {
    public RobotMoveStop(KafkaTemplate<String, String> kafkaTemplate) {
        super(kafkaTemplate);
    }

    @Override
    public void makeMove() {
        log.info("Robot command: Stop");
        super.kafkaTemplate.send(KafkaUtils.OUTPUT_DATA_PRODUCER,
                RobotUtils.MOVE_STOP);
        super.kafkaTemplate.flush();
    }
}
