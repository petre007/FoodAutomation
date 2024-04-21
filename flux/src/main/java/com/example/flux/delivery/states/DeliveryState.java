package com.example.flux.delivery.states;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.repository.OrderRepository;
import com.example.flux.room.model.RoomEntity;

public interface DeliveryState {

    void updateDeliveryStatus();

    void setOrderRepository(OrderRepository orderRepository);

    void setOrderEntity(OrderEntity orderEntity);

    void setRoomEntity(RoomEntity roomEntity);
}
