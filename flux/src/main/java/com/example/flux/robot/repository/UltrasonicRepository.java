package com.example.flux.robot.repository;

import com.example.flux.robot.model.UltrasonicData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UltrasonicRepository extends JpaRepository<UltrasonicData, Integer> {

    @Modifying
    @Transactional
    @Query(value = "delete from food_automation.ultrasonic_data " +
            "order by food_automation.ultrasonic_data.id asc " +
            "limit ?1", nativeQuery = true)
    void deleteOldestValue(@Param("limit") int limit);

}
