package com.example.flux.food.service;

import com.example.flux.food.model.FoodModel;
import com.example.flux.food.repository.FoodRepository;
import com.example.flux.security.config.JwtService;
import com.example.flux.security.exception.NoGrantedAuthorityException;
import com.example.flux.user.model.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final JwtService jwtService;

    public List<FoodModel> getAllFoods(String token)
            throws NoGrantedAuthorityException {
        this.jwtService.checkRole(token, Roles.ROLE_CLIENT);
        return this.foodRepository.findAll();
    }

    public FoodModel getFoodById(Integer id) {
        return this.foodRepository.getReferenceById(id);
    }

    @Transactional
    public void addFood(String token, FoodModel foodModel) throws NoGrantedAuthorityException {
        this.jwtService.checkRole(token, Roles.ROLE_ADMIN);
        this.foodRepository.save(foodModel);
    }

    @Transactional
    public void deleteFood(String token, FoodModel foodModel) throws NoGrantedAuthorityException {
        this.jwtService.checkRole(token, Roles.ROLE_ADMIN);
        this.foodRepository.delete(foodModel);
    }

}
