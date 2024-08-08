package com.example.flux.user.controller;

import com.example.flux.security.exception.NoGrantedAuthorityException;

import com.example.flux.user.repository.UserInfo;
import com.example.flux.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/get_all")
    public ResponseEntity<List<UserInfo>> getAllUsers(@RequestHeader String token)
            throws NoGrantedAuthorityException {
        return ResponseEntity.ok(this.userService.getAllUsers(token));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestHeader String token,
                                             @RequestBody Integer id)
            throws NoGrantedAuthorityException {
        this.userService.deleteUser(token, id);
        return ResponseEntity.ok("User has been deleted");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestHeader String token,
                                          @RequestBody AddUserReqBody addUserReqBody)
            throws NoGrantedAuthorityException {
        this.userService.addUser(token, addUserReqBody.getUserModel(), addUserReqBody.getRoomName());
        return ResponseEntity.ok("User has been added");
    }
}
