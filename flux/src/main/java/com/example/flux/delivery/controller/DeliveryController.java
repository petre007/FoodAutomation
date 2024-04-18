package com.example.flux.delivery.controller;

import com.example.flux.delivery.service.DeliveryService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/update_state")
    public ResponseEntity<String> updateDeliveryState(@RequestHeader String token,
            @RequestBody UpdateDeliveryStateRequestBody requestBody)
            throws NoGrantedAuthorityException {
        this.deliveryService.deliveryFlow(requestBody.getOrderEntity(),
                requestBody.getStates(),
                token);
        return ResponseEntity.ok("Delivery state updated");
    }

}
