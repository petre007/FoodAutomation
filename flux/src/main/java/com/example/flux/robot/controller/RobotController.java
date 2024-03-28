package com.example.flux.robot.controller;

import com.example.flux.robot.model.RobotEntity;
import com.example.flux.robot.service.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    @PostMapping("/robot")
    public ResponseEntity<RobotEntity> getRobotById(@RequestBody Integer id) {
        return ResponseEntity.ok(this.robotService.getRobotEntityById(id));
    }

    @PostMapping("/robot/ultrasonic")
    public ResponseEntity<List<Integer>> getAllDataFromUltrasonic(@RequestBody Integer id) {
        return ResponseEntity.ok(this.robotService.getDataFromUltrasonic(id));
    }
}
