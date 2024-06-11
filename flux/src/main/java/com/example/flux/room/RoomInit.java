package com.example.flux.room;

import com.example.flux.room.model.RoomEntity;
import com.example.flux.room.repository.RoomRepository;
import com.example.flux.room.service.RoomService;
import com.example.flux.room.utils.RoomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RoomInit {

    private final RoomRepository roomRepository;
    private final RoomService roomService;


    @Bean
    public void init3() {
        this.roomRepository.save(RoomEntity.builder()
                .roomName(RoomUtils.getAdminRoom())
                .isEmpty(false)
                .build());
        this.roomRepository.save(RoomEntity.builder()
                .roomName(RoomUtils.getEmployeeRoom())
                .isEmpty(false)
                .build());

    }

}
