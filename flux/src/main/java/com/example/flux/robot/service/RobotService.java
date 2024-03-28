package com.example.flux.robot.service;

import com.example.flux.robot.model.RobotEntity;
import com.example.flux.robot.model.UltrasonicData;
import com.example.flux.robot.repository.RobotsRepository;
import com.example.flux.robot.repository.UltrasonicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RobotService {

    private final RobotsRepository robotsRepository;
    private final UltrasonicRepository ulrasonicRepository;

    public RobotEntity getRobotEntityById(Integer id) {
        return this.robotsRepository.getReferenceById(id);
    }

    @Transactional
    public void collectDataFromUltrasonic(Integer value, Integer id) {
        RobotEntity robotEntity = this.getRobotEntityById(id);
        if (robotEntity.getUltrasonicData().size() == 5000) {
            robotEntity.getUltrasonicData().remove(0);
        }

        UltrasonicData ultrasonicData = UltrasonicData.builder()
                .value(value)
                .robotEntity(robotEntity)
                .build();

        this.ulrasonicRepository.save(ultrasonicData);
        robotEntity.getUltrasonicData().add(ultrasonicData);
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
}
