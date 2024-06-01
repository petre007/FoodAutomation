package com.example.flux.parameters.init;

import com.example.flux.parameters.model.ParametersEntity;
import com.example.flux.parameters.repository.ParametersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParamInit {

    private final ParametersRepository parametersRepository;

    @Bean
    public void initParameters() {
        this.parametersRepository.save(ParametersEntity.builder()
                .name("max_limit")
                .description("Defines how much data it is stored in the database per category")
                .value("1000")
                .build());
    }

}
