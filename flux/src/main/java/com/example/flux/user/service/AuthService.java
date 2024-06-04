package com.example.flux.user.service;

import com.example.flux.room.model.RoomEntity;
import com.example.flux.room.utils.RoomUtils;
import com.example.flux.security.config.JwtService;
import com.example.flux.user.controller.AuthenticationRequest;
import com.example.flux.user.controller.AuthenticationResponse;
import com.example.flux.user.controller.RegisterRequest;
import com.example.flux.user.model.Roles;
import com.example.flux.user.model.UserModel;
import com.example.flux.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
//    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest register) {
        UserModel userModel = UserModel.builder()
                .username(register.getUsername())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(register.getRoles())
                .build();
        if (userModel.getRole().equals(Roles.ROLE_ADMIN)) {
            userModel.setRoomEntity(RoomEntity.builder()
                    .roomName(RoomUtils.getAdminRoom())
                    .isEmpty(false)
                    .build());
        }
        if (userModel.getRole().equals(Roles.ROLE_EMPLOYEE)) {
            userModel.setRoomEntity(RoomEntity.builder()
                    .roomName(RoomUtils.getEmployeeRoom())
                    .isEmpty(false)
                    .build());
        }
        userRepository.save(userModel);
        var jwtToken = jwtService.generateToken(userModel);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse login(AuthenticationRequest auth) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));

        var userModel = userRepository.findUserModelByUsername(auth.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(userModel);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .roles(userModel.getRole())
                .build();
    }
}
