package com.example.robot_controller.gui;

import com.example.robot_controller.commands.RobotMakeMove;
import com.example.robot_controller.commands.RobotMoveBackward;
import com.example.robot_controller.commands.RobotMoveForward;
import com.example.robot_controller.commands.RobotMoveLeft;
import com.example.robot_controller.commands.RobotMoveRight;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.kafka.core.KafkaTemplate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
public class RobotPanel extends JFrame {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private JPanel panel1;
    private JButton leftButton;
    private JButton forwardButton;
    private JButton backwardButton;
    private JButton rightButton;
    private JButton stopButton;

    private JTextPane ultrasonicField;
    private JLabel imageLabel;

    public RobotPanel(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        super.setTitle("Food Automation");
        super.setVisible(true);
        super.setSize(new Dimension(768, 480));
        super.setResizable(false);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.add(panel1);

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RobotMakeMove.makeMove(new RobotMoveLeft(kafkaTemplate));
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


    public void setUltrasonicFieldText(String text) {
        ultrasonicField.setText("Ultrasonic value: " + text + "cm");
    }

    public void setImage(String imageBase64) throws IOException {
        try {
            byte[] btDataFile = Base64.decodeBase64(imageBase64);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(btDataFile));
            if (image != null) {
                ImageIcon icon = new ImageIcon(image);
                this.imageLabel.setIcon(icon);
            } else {
                log.info("Image was null");
            }
        } catch (Exception e) {
            log.error("Could not convert image");
        }
    }

}
