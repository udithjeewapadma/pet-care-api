package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreatePetRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetResponseDTO;
import com.example.pet_care_api.models.Pet;

import java.io.IOException;

public interface PetService {

    PetResponseDTO createPet(Long doctorId,
                             Long petCategoryId,
                             Long petOwnerId, CreatePetRequestDTO createPetRequestDTO) throws IOException;
}
