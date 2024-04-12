package com.example.flux.delivery.controller;

import com.example.flux.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/delivery")
    public ResponseEntity<String> updateDeliveryState(UpdateDeliveryStateRequestBody requestBody) {
        this.deliveryService.deliveryFlow(requestBody.getOrderEntity(), requestBody.getStates());
        return ResponseEntity.ok("Delivery state updated");
    }

}
