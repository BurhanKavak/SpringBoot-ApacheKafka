package com.example.kafkaproducer.service;

import com.example.kafkaproducer.config.Producer;
import com.example.kafkaproducer.model.FoodOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FoodOrderService {

    private final Producer producer;

    public FoodOrderService(Producer producer) {
        this.producer = producer;
    }

    public String createFoodOrder(FoodOrder foodOrder) throws JsonProcessingException {
        return producer.sendMessage(foodOrder);
    }
}
