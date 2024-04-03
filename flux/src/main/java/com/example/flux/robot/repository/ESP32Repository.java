package com.example.flux.robot.repository;

import com.example.flux.robot.model.ESP32Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ESP32Repository extends JpaRepository<ESP32Data, Integer> {

    @Modifying
    @Transactional
    @Query(value = "delete from food_automation.esp32_data " +
            "order by food_automation.esp32_data.id asc " +
            "limit ?1", nativeQuery = true)
    void deleteOldestValue(@Param("limit") int limit);
}
