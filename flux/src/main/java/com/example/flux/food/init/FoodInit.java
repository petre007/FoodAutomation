//package com.example.flux.food.init;
//
//import com.example.flux.food.model.FoodModel;
//import com.example.flux.food.model.Type;
//import com.example.flux.food.repository.FoodRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
//@RequiredArgsConstructor
//@Component
//public class FoodInit {
//
//    private final FoodRepository foodRepository;
//
//    @Bean
//    public void init1() {
//        foodRepository.save(FoodModel.builder()
//                .name("Cafea")
//                .description("Contine cofeina")
//                .image("")
//                .price(1.5)
//                .quantity(100)
//                .type(Type.BREAKFAST)
//                .build());
//
//        foodRepository.save(FoodModel.builder()
//                .name("Clatite cu ciocolata")
//                .description("Contine ou, lapte, ciocolata")
//                .image("")
//                .price(1.5)
//                .quantity(100)
//                .type(Type.DESERT)
//                .build());
//    }
//
//}
