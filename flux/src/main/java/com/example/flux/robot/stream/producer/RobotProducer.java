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
    private final KafkaTemplate<String, List<byte[]>> kafkaTemplateByte;

    private final RobotService robotService;

    @Async
    @Scheduled(fixedRate = 500)
    public void produceUltrasonicData() {
        String data = this.robotService.getDataFromUltrasonic(1).toString();
        log.info(KafkaUtils.ULTRASONIC_DATA_PRODUCER + " topic produced data");
        kafkaTemplate.send(KafkaUtils.ULTRASONIC_DATA_PRODUCER, data);
        kafkaTemplate.flush();
    }

    @Async
    @Scheduled(fixedRate = 500)
    public void produceESP32Data() {
        List<byte[]> data = this.robotService.getDataFromESP32(1);
        log.info(KafkaUtils.ESP32_DATA_PRODUCER + " topic produced data");
        kafkaTemplateByte.send(KafkaUtils.ESP32_DATA_PRODUCER, data.subList(0, 100));
        kafkaTemplateByte.flush();
    }

}
