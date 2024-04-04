package com.example.flux.config;

import com.example.flux.utils.ConnexionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableAsync
@EnableScheduling
@Slf4j
public class ConnexionsConfig {

    private static final Map<String, Boolean> connexionsMap = new HashMap<>();
    private final RestTemplate restTemplate;

    @Bean
    public void addConnexion() {
        connexionsMap.put(ConnexionUtils.FLOW_CONNEXION, false);
    }

    @Async
    @Scheduled(fixedRate = 1000)
    public void createConnexions() {
        for (Map.Entry<String, Boolean> entry : connexionsMap.entrySet()) {
            if (!entry.getValue()) {
                try {
                    ResponseEntity<String> responseEntity = restTemplate
                            .postForEntity(ConnexionUtils.getURL( ConnexionUtils.FLOW_CONNEXION,
                                    ConnexionUtils.FLOW_PORT, ConnexionUtils.CREATE_CONNEXION_ENDPOINT), HttpEntity.EMPTY, String.class);
                    if (responseEntity.getStatusCode().is2xxSuccessful()) {
                        log.info("Connexion established with: " + ConnexionUtils.getURL( ConnexionUtils.FLOW_CONNEXION,
                                ConnexionUtils.FLOW_PORT, ConnexionUtils.CREATE_CONNEXION_ENDPOINT));
                        entry.setValue(true);
                    } else {
                        log.error("Could not established connection with: " + ConnexionUtils.getURL( ConnexionUtils.FLOW_CONNEXION,
                                ConnexionUtils.FLOW_PORT, ConnexionUtils.CREATE_CONNEXION_ENDPOINT));
                        entry.setValue(false);
                    }

                } catch (Exception e) {
                    log.error("Could not established connection with: " + ConnexionUtils.getURL( ConnexionUtils.FLOW_CONNEXION,
                            ConnexionUtils.FLOW_PORT, ConnexionUtils.CREATE_CONNEXION_ENDPOINT));
                    entry.setValue(false);
                }
            }
        }
    }

}
