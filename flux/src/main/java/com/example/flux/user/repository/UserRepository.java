package com.example.flux.user.repository;

import com.example.flux.room.model.RoomEntity;
import com.example.flux.user.model.UserModel;
import com.example.flux.user.model.UserRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findUserModelByUsername(String username);

    UserRoomEntity findUserRoomEntityByUsername(String username);
}
