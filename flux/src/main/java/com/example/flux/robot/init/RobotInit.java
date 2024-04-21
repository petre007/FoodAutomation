//package com.example.flux.robot.init;
//
//import com.example.flux.robot.model.RobotEntity;
//import com.example.flux.robot.repository.RobotsRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class RobotInit {
//
//    private final RobotsRepository robotsRepository;
//
//    @Bean
//    public void init5 () {
//        this.robotsRepository.save(RobotEntity.builder()
//                .connexionString("192.168.1.132")
//                .connexionPort("5000")
//                .build());
//    }
//
//}
