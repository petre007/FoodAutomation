package com.example.flux.robot.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "robots")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RobotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "connexion_string")
    private String connexionString;

    @Column(name = "serial_port")
    private String serialPort;

    @Column(name = "connexion_port")
    private String connexionPort;

    @Column(name = "connected")
    @Enumerated(EnumType.STRING)
    private Connexions connexions;

    @OneToMany
    @JoinColumn(name = "robot_id")
    private Set<UltrasonicData> ultrasonicData = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "robot_id")
    private Set<ESP32Data> esp32Data = new HashSet<>();

    //add orders oneToMany relationship
}
