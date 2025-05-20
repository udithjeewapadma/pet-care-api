package com.example.pet_care_api.repositories;

import com.example.pet_care_api.models.PetCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetCategoryRepository extends JpaRepository<PetCategory, Long> {
}
