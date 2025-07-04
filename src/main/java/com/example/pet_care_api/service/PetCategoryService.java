package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreatePetCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetCategoryResponseDTO;
import com.example.pet_care_api.exceptions.PetCategoryNotFoundException;
import com.example.pet_care_api.models.PetCategory;

import java.util.List;

public interface PetCategoryService {

    PetCategory createPetCategory(CreatePetCategoryRequestDTO createPetCategoryRequestDTO);

    PetCategoryResponseDTO findPetCategoryById(Long id) throws PetCategoryNotFoundException;

    List<PetCategoryResponseDTO> getAllPetCategories();

    void deletePetCategoryById(Long id);

    PetCategory updatePetCategory(Long id, CreatePetCategoryRequestDTO createPetCategoryRequestDTO)
            throws PetCategoryNotFoundException;
}
