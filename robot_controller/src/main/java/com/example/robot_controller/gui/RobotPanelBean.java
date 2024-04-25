package com.example.robot_controller.gui;

import com.example.robot_controller.kafka.KafkaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class RobotPanelBean {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private RobotPanel robotPanel;


    @Bean
    public void initRobotPanel() {
       this.robotPanel = new RobotPanel(kafkaTemplate);
    }

    @KafkaListener(id = KafkaUtils.GROUP_ID_ULTRASONIC, topics = KafkaUtils.ULTRASONIC_DATA_CONSUMER)
    public void ultrasonicConsumer(String in) {
        log.info("Value from ultrasonic: " + in);
        this.robotPanel.setUltrasonicFieldText(in);
    }

    @KafkaListener(id = KafkaUtils.GROUP_ID_ESP32, topics = KafkaUtils.ESP32_DATA_CONSUMER)
    public void esp32Consumer(String in) throws IOException {
        log.info("Value from esp32: " + in);
        this.robotPanel.setImage(in);
    }

}
