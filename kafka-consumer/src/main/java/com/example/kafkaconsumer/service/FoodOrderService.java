package com.example.kafkaconsumer.service;

import com.example.kafkaconsumer.dto.FoodOrderDto;
import com.example.kafkaconsumer.model.FoodOrder;
import com.example.kafkaconsumer.repository.FoodOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FoodOrderService {

    private final FoodOrderRepository foodOrderRepository;


    public void persistFoodOrder(FoodOrderDto foodOrderDto) {
        FoodOrder foodOrder = new FoodOrder();
        foodOrder.setItem(foodOrderDto.getItem());
        foodOrder.setAmount(foodOrderDto.getAmount());
        FoodOrder persistedFoodOrder = foodOrderRepository.save(foodOrder);

        log.info("Yemek siparişi kaydedildi {}",persistedFoodOrder);
    }

}
