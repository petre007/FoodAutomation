package com.example.flux.robot.cronjob;

import com.example.flux.parameters.repository.ParametersRepository;
import com.example.flux.robot.repository.OutputRepository;
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
    private final ParametersRepository parametersRepository;

    @Async
    @Scheduled(fixedRate = 500)
    public void deleteExceededValue() {
        long totalRecords = this.outputRepository.count();
        long maxLimit = Long.parseLong(this.parametersRepository.findParametersEntityByName("max_limit").getValue());
        if (totalRecords > maxLimit) {
            log.info("OutputData records limit exceeded. Recalibration...");
            this.outputRepository.deleteOldestValue((int) (totalRecords - maxLimit));
        } else {
            log.info("OutputData records did not pass the limit: {}.", maxLimit);
        }
    }

}
