package com.example.flux.room.service;

import com.example.flux.room.model.RoomEntity;
import com.example.flux.room.repository.RoomRepository;
import com.example.flux.user.model.UserModel;
import com.example.flux.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<RoomEntity> getAllRooms() {
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
    public void assignRoomToUser(String username, RoomEntity roomEntity) {
        UserModel userModel = this.userRepository.findUserModelByUsername(username)
                .orElseThrow();

        userModel.setRoomEntity(roomEntity);

        this.userRepository.save(userModel);
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
