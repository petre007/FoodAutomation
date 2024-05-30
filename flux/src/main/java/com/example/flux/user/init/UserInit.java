package com.example.flux.user.init;

import com.example.flux.room.repository.RoomRepository;
import com.example.flux.user.model.Roles;
import com.example.flux.user.model.UserModel;
import com.example.flux.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserInit{

    private final UserRepository userRepository;

    @Bean
    public void init2() {
        this.userRepository.save(UserModel.builder()
                .username("petre")
                .password("petre")
                .role(Roles.ROLE_ADMIN)
                .build());
        this.userRepository.save(UserModel.builder()
                .username("rebeca")
                .password("rebeca")
                .role(Roles.ROLE_CLIENT)
                .build());
        this.userRepository.save(UserModel.builder()
                .username("mihai")
                .password("mihai")
                .role(Roles.ROLE_EMPLOYEE)
                .build());
    }

}
