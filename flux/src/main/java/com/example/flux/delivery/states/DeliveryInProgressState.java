package com.example.flux.delivery.states;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.model.States;

public class DeliveryInProgressState extends DeliveryStateAbstract implements DeliveryState {

    @Override
    public void updateDeliveryStatus() {
        OrderEntity orderEntity = this.getOrderEntity();
        orderEntity.setStates(States.IN_PROGRESS);
        this.getOrderRepository().save(orderEntity);
    }
}
