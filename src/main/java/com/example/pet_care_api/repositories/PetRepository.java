package com.example.pet_care_api.repositories;

import com.example.pet_care_api.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

}
