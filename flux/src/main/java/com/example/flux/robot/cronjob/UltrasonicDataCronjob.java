package com.example.flux.robot.cronjob;

import com.example.flux.robot.repository.UltrasonicRepository;
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
public class UltrasonicDataCronjob {

    private final UltrasonicRepository ultrasonicRepository;

    @Async
    @Scheduled(fixedRate = 500)
    public void deleteExceededValue() {
        long totalRecords = this.ultrasonicRepository.count();
        if (totalRecords > RobotUtils.MAX_LIMIT) {
            log.info("UltrasonicData records limit exceeded. Recalibration...");
            this.ultrasonicRepository.deleteOldestValue((int) (totalRecords - RobotUtils.MAX_LIMIT));
        } else {
            log.info("UltraSonicData records did not pass the limit: {}.", RobotUtils.MAX_LIMIT);
        }
    }


}
