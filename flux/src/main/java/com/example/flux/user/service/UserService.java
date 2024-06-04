package com.example.flux.user.service;

import com.example.flux.security.config.JwtService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import com.example.flux.user.model.Roles;
import com.example.flux.user.model.UserModel;
import com.example.flux.user.repository.UserInfo;
import com.example.flux.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    public List<UserInfo> getAllUsers(String token)
            throws NoGrantedAuthorityException {
        this.jwtService.checkRole(token, Roles.ROLE_ADMIN);
        return this.userRepository.findAllUserInfo();
    }

    @Transactional
    public void deleteUser(String token ,Integer id)
            throws NoGrantedAuthorityException {
        this.jwtService.checkRole(token, Roles.ROLE_ADMIN);
        this.userRepository.deleteById(id);
    }

    @Transactional
    public void addUser(String token, UserModel userModel)
            throws NoGrantedAuthorityException {
        this.jwtService.checkRole(token, Roles.ROLE_ADMIN);
        this.userRepository.save(userModel);
    }

}
