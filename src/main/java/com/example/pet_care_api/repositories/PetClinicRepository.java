package com.example.pet_care_api.repositories;

import com.example.pet_care_api.models.PetClinic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetClinicRepository extends JpaRepository<PetClinic, Long> {
}
