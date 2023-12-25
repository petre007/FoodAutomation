package com.example.flux.user.controller;

import com.example.flux.room.model.RoomEntity;
import com.example.flux.user.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String password;
    private Roles roles;

}
