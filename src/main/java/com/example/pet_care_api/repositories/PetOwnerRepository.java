package com.example.pet_care_api.repositories;

import com.example.pet_care_api.models.PetOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetOwnerRepository extends JpaRepository<PetOwner, Long> {

    // For login
    PetOwner findByUsername(String username);

    // Optional business query
    PetOwner findByOwnerName(String ownerName);
}
