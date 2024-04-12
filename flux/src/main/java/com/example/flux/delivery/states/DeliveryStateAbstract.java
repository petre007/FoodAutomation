package com.example.flux.delivery.states;

import com.example.flux.delivery.model.OrderEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class DeliveryStateAbstract {

    private OrderEntity orderEntity;

}
