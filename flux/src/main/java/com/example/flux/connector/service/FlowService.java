package com.example.flux.connector.service;

import com.example.flux.connector.model.ComponentsEntity;
import com.example.flux.connector.repository.ComponentsRepository;
import com.example.flux.connector.utils.FlowUtils;
import com.example.flux.utils.ConnexionUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlowService implements ComponentsServiceInt {

    private final ComponentsRepository componentsRepository;
    private final RestTemplate restTemplate;


    @Override
    public void callEndpoint(String endpoint, Map<?, ?> requestBody, HttpMethodsEnum httpMethod)
            throws UnsupportedOperationException {

        ComponentsEntity flowEntity = this.componentsRepository.getComponentsEntityByName(FlowUtils.FLOW);

        HttpEntity<?> httpEntity;

        if (requestBody == null) {
            httpEntity = HttpEntity.EMPTY;
        } else {
            httpEntity = new HttpEntity<>(requestBody);
        }
        ResponseEntity<String> responseEntity = null;

        switch (httpMethod) {
            case GET -> responseEntity = this.restTemplate.getForEntity(ConnexionUtils.getURL(flowEntity.getAddress(), flowEntity.getPort(), endpoint), String.class);
            case POST -> responseEntity = this.restTemplate.postForEntity(ConnexionUtils.getURL(flowEntity.getAddress(), flowEntity.getName(), endpoint), httpEntity, String.class);
        }

        if (responseEntity == null) {
            throw new UnsupportedOperationException("Operation " + httpMethod + " is currently unavailable");
        }

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            log.info("Flow endpoint " + endpoint + " was successfully called.");
        } else {
            log.error("Flow endpoint " + endpoint + " was not successfully called.");
        }
    }
}
