package com.example.flux.robot.service;

import com.example.flux.robot.model.ESP32Data;
import com.example.flux.robot.model.OutputData;
import com.example.flux.robot.model.OutputDataType;
import com.example.flux.robot.model.RobotEntity;
import com.example.flux.robot.model.UltrasonicData;
import com.example.flux.robot.repository.ESP32Repository;
import com.example.flux.robot.repository.OutputRepository;
import com.example.flux.robot.repository.RobotsRepository;
import com.example.flux.robot.repository.UltrasonicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RobotService {

    private final RobotsRepository robotsRepository;
    private final UltrasonicRepository ultrasonicRepository;
    private final ESP32Repository esp32Repository;
    private final OutputRepository outputRepository;

    public RobotEntity getRobotEntityById(Integer id) {
        return this.robotsRepository.getReferenceById(id);
    }

    @Transactional
    public void collectDataFromUltrasonic(Integer value, Integer id) {
        RobotEntity robotEntity = this.getRobotEntityById(id);

        UltrasonicData ultrasonicData = UltrasonicData.builder()
                .value(value)
                .robotEntity(robotEntity)
                .build();

        this.ultrasonicRepository.save(ultrasonicData);
        robotEntity.getUltrasonicData().add(ultrasonicData);
        this.robotsRepository.save(robotEntity);
    }

    @Transactional
    public void collectDataFromESP32(String value, Integer id) {
        RobotEntity robotEntity = this.getRobotEntityById(id);

        ESP32Data esp32Data = ESP32Data.builder()
                .imageBase64(value)
                .robotEntity(robotEntity)
                .build();

        this.esp32Repository.save(esp32Data);
        robotEntity.getEsp32Data().add(esp32Data);
        this.robotsRepository.save(robotEntity);
    }

    @Transactional
    public void collectDataFromOutputCommands(Integer value, Integer id, OutputDataType outputDataType) {
        RobotEntity robotEntity = this.getRobotEntityById(id);

        OutputData outputData = OutputData.builder()
                .value(value)
                .robotEntity(robotEntity)
                .outputDataType(outputDataType)
                .build();

        this.outputRepository.save(outputData);
        robotEntity.getOutputData().add(outputData);
        this.robotsRepository.save(robotEntity);
    }

    @Transactional
    public List<Integer> getDataFromUltrasonic(Integer id) {
        return new ArrayList<>(this.robotsRepository.getReferenceById(id)
                .getUltrasonicData())
                .stream()
                .sorted(Comparator.comparing(UltrasonicData::getId))
                .map(UltrasonicData::getValue)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<String> getDataFromESP32(Integer id) {
        return new ArrayList<>(this.robotsRepository.getReferenceById(id)
                .getEsp32Data())
                .stream()
                .sorted(Comparator.comparing(ESP32Data::getId))
                .map(ESP32Data::getImageBase64)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<Integer> getDataFromOutputCommands(Integer id) {
        return new ArrayList<>(this.robotsRepository.getReferenceById(id)
                .getOutputData())
                .stream()
                .filter(outputData -> outputData.getOutputDataType().equals(OutputDataType.MANUAL))
                .sorted(Comparator.comparing(OutputData::getId))
                .map(OutputData::getValue)
                .collect(Collectors.toList());
    }

    public Map<String, List<?>> getData(Integer id) {
        Map<String, List<?>> robotData = new HashMap<>();
        robotData.put("ultrasonic_data", this.getDataFromUltrasonic(id));
        robotData.put("esp32_data", this.getDataFromESP32(id));
        robotData.put("output_data", this.getDataFromOutputCommands(id));
        return robotData;
    }
}
