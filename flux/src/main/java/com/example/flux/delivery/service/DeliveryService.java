package com.example.flux.delivery.service;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.model.States;
import com.example.flux.delivery.repository.OrderRepository;
import com.example.flux.delivery.states.DeliveryContext;
import com.example.flux.delivery.states.DeliveryDeliveringState;
import com.example.flux.delivery.states.DeliveryInProgressState;
import com.example.flux.delivery.states.DeliveryPendingState;
import com.example.flux.security.config.JwtService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import com.example.flux.user.model.Roles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {

    private final JwtService jwtService;
    private final OrderRepository orderRepository;

    @Transactional
    public void deliveryFlow(OrderEntity orderEntity, States states, String token)
            throws NoGrantedAuthorityException {
        DeliveryContext context = new DeliveryContext();
        context.setOrderEntity(orderEntity);
        context.setOrderRepository(orderRepository);
        switch (states) {
            case PENDING -> {
                this.jwtService.checkRole(token, Roles.ROLE_CLIENT);
                context.setCurrentState(new DeliveryPendingState());
            }
            case IN_PROGRESS -> {
                this.jwtService.checkRole(token, Roles.ROLE_EMPLOYEE);
                context.setCurrentState(new DeliveryInProgressState());
            }
            case DELIVERING -> {
                this.jwtService.checkRole(token, Roles.ROLE_EMPLOYEE);
                context.setCurrentState(new DeliveryDeliveringState());
            }
            default -> log.info(states + " is not a valid state");
        }

        context.updateDeliveryStatus();
    }

}
