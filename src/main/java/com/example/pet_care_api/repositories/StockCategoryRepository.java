package com.example.pet_care_api.repositories;

import com.example.pet_care_api.models.StockCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockCategoryRepository extends JpaRepository<StockCategory, Long> {
    Long id(Long id);
}
