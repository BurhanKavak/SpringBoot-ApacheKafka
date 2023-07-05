package com.example.kafkaconsumer.repository;

import com.example.kafkaconsumer.model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrder,Long> {
}
