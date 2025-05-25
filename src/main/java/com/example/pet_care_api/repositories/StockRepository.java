package com.example.pet_care_api.repositories;

import com.example.pet_care_api.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
