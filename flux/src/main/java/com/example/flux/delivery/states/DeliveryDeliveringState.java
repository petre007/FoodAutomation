package com.example.flux.delivery.states;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.model.States;

public class DeliveryDeliveringState extends DeliveryStateAbstract implements DeliveryState {

    @Override
    public void updateDeliveryStatus() {
        OrderEntity orderEntity = this.getOrderEntity();
        orderEntity.setStates(States.DELIVERING);
        this.getOrderRepository().save(orderEntity);
        // sent order to the robot

        // when robot reaches the target set order state to DELIVERED

        orderEntity.setStates(States.DELIVERED);
        this.getOrderRepository().save(orderEntity);
    }
}
