package com.example.flux.robot.repository;

import com.example.flux.robot.model.Connexions;
import com.example.flux.robot.model.RobotConnexions;
import com.example.flux.robot.model.RobotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RobotsRepository extends JpaRepository<RobotEntity, Integer> {

    @Modifying
    @Transactional
    @Query(value = "update food_automation.robots set food_automation.robots.connected =:#{#connexionState.name()}" +
            " where id = :id ", nativeQuery = true)
    void setConnexionState(@Param("connexionState")Connexions connexionState, @Param("id")Integer id);

    RobotConnexions findRobotConnexionsById(Integer id);

}
