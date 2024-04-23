package com.example.robot_controller.gui;

import com.example.robot_controller.commands.RobotMakeMove;
import com.example.robot_controller.commands.RobotMoveBackward;
import com.example.robot_controller.commands.RobotMoveForward;
import com.example.robot_controller.commands.RobotMoveLeft;
import com.example.robot_controller.commands.RobotMoveRight;
import com.example.robot_controller.commands.RobotMoveStop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RobotPanel extends JFrame {
    private JPanel panel1;
    private JButton leftButton;
    private JButton forwardButton;
    private JButton backwardButton;
    private JButton rightButton;
    private JButton stopButton;

    public RobotPanel(String title) {
        super(title);
        super.setVisible(true);
        super.setSize(new Dimension(768, 480));
        super.setResizable(false);
        super.add(panel1);

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RobotMakeMove.makeMove(new RobotMoveLeft());
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RobotMakeMove.makeMove(new RobotMoveStop());
            }
        });
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RobotMakeMove.makeMove(new RobotMoveRight());
            }
        });
        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RobotMakeMove.makeMove(new RobotMoveForward());
            }
        });
        backwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RobotMakeMove.makeMove(new RobotMoveBackward());
            }
        });


    }

    private void createUIComponents() {

    }
}
