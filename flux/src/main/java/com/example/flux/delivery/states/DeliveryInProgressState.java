package com.example.flux.delivery.states;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.model.States;
import com.example.flux.delivery.utils.DeliveryUtils;

public class DeliveryInProgressState extends DeliveryStateAbstract implements DeliveryState {

    @Override
    public void updateDeliveryStatus() throws DeliveryStateException {
        OrderEntity orderEntity = this.getOrderEntity();
        if (!orderEntity.getStates().equals(States.PENDING)) {
            throw new DeliveryStateException(DeliveryUtils.deliveryExceptionMessage);
        }
        orderEntity.setStates(States.IN_PROGRESS);
        this.getOrderRepository().save(orderEntity);
    }
}
