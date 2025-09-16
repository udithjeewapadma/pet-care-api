package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreatePetCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetCategoryResponseDTO;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.service.PetCategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class PetCategoryController {

    private final PetCategoryService petCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetCategoryResponseDTO createPetCategory(@Valid @RequestBody CreatePetCategoryRequestDTO createPetCategoryRequestDTO) {
        PetCategory petCategory = petCategoryService.createPetCategory(createPetCategoryRequestDTO);
        PetCategoryResponseDTO petCategoryResponseDTO = new PetCategoryResponseDTO();
        petCategoryResponseDTO.setId(petCategory.getId());
        petCategoryResponseDTO.setCategoryName(petCategory.getCategoryName());
        return petCategoryResponseDTO;
    }

    @GetMapping("/{pet-category-id}")
    public PetCategoryResponseDTO getPetCategoryById(@PathVariable("pet-category-id") Long petCategoryId) {
        return petCategoryService.findPetCategoryById(petCategoryId);
    }

    @GetMapping
    public List<PetCategoryResponseDTO> getAllPetCategories() {
        return petCategoryService.getAllPetCategories();
    }

    @DeleteMapping("/{pet-category-id}")
    public void deletePetCategoryById(@PathVariable("pet-category-id") Long petCategoryId) {
        petCategoryService.deletePetCategoryById(petCategoryId);
    }

    @PutMapping("/{pet-category-id}")
    public PetCategoryResponseDTO updatePetCategory(@PathVariable("pet-category-id") Long id,
                                                     @RequestBody CreatePetCategoryRequestDTO createPetCategoryRequestDTO) {
        PetCategory petCategory = petCategoryService.updatePetCategory(id, createPetCategoryRequestDTO);
        PetCategoryResponseDTO petCategoryResponseDTO = new PetCategoryResponseDTO();
        petCategoryResponseDTO.setId(petCategory.getId());
        petCategoryResponseDTO.setCategoryName(petCategory.getCategoryName());
        return petCategoryResponseDTO;
    }
}
