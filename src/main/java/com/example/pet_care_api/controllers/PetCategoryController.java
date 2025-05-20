package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreatePetCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetCategoryResponseDTO;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.service.PetCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class PetCategoryController {

    @Autowired
    private PetCategoryService petCategoryService;

    @PostMapping
    private PetCategoryResponseDTO createPetCategory(@RequestBody CreatePetCategoryRequestDTO createPetCategoryRequestDTO) {
        PetCategory petCategory = petCategoryService.createPetCategory(createPetCategoryRequestDTO);
        PetCategoryResponseDTO petCategoryResponseDTO = new PetCategoryResponseDTO();
        petCategoryResponseDTO.setId(petCategory.getId());
        petCategoryResponseDTO.setCategoryName(petCategory.getCategoryName());
        return petCategoryResponseDTO;
    }
}
