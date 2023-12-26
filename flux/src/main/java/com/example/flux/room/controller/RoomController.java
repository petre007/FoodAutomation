package com.example.flux.room.controller;

import com.example.flux.room.model.RoomEntity;
import com.example.flux.room.service.RoomService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/get_all_rooms/{token}")
    public ResponseEntity<List<RoomEntity>> getAllRooms(@PathVariable String token) throws NoGrantedAuthorityException {
        return ResponseEntity.ok(roomService.getAllRooms(token));
    }

    @PostMapping("/assign_room/{token}")
    public ResponseEntity<String> assignRoomToUser(@PathVariable String token,
                                                   @RequestBody String username,
                                                   @RequestBody RoomEntity roomEntity) throws NoGrantedAuthorityException {
        this.roomService.assignRoomToUser(token, username, roomEntity);
        return ResponseEntity.ok("Assign room to user successfully");
    }
}
