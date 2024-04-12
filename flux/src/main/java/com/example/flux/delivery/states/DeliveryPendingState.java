package com.example.flux.delivery.states;

import com.example.flux.delivery.model.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeliveryPendingState implements DeliveryState {

    private OrderEntity orderEntity;

    @Override
    public void updateDeliveryStatus() {

    }
}
