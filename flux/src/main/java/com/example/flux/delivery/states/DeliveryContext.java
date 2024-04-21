package com.example.flux.delivery.states;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeliveryContext extends DeliveryStateAbstract implements DeliveryState {

    private DeliveryState currentState;

    @Override
    public void updateDeliveryStatus()
            throws DeliveryStateException {
        this.currentState.setRoomEntity(this.getRoomEntity());
        this.currentState.setOrderRepository(this.getOrderRepository());
        this.currentState.setOrderEntity(this.getOrderEntity());
        this.currentState.updateDeliveryStatus();
    }
}
