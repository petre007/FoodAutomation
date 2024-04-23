package com.example.robot_controller.gui;

import com.example.robot_controller.commands.RobotMakeMove;
import com.example.robot_controller.commands.RobotMoveBackward;
import com.example.robot_controller.commands.RobotMoveForward;
import com.example.robot_controller.commands.RobotMoveLeft;
import com.example.robot_controller.commands.RobotMoveRight;
import com.example.robot_controller.commands.RobotMoveStop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RobotPanel extends JFrame {

    private KafkaTemplate<String, String> kafkaTemplate;

    private JPanel panel1;
    private JButton leftButton;
    private JButton forwardButton;
    private JButton backwardButton;
    private JButton rightButton;
    private JButton stopButton;

    @Autowired
    public RobotPanel(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        super.setTitle("Food Automation");
        super.setVisible(true);
        super.setSize(new Dimension(768, 480));
        super.setResizable(false);
        super.add(panel1);

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RobotMakeMove.makeMove(new RobotMoveLeft(kafkaTemplate));
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RobotMakeMove.makeMove(new RobotMoveStop(kafkaTemplate));
            }
        });
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RobotMakeMove.makeMove(new RobotMoveRight(kafkaTemplate));
            }
        });
        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RobotMakeMove.makeMove(new RobotMoveForward(kafkaTemplate));
            }
        });
        backwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RobotMakeMove.makeMove(new RobotMoveBackward(kafkaTemplate));
            }
        });


    }

    private void createUIComponents() {

    }
}