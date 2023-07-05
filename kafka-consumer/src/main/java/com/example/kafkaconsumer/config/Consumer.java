package com.example.kafkaconsumer.config;

import com.example.kafkaconsumer.dto.FoodOrderDto;
import com.example.kafkaconsumer.service.FoodOrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class Consumer {


    private static final String orderTopic = "${topic.name}";

    private final ObjectMapper objectMapper;

    private final FoodOrderService foodOrderService;

    @KafkaListener(topics = orderTopic)
    public void consumerMessage(String message) throws JsonProcessingException {
        log.info("mesaj dinlendi(tüketildi) {}",message);

        FoodOrderDto foodOrderDto = objectMapper.readValue(message,FoodOrderDto.class);
        foodOrderService.persistFoodOrder(foodOrderDto);
    }

}
