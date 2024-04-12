package com.example.flux.room.service;

import com.example.flux.room.model.RoomEntity;
import com.example.flux.room.repository.RoomRepository;
import com.example.flux.security.config.JwtService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import com.example.flux.user.model.Roles;
import com.example.flux.user.model.UserModel;
import com.example.flux.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Transactional
    public List<RoomEntity> getAllRooms(String token) throws NoGrantedAuthorityException {
        this.jwtService.checkRole(token, Roles.ROLE_ADMIN);
        return this.roomRepository.findAll();
    }

    @Transactional
    public RoomEntity getRoomById(Integer id) {
        return this.roomRepository.getReferenceById(id);
    }

    @Transactional
    public boolean isRoomUsed(Integer id) {
        return this.roomRepository.getReferenceById(id).getIsEmpty();
    }

    @Transactional
    public void assignRoomToUser(String token, String user, RoomEntity roomEntity) throws NoGrantedAuthorityException {

       this.jwtService.checkRole(token, Roles.ROLE_ADMIN);

        UserModel userToAssign = this.userRepository.findUserModelByUsername(user)
                .orElseThrow();

        userToAssign.setRoomEntity(roomEntity);

        this.userRepository.save(userToAssign);
    }

    @Transactional
    public void createEmptyRoom(String roomName) {
        this.roomRepository.save(RoomEntity.builder()
                .roomName(roomName)
                .isEmpty(true)
                .build());
    }

    @Transactional
    public void deleteRoomById(Integer id) {
        this.roomRepository.delete(this.getRoomById(id));
    }

    @Transactional
    public void setRoomIsEmpty(RoomEntity roomEntity ,Boolean isEmpty) {
        roomEntity.setIsEmpty(isEmpty);
        this.roomRepository.save(roomEntity);
    }

}
