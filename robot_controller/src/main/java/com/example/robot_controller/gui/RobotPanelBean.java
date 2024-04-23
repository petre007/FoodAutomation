package com.example.robot_controller.gui;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RobotPanelBean {

    @Bean
    public void initRobotPanel() {
        new RobotPanel("Food Automation");
    }

}
