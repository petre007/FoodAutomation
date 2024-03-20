package com.example.flux.room.controller;

import com.example.flux.room.model.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AssignRoomRequestBody {

    private String token;
    private String username;
    private RoomEntity roomEntity;

}
