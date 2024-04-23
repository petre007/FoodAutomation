package com.example.robot_controller.commands;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RobotMoveForward extends RobotAbstractMove implements RobotMove {
    @Override
    public void makeMove() {
        System.out.println("Forward");
    }
}
