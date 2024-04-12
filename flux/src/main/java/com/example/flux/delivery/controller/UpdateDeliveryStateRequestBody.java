package com.example.flux.delivery.controller;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.model.States;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateDeliveryStateRequestBody {

    private String token;
    private OrderEntity orderEntity;
    private States states;

}
