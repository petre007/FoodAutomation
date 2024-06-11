package com.example.flux.robot.controller;

import com.example.flux.robot.service.RobotService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/robot")
public class RobotController {

    private final RobotService robotService;

    @GetMapping("/data")
    public ResponseEntity<Map<String, List<?>>> getAllData(@RequestHeader Integer id) {
        return ResponseEntity.ok(this.robotService.getData(id));
    }

    @PostMapping("/ultrasonic_data")
    public ResponseEntity<List<Integer>> getUltrasonicData(@RequestHeader String token,
                                                           @RequestBody Integer id)
            throws NoGrantedAuthorityException {
        return ResponseEntity.ok(this.robotService.getDataFromUltrasonic(token, id));
    }

    @PostMapping("/rl_model_train")
    public ResponseEntity<String> startTrainRlModel(@RequestHeader String token)
            throws NoGrantedAuthorityException {
        this.robotService.startRlModel(token);
        return ResponseEntity.ok("Started training rl model");
    }

}
