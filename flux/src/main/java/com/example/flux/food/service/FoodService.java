package com.example.flux.food.service;

import com.example.flux.food.model.FoodModel;
import com.example.flux.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    @Transactional
    public List<FoodModel> getAllFoods() {
        return this.foodRepository.findAll();
    }

    @Transactional
    public FoodModel getFoodById(Integer id) {
        return this.foodRepository.getReferenceById(id);
    }

    @Transactional
    public void addFood(FoodModel foodModel) {
        this.foodRepository.save(foodModel);
    }

    @Transactional
    public void deleteFood(FoodModel foodModel) {
        this.foodRepository.delete(foodModel);
    }

}
