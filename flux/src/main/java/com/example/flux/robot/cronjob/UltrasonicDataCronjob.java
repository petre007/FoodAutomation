package com.example.flux.robot.cronjob;

import com.example.flux.robot.repository.UltrasonicRepository;
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

    private final static long MAX_LIMIT = 1000;

    @Async
    @Scheduled(fixedRate = 500)
    public void deleteExcededValue() throws InterruptedException {
        long totalRecords = this.ultrasonicRepository.count();
        if (totalRecords > MAX_LIMIT) {
            log.info("UltrasonicData records limit exceeded. Recalibration...");
            this.ultrasonicRepository.deleteOldestValue((int) (totalRecords - MAX_LIMIT));
        } else {
            log.info("UltraSonicData records did not pass the limit.");
        }
    }


}
