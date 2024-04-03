package com.example.flux.robot.cronjob;

import com.example.flux.robot.repository.ESP32Repository;
import com.example.flux.robot.utils.RobotUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@RequiredArgsConstructor
@EnableAsync
@EnableScheduling
@Slf4j
public class ESP32DataCronjob {

    private final ESP32Repository esp32Repository;


    @Async
    @Scheduled(fixedRate = 500)
    public void deleteExceededValue() {
        long totalRecords = this.esp32Repository.count();
        if (totalRecords > RobotUtils.MAX_LIMIT) {
            log.info("ESP32Data records limit exceeded. Recalibration...");
            this.esp32Repository.deleteOldestValue((int) (totalRecords - RobotUtils.MAX_LIMIT));
        } else {
            log.info("ESP32Data records did not pass the limit: {}.", RobotUtils.MAX_LIMIT);
        }
    }

}
