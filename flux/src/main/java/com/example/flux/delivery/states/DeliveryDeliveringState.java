package com.example.flux.delivery.states;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.model.States;
import com.example.flux.delivery.utils.DeliveryUtils;

public class DeliveryDeliveringState extends DeliveryStateAbstract implements DeliveryState {

    @Override
    public void updateDeliveryStatus() throws DeliveryStateException {
        OrderEntity orderEntity = this.getOrderEntity();
        if (!orderEntity.getStates().equals(States.IN_PROGRESS)) {
            throw new DeliveryStateException(DeliveryUtils.deliveryExceptionMessage);
        }
        orderEntity.setStates(States.DELIVERING);
        this.getOrderRepository().save(orderEntity);
        // sent order to the robot

        // when robot reaches the target set order state to DELIVERED

        orderEntity.setStates(States.DELIVERED);
        this.getOrderRepository().save(orderEntity);
    }
}
