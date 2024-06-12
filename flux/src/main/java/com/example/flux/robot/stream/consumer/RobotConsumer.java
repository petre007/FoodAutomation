package com.example.flux.robot.stream.consumer;

import com.example.flux.kafka.utils.KafkaUtils;
import com.example.flux.parameters.repository.ParametersRepository;
import com.example.flux.robot.model.OutputDataType;
import com.example.flux.robot.service.RobotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RobotConsumer {

    private final RobotService robotService;
    private final ParametersRepository parametersRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(id = KafkaUtils.GROUP_ID_ULTRASONIC, topics = KafkaUtils.ULTRASONIC_DATA_CONSUMER)
    public void listenUltrasonic(String in) {
        try {
            log.info(in + " was collected from the " + KafkaUtils.ULTRASONIC_DATA_CONSUMER + " topic");
            this.robotService.collectDataFromUltrasonic(Integer.parseInt(in), 1);
        } catch (NumberFormatException e) {
            log.error(in + " incorrect value for " + KafkaUtils.ULTRASONIC_DATA_CONSUMER + " topic");
        }
    }

    @KafkaListener(id = KafkaUtils.GROUP_ID_ESP32, topics = KafkaUtils.ESP32_DATA_CONSUMER)
    public void listenESP32(String in) {
        log.info("An image was collected from the " + KafkaUtils.ESP32_DATA_CONSUMER + " topic");
        this.robotService.collectDataFromESP32(in, 1);
    }

    @KafkaListener(id = KafkaUtils.GROUP_ID_OUTPUT, topics = KafkaUtils.OUTPUT_DATA_CONSUMER)
    public void listenOutput(String in) {
        try {
            log.info(in + " was collected from the " + KafkaUtils.OUTPUT_DATA_CONSUMER + " topic");
            this.robotService.collectDataFromOutputCommands(Integer.parseInt(in), 1, OutputDataType.MANUAL);
            int value = Integer.parseInt(this.parametersRepository.findParametersEntityByName("manual_control").getValue());
            if (value > 0) {
                kafkaTemplate.send(KafkaUtils.ROBOT_COMMANDS, in);
                kafkaTemplate.flush();
            }
        } catch (NumberFormatException e) {
            log.error(in + " incorrect value for " + KafkaUtils.OUTPUT_DATA_CONSUMER + " topic");
        }
    }

    @KafkaListener(id = KafkaUtils.GROUP_ID_OUTPUT_FLOW, topics = KafkaUtils.OUTPUT_RL_MODEL_CONSUMER)
    public void listenOutputFlow(String in) {
        try {
            log.info(in + " was collected from the " + KafkaUtils.OUTPUT_RL_MODEL_CONSUMER + " topic");
            this.robotService.collectDataFromOutputCommands(Integer.parseInt(in), 1, OutputDataType.AUTONOMOUS);
            int value = Integer.parseInt(this.parametersRepository.findParametersEntityByName("manual_control").getValue());
            if (value == 0) {
                kafkaTemplate.send(KafkaUtils.ROBOT_COMMANDS, in);
                kafkaTemplate.flush();
            }
        } catch (NumberFormatException e) {
            log.error(in + " incorrect value for " + KafkaUtils.OUTPUT_DATA_CONSUMER + " topic");
        }
    }
}
