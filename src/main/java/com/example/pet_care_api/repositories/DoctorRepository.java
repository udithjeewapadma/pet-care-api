package com.example.pet_care_api.repositories;

import com.example.pet_care_api.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
