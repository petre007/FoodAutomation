package com.example.flux.delivery.states;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.repository.OrderRepository;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class DeliveryStateAbstract {

    private OrderRepository orderRepository;

    private OrderEntity orderEntity;

}
