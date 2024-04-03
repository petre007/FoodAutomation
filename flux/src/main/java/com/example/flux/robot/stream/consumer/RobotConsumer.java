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
    public void listenUltrasonic(String in) {
        try {
            log.info(in + " was collected from the " + KafkaUtils.ULTRASONIC_DATA_CONSUMER + " topic");
            this.robotService.collectDataFromUltrasonic(Integer.parseInt(in), 1);
        } catch (NumberFormatException e) {
            log.error(in + "incorrect value for: " + KafkaUtils.ULTRASONIC_DATA_CONSUMER + " topic");
        }
    }

    @KafkaListener(id = "ESP32", topics = KafkaUtils.ESP32_DATA_CONSUMER)
    public void listenESP32(String in) {
        log.info(in + " was collected from the " + KafkaUtils.ESP32_DATA_CONSUMER + " topic");
    }
}
