package com.example.flux.robot.repository;

import com.example.flux.robot.model.OutputData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OutputRepository extends JpaRepository<OutputData, Integer> {

    @Modifying
    @Transactional
    @Query(value = "delete from food_automation.output_data " +
            "order by food_automation.output_data.id asc " +
            "limit ?1", nativeQuery = true)
    void deleteOldestValue(@Param("limit") int limit);

}
