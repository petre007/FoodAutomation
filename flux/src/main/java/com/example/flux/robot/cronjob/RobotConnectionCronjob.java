package com.example.flux.robot.cronjob;

import com.example.flux.robot.model.Connexions;
import com.example.flux.robot.model.RobotConnexions;
import com.example.flux.robot.repository.RobotsRepository;
import com.example.flux.robot.utils.RobotUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Configuration
@RequiredArgsConstructor
@EnableAsync
@EnableScheduling
@Slf4j
public class RobotConnectionCronjob {

    private final RobotsRepository robotsRepository;
    private final RestTemplate restTemplate;

    @Async
    @Scheduled(fixedRate = 2000)
    public void createConnexions() {
        long count = this.robotsRepository.count();

        for (int i = 1; i <= count; i++) {
            RobotConnexions robotConnexions = this.robotsRepository.findRobotConnexionsById(i);
            String state;
            try {
                ResponseEntity<String> responseEntity = restTemplate
                        .postForEntity(RobotUtils.getURL(robotConnexions.getConnexionString(),
                                robotConnexions.getConnexionPort(), RobotUtils.CREATE_CONNEXION_ENDPOINT), HttpEntity.EMPTY, String.class);
                if (responseEntity.getStatusCode().is2xxSuccessful()) {
                    state = RobotUtils.CONNEXION_SUCCESSFUL;
                } else {
                    state = RobotUtils.CONNEXION_NOT_SUCCESSFUL;
                }

            } catch (Exception e) {
                state = RobotUtils.CONNEXION_NOT_SUCCESSFUL;
            }
            if (state.equals(RobotUtils.CONNEXION_SUCCESSFUL)) {
                log.info("Connexion established between flux and robot with address: " + robotConnexions.getConnexionString());
                this.robotsRepository.setConnexionState(Connexions.CONNECTED, i);
            } else {
                log.error("Could not establish connexion between flux and robot with address: " + robotConnexions.getConnexionString());
                this.robotsRepository.setConnexionState(Connexions.NOT_CONNECTED, i);
            }
        }
    }

}
