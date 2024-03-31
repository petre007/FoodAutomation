package com.example.flux.robot.stream.producer;

import com.example.flux.kafka.utils.KafkaUtils;
import com.example.flux.robot.service.RobotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@EnableAsync
public class RobotProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final RobotService robotService;

    @Async
    @Scheduled(fixedRate = 500)
    public void produceUltrasonicData() {
        String data = this.robotService.getDataFromUltrasonic(1).toString();
        log.info(KafkaUtils.ULTRASONIC_DATA_PRODUCER+ " topic received data with the value: " + data);
        kafkaTemplate.send(KafkaUtils.ULTRASONIC_DATA_PRODUCER, data);
        kafkaTemplate.flush();
    }

}
