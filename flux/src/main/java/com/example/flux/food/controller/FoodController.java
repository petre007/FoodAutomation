package com.example.flux.food.controller;

import com.example.flux.food.model.FoodModel;
import com.example.flux.food.service.FoodService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food")
public class FoodController {

    private final FoodService foodService;

    @GetMapping("/get_all_foods")
    public ResponseEntity<List<FoodModel>> getAllFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }

    @PostMapping("/add_food")
    public ResponseEntity<String> addFood(@RequestHeader String token,
            @RequestBody AddFoodRequest addFoodRequest)
            throws NoGrantedAuthorityException {
        this.foodService.addFood(token, addFoodRequest.getFoodModel());
        return ResponseEntity.ok("Added food");
    }

}
