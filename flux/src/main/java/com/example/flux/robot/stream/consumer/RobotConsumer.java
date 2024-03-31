package com.example.flux.robot.stream.consumer;

import com.example.flux.kafka.utils.KafkaUtils;
import com.example.flux.robot.service.RobotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RobotConsumer {

    private final RobotService robotService;

    @KafkaListener(id = "ULTRASONIC", topics = KafkaUtils.ULTRASONIC_DATA_CONSUMER)
    public void listen(String in) {
        log.info(in + " was collected from " + KafkaUtils.ULTRASONIC_DATA_CONSUMER + " topic");
        this.robotService.collectDataFromUltrasonic(Integer.parseInt(in), 1);
    }

}
