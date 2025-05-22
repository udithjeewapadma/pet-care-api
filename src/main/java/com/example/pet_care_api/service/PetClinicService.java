package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreatePetClinicRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetClinicResponseDTO;
import com.example.pet_care_api.models.PetClinic;

import java.util.List;

public interface PetClinicService {

    PetClinic createPetClinic(CreatePetClinicRequestDTO createPetClinicRequestDTO);

    PetClinicResponseDTO findPetClinicById(Long id);

    List<PetClinicResponseDTO> findAllPetClinics();

    void deletePetClinicById(Long id);
}
