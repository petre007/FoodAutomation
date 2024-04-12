package com.example.flux.delivery.states;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DeliveryDeliveringState extends DeliveryStateAbstract implements DeliveryState {

    @Override
    public void updateDeliveryStatus() {

    }
}
