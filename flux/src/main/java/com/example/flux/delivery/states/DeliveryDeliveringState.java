package com.example.flux.delivery.states;

import com.example.flux.connector.service.FlowService;
import com.example.flux.connector.service.HttpMethodsEnum;
import com.example.flux.connector.utils.FlowUtils;
import com.example.flux.delivery.model.OrderEntity;
import com.example.flux.delivery.model.States;
import com.example.flux.delivery.utils.DeliveryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliveryDeliveringState extends DeliveryStateAbstract implements DeliveryState {

    private final FlowService flowService;

    @Autowired
    public DeliveryDeliveringState(FlowService flowService) {
        this.flowService = flowService;
    }

    @Override
    public void updateDeliveryStatus()
            throws DeliveryStateException, UnsupportedOperationException {
        OrderEntity orderEntity = this.getOrderEntity();
        if (!orderEntity.getStates().equals(States.IN_PROGRESS)) {
            throw new DeliveryStateException(DeliveryUtils.deliveryExceptionMessage);
        }

        orderEntity.setStates(States.DELIVERING);
        this.getOrderRepository().save(orderEntity);

        // start robot
        new Thread(() -> {
            flowService.callEndpoint(FlowUtils.FLOW_RL_MODEL, null, HttpMethodsEnum.GET);
            // when robot reaches the target set order state to DELIVERED
            orderEntity.setStates(States.DELIVERED);
            getOrderRepository().save(orderEntity);
        }).start();


    }
}
