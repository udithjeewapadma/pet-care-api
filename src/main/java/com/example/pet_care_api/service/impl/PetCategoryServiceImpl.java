package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreatePetCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetCategoryResponseDTO;
import com.example.pet_care_api.exceptions.PetCategoryNotFoundException;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.repositories.PetCategoryRepository;
import com.example.pet_care_api.service.PetCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetCategoryServiceImpl implements PetCategoryService {

    @Autowired
    private PetCategoryRepository petCategoryRepository;

    @Override
    public PetCategory createPetCategory(CreatePetCategoryRequestDTO createPetCategoryRequestDTO) {

        PetCategory petCategory = new PetCategory();
        petCategory.setCategoryName(createPetCategoryRequestDTO.getCategoryName());

        return petCategoryRepository.save(petCategory);
    }

    @Override
    public PetCategoryResponseDTO findPetCategoryById(Long id) {

        PetCategory petCategory = petCategoryRepository.findById(id)
                .orElseThrow( () -> new PetCategoryNotFoundException("Pet Category Not Found"));
        PetCategoryResponseDTO petCategoryResponseDTO = new PetCategoryResponseDTO();
        petCategoryResponseDTO.setId(petCategory.getId());
        petCategoryResponseDTO.setCategoryName(petCategory.getCategoryName());
        return petCategoryResponseDTO;
    }
}
