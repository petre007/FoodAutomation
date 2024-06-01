package com.example.flux.parameters.service;


import com.example.flux.parameters.model.ParametersEntity;
import com.example.flux.parameters.repository.ParametersRepository;
import com.example.flux.parameters.utils.Utils;
import com.example.flux.security.config.JwtService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import com.example.flux.user.model.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParametersService {

    private final ParametersRepository parametersRepository;
    private final JwtService jwtService;

    public List<ParametersEntity> getAll(String token)
            throws NoGrantedAuthorityException {
        this.jwtService.checkRole(token, Roles.ROLE_ADMIN);
        return this.parametersRepository.findAll();
    }

    @Transactional
    public void updateParameterValue(String token ,ParametersEntity parametersEntity)
            throws NoGrantedAuthorityException, IllegalArgumentException {
        this.jwtService.checkRole(token, Roles.ROLE_ADMIN);
        if (Utils.validateIntegerValue(parametersEntity.getValue())) {
            this.parametersRepository.save(parametersEntity);
        }
    }

}
