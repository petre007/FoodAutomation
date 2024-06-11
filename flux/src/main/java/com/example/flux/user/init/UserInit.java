package com.example.flux.user.init;

import com.example.flux.room.utils.RoomUtils;
import com.example.flux.user.controller.RegisterRequest;
import com.example.flux.user.model.Roles;
import com.example.flux.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserInit {

    private final AuthService authService;

    @Bean
    public void init2() {
        this.authService.register(RegisterRequest.builder()
                .username("petre")
                .password("petre")
                .roles(Roles.ROLE_ADMIN)
                .build());
        this.authService.register(RegisterRequest.builder()
                .username("mihai")
                .password("mihai")
                .roles(Roles.ROLE_EMPLOYEE)
                .build());
        this.authService.register(RegisterRequest.builder()
                .username("rebeca")
                .password("rebeca")
                .roles(Roles.ROLE_CLIENT)
                .roomName("ROOM1")
                .build());
    }

}
