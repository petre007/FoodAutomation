package com.example.flux.parameters.controller;

import com.example.flux.parameters.model.ParametersEntity;
import com.example.flux.parameters.service.ParametersService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/param")
public class ParametersController {

    private final ParametersService parametersService;

    @GetMapping("/get_all")
    public ResponseEntity<List<ParametersEntity>> getAll(@RequestHeader String token)
            throws NoGrantedAuthorityException {
        return ResponseEntity.ok(this.parametersService.getAll(token));
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateParametersValue(@RequestHeader String token,
                                                        @RequestBody ParametersEntity parametersEntity)
            throws NoGrantedAuthorityException, IllegalArgumentException {
        log.info("Operation update for the parameter: " + parametersEntity.getName());
        this.parametersService.updateParameterValue(token, parametersEntity);
        return ResponseEntity.ok("Update successful");
    }

}
