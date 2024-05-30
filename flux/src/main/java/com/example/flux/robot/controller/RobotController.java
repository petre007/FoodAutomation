package com.example.flux.robot.controller;

import com.example.flux.robot.service.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    @GetMapping("/robot/data")
    public ResponseEntity<Map<String, List<?>>> getAllData(@RequestHeader Integer id) {
        return ResponseEntity.ok(this.robotService.getData(id));
    }
}
