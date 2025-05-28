package com.example.pet_care_api.service.impl.integration;

import com.example.pet_care_api.controllers.dto.request.CreatePetCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetCategoryResponseDTO;
import com.example.pet_care_api.exceptions.PetCategoryNotFoundException;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.repositories.PetCategoryRepository;
import com.example.pet_care_api.service.PetCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetCategoryServiceImplSpringIntegrationTest {

    @Autowired
    private PetCategoryService petCategoryService;

    @Autowired
    private PetCategoryRepository petCategoryRepository;

    @BeforeEach
    void setUp() {
        petCategoryRepository.deleteAll();
    }

    @Test
    void testCreateAndFindPetCategory() {
        CreatePetCategoryRequestDTO dto = new CreatePetCategoryRequestDTO();
        dto.setCategoryName("Rabbit");

        PetCategory created = petCategoryService.createPetCategory(dto);

        PetCategoryResponseDTO found = petCategoryService.findPetCategoryById(created.getId());

        assertEquals("Rabbit", found.getCategoryName());
    }

    @Test
    void testGetAllPetCategories() {
        CreatePetCategoryRequestDTO dto1 = new CreatePetCategoryRequestDTO(); dto1.setCategoryName("Dog");
        CreatePetCategoryRequestDTO dto2 = new CreatePetCategoryRequestDTO(); dto2.setCategoryName("Cat");
        petCategoryService.createPetCategory(dto1);
        petCategoryService.createPetCategory(dto2);

        List<PetCategoryResponseDTO> all = petCategoryService.getAllPetCategories();
        assertEquals(2, all.size());
    }

    @Test
    void testUpdatePetCategory() {
        CreatePetCategoryRequestDTO dto = new CreatePetCategoryRequestDTO();
        dto.setCategoryName("Parrot");

        PetCategory created = petCategoryService.createPetCategory(dto);

        CreatePetCategoryRequestDTO updateDto = new CreatePetCategoryRequestDTO();
        updateDto.setCategoryName("Updated Parrot");

        PetCategory updated = petCategoryService.updatePetCategory(created.getId(), updateDto);

        assertEquals("Updated Parrot", updated.getCategoryName());
    }

    @Test
    void testDeletePetCategory() {
        CreatePetCategoryRequestDTO dto = new CreatePetCategoryRequestDTO();
        dto.setCategoryName("Delete Me");

        PetCategory created = petCategoryService.createPetCategory(dto);
        petCategoryService.deletePetCategoryById(created.getId());

        assertThrows(PetCategoryNotFoundException.class, () -> {
            petCategoryService.findPetCategoryById(created.getId());
        });
    }
}
