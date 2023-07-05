package com.example.kafkaconsumer.dto;

import lombok.Data;

@Data
public class FoodOrderDto {

    private String item;

    private Double amount;
}
