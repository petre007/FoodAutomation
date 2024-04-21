package com.example.flux.delivery.states;

import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.model.States;
import com.example.flux.delivery.utils.DeliveryUtils;
import com.example.flux.kafka.utils.KafkaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeliveryDeliveringState extends DeliveryStateAbstract implements DeliveryState {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public DeliveryDeliveringState(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void updateDeliveryStatus() throws DeliveryStateException {
        OrderEntity orderEntity = this.getOrderEntity();
        if (!orderEntity.getStates().equals(States.IN_PROGRESS)) {
            throw new DeliveryStateException(DeliveryUtils.deliveryExceptionMessage);
        }
        // sent order to the robot
        orderEntity.setStates(States.DELIVERING);
        this.getOrderRepository().save(orderEntity);
        kafkaTemplate.send(KafkaUtils.ORDERS_DELIVERING, orderEntity.toString());
        kafkaTemplate.flush();
        // when robot reaches the target set order state to DELIVERED

//        orderEntity.setStates(States.DELIVERED);
//        this.getOrderRepository().save(orderEntity);
    }
}
