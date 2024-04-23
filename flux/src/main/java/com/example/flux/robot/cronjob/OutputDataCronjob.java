package com.example.flux.robot.cronjob;

import com.example.flux.robot.repository.OutputRepository;
import com.example.flux.utils.ConnexionUtils;
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
public class OutputDataCronjob {

    private final OutputRepository outputRepository;

    @Async
    @Scheduled(fixedRate = 500)
    public void deleteExceededValue() {
        long totalRecords = this.outputRepository.count();
        if (totalRecords > ConnexionUtils.MAX_LIMIT) {
            log.info("OutputData records limit exceeded. Recalibration...");
            this.outputRepository.deleteOldestValue((int) (totalRecords - ConnexionUtils.MAX_LIMIT));
        } else {
            log.info("OutputData records did not pass the limit: {}.", ConnexionUtils.MAX_LIMIT);
        }
    }

}
