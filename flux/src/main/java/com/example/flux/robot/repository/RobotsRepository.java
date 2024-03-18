package com.example.flux.robot.repository;

import com.example.flux.robot.model.RobotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotsRepository extends JpaRepository<RobotEntity, Integer> {
}
