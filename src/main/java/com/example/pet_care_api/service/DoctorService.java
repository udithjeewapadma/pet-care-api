package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateDoctorRequestDTO;
import com.example.pet_care_api.controllers.dto.response.DoctorResponseDTO;
import com.example.pet_care_api.models.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor createDoctor(CreateDoctorRequestDTO createDoctorRequestDTO);

    DoctorResponseDTO findDoctorById(Long id);

    List<DoctorResponseDTO> findAllDoctors();
}
