package com.example.flux.delivery.states;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeliveryContext implements DeliveryState {

    private DeliveryState currentState;

    @Override
    public void updateDeliveryStatus() {
        this.currentState.updateDeliveryStatus();
    }
}
