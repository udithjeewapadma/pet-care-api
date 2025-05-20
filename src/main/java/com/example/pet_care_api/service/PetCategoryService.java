package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreatePetCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetCategoryResponseDTO;
import com.example.pet_care_api.models.PetCategory;

public interface PetCategoryService {

    PetCategory createPetCategory(CreatePetCategoryRequestDTO createPetCategoryRequestDTO);

    PetCategoryResponseDTO findPetCategoryById(Long id);
}
