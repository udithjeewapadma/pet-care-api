package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreatePetCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetCategoryResponseDTO;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.service.PetCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class PetCategoryController {

    @Autowired
    private PetCategoryService petCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private PetCategoryResponseDTO createPetCategory(@RequestBody CreatePetCategoryRequestDTO createPetCategoryRequestDTO) {
        PetCategory petCategory = petCategoryService.createPetCategory(createPetCategoryRequestDTO);
        PetCategoryResponseDTO petCategoryResponseDTO = new PetCategoryResponseDTO();
        petCategoryResponseDTO.setId(petCategory.getId());
        petCategoryResponseDTO.setCategoryName(petCategory.getCategoryName());
        return petCategoryResponseDTO;
    }

    @GetMapping("/{pet-category-id}")
    private PetCategoryResponseDTO getPetCategoryById(@PathVariable("pet-category-id") Long petCategoryId) {
        return petCategoryService.findPetCategoryById(petCategoryId);
    }

    @GetMapping
    private List<PetCategoryResponseDTO> getAllPetCategories() {
        return petCategoryService.getAllPetCategories();
    }

    @DeleteMapping("/{pet-category-id}")
    private void deletePetCategoryById(@PathVariable("pet-category-id") Long petCategoryId) {
        petCategoryService.deletePetCategoryById(petCategoryId);
    }

    @PutMapping("/{pet-category-id}")
    private PetCategoryResponseDTO updatePetCategory(@PathVariable("pet-category-id") Long id,
                                                     @RequestBody CreatePetCategoryRequestDTO createPetCategoryRequestDTO) {
        PetCategory petCategory = petCategoryService.updatePetCategory(id, createPetCategoryRequestDTO);
        PetCategoryResponseDTO petCategoryResponseDTO = new PetCategoryResponseDTO();
        petCategoryResponseDTO.setId(petCategory.getId());
        petCategoryResponseDTO.setCategoryName(petCategory.getCategoryName());
        return petCategoryResponseDTO;
    }
}
