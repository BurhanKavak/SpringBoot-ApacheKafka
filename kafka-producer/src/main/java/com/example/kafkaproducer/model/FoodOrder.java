package com.example.kafkaproducer.model;

import lombok.Data;

@Data
public class FoodOrder {

    private String item;

    private Double amount;
}
