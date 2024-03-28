package com.example.flux.room.controller;

import com.example.flux.room.model.RoomEntity;
import com.example.flux.room.service.RoomService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/get_all_rooms/")
    public ResponseEntity<List<RoomEntity>> getAllRooms(@RequestBody String token)
            throws NoGrantedAuthorityException {
        return ResponseEntity.ok(roomService.getAllRooms(token));
    }

    @PostMapping("/assign_room/")
    public ResponseEntity<String> assignRoomToUser(@RequestBody AssignRoomRequestBody assignRoomRequestBody)
            throws NoGrantedAuthorityException {
        this.roomService.assignRoomToUser(assignRoomRequestBody.getToken(),
                assignRoomRequestBody.getUsername(),
                assignRoomRequestBody.getRoomEntity());
        return ResponseEntity.ok("Assign room to user successfully");
    }
}
