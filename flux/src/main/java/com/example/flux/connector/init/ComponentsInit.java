package com.example.flux.connector.init;

import com.example.flux.connector.model.ComponentsEntity;
import com.example.flux.connector.repository.ComponentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComponentsInit {

    private final ComponentsRepository componentsRepository;


    @Bean
    public void initComponents() {
        this.componentsRepository.save(ComponentsEntity.builder()
                .name("flow")
                .address("localhost")
                .port("5000")
                .build());
    }


}
