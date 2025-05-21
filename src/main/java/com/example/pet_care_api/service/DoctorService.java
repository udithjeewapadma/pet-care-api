package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateDoctorRequestDTO;
import com.example.pet_care_api.models.Doctor;

public interface DoctorService {

    Doctor createDoctor(CreateDoctorRequestDTO createDoctorRequestDTO);
}
