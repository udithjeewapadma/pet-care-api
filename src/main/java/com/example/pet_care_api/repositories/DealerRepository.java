package com.example.pet_care_api.repositories;

import com.example.pet_care_api.models.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealerRepository extends JpaRepository<Dealer, Long> {
}
