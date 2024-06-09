package com.example.flux.user.controller;

import com.example.flux.user.model.UserModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserReqBody {

    private UserModel userModel;
    private String roomName;

}
