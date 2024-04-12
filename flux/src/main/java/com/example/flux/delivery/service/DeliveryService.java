package com.example.flux.delivery.service;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.model.States;
import com.example.flux.delivery.repository.OrderRepository;
import com.example.flux.delivery.states.DeliveryContext;
import com.example.flux.delivery.states.DeliveryDeliveringState;
import com.example.flux.delivery.states.DeliveryInProgressState;
import com.example.flux.delivery.states.DeliveryPendingState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {

    @Transactional
    public void deliveryFlow(OrderEntity orderEntity, States states) {
        DeliveryContext context = new DeliveryContext();

        switch (states) {
            case PENDING -> context.setCurrentState(new DeliveryPendingState(orderEntity));
            case IN_PROGRESS -> context.setCurrentState(new DeliveryInProgressState(orderEntity));
            case DELIVERING -> context.setCurrentState(new DeliveryDeliveringState(orderEntity));
            default -> log.info(states + " is not a valid state");
        }

        context.updateDeliveryStatus();
    }

}
