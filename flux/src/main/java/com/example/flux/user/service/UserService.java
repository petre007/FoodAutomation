package com.example.flux.user.service;

import com.example.flux.security.config.JwtService;
import com.example.flux.user.controller.AuthenticationRequest;
import com.example.flux.user.controller.AuthenticationResponse;
import com.example.flux.user.controller.RegisterRequest;
import com.example.flux.user.model.Roles;
import com.example.flux.user.model.UserModel;
import com.example.flux.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    /**
     *  TODO:Implement different Roms for ROLE_Employee and Role_Admin by default when adding Rooms table and connect it to User
     *
     * */
    public AuthenticationResponse register(RegisterRequest register) {
        UserModel userModel = UserModel.builder()
                .username(register.getUsername())
                .password(passwordEncoder.encode(register.getPassword()))
                .role(Roles.ROLE_ADMIN)
                .build();
        userRepository.save(userModel);
        var jwtToken = jwtService.generateToken(userModel);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse auth(AuthenticationRequest auth) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));

        var userModel = userRepository.findUserModelByUsername(auth.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(userModel);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
