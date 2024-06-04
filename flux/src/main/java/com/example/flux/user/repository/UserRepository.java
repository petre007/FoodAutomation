package com.example.flux.user.repository;

import com.example.flux.user.model.UserModel;
import com.example.flux.user.model.UserRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    Optional<UserModel> findUserModelByUsername(String username);

    UserRoomEntity findUserRoomEntityByUsername(String username);

    @Query(value = "select food_automation.user.id ,food_automation.user.username, food_automation.user.role from food_automation.user", nativeQuery = true)
    List<UserInfo> findAllUserInfo();
}
