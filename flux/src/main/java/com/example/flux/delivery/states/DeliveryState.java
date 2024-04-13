package com.example.flux.delivery.states;

import com.example.flux.delivery.repository.OrderRepository;

public interface DeliveryState {

    void updateDeliveryStatus();

    void setOrderRepository(OrderRepository orderRepository);

}
