package com.example.flux.delivery.service;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.model.States;
import com.example.flux.delivery.repository.OrderRepository;
import com.example.flux.delivery.states.DeliveryContext;
import com.example.flux.delivery.states.DeliveryDeliveringState;
import com.example.flux.delivery.states.DeliveryInProgressState;
import com.example.flux.delivery.states.DeliveryPendingState;
import com.example.flux.delivery.states.DeliveryStateException;
import com.example.flux.food.model.FoodModel;
import com.example.flux.food.repository.FoodRepository;
import com.example.flux.security.config.JwtService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import com.example.flux.user.model.Roles;
import com.example.flux.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {

    private final JwtService jwtService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;


    @Transactional
    public void deliveryFlow(OrderEntity orderEntity, States states, String token)
            throws NoGrantedAuthorityException, DeliveryStateException {
        DeliveryContext context = new DeliveryContext();

        Set<FoodModel> foodModels = orderEntity.getFoodModelSet().stream()
                .map(foodModel -> foodRepository.findById(foodModel.getId()).orElse(null))
                .collect(Collectors.toSet());

        orderEntity.setFoodModelSet(foodModels);
        context.setOrderEntity(orderEntity);
        context.setOrderRepository(orderRepository);

        switch (states) {
            case PENDING -> {
                this.jwtService.checkRole(token, Roles.ROLE_CLIENT);

                String username = this.jwtService.extractUsername(token);

                context.setRoomEntity(this.userRepository
                        .findUserRoomEntityByUsername(username).getRoomEntity());

                context.setCurrentState(new DeliveryPendingState());
            }
            case IN_PROGRESS -> {
                this.jwtService.checkRole(token, Roles.ROLE_EMPLOYEE);
                context.setCurrentState(new DeliveryInProgressState());
            }
            case DELIVERING -> {
                this.jwtService.checkRole(token, Roles.ROLE_EMPLOYEE);
                context.setCurrentState(new DeliveryDeliveringState(kafkaTemplate));
            }
            default -> log.info(states + " is not a valid state");
        }

        context.updateDeliveryStatus();
    }


    public List<OrderEntity> getAll() {
        return this.orderRepository.findAll();
    }
}
