package com.example.pet_care_api.repositories;

import com.example.pet_care_api.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
