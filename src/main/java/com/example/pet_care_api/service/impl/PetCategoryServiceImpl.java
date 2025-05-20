package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreatePetCategoryRequestDTO;
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
}
