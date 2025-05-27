package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreatePetClinicRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetClinicResponseDTO;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.models.PetClinic;

import java.util.List;

public interface PetClinicService {

    PetClinic createPetClinic(CreatePetClinicRequestDTO createPetClinicRequestDTO);

    PetClinicResponseDTO findPetClinicById(Long id) throws PetClinicNotFoundException;

    List<PetClinicResponseDTO> findAllPetClinics();

    void deletePetClinicById(Long id);

    PetClinic updatePetClinic(Long id, CreatePetClinicRequestDTO createPetClinicRequestDTO)
            throws PetClinicNotFoundException;
}
