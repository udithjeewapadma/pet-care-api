package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreatePetOwnerRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetOwnerResponseDTO;
import com.example.pet_care_api.models.PetOwner;

import java.util.List;

public interface PetOwnerService {

    PetOwner createPetOwner(CreatePetOwnerRequestDTO createPetOwnerRequestDTO);

    PetOwnerResponseDTO findPetOwnerById(Long id);

    List<PetOwnerResponseDTO> findAllPetOwners();
}
