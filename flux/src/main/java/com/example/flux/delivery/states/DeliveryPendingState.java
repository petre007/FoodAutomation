package com.example.flux.delivery.states;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.model.States;
import com.example.flux.delivery.utils.DeliveryUtils;

public class DeliveryPendingState extends DeliveryStateAbstract implements DeliveryState {

    @Override
    public void updateDeliveryStatus() throws DeliveryStateException {
        OrderEntity orderEntity = this.getOrderEntity();
        if (!(orderEntity.getStates() == null)) {
            throw new DeliveryStateException(DeliveryUtils.deliveryExceptionMessage);
        }
        orderEntity.setRoomEntity(this.getRoomEntity());
        orderEntity.setStates(States.PENDING);
        this.getOrderRepository().save(orderEntity);
    }
}
