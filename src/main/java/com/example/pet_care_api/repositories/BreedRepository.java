package com.example.pet_care_api.repositories;

import com.example.pet_care_api.models.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreedRepository extends JpaRepository<Breed, Long> {
}
