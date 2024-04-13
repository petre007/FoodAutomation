package com.example.flux.delivery.states;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class DeliveryInProgressState extends DeliveryStateAbstract implements DeliveryState {

    @Override
    public void updateDeliveryStatus() {

    }
}
