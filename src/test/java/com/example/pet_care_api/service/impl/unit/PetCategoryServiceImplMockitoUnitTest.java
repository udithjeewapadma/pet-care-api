package com.example.pet_care_api.service.impl.unit;

import com.example.pet_care_api.controllers.dto.request.CreatePetCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetCategoryResponseDTO;
import com.example.pet_care_api.exceptions.PetCategoryNotFoundException;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.repositories.PetCategoryRepository;
import com.example.pet_care_api.service.impl.PetCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetCategoryServiceImplMockitoUnitTest {

    @InjectMocks
    private PetCategoryServiceImpl petCategoryService;

    @Mock
    private PetCategoryRepository petCategoryRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePetCategory() {
        CreatePetCategoryRequestDTO dto = new CreatePetCategoryRequestDTO();
        dto.setCategoryName("Dog");

        PetCategory saved = new PetCategory();
        saved.setId(1L);
        saved.setCategoryName("Dog");

        when(petCategoryRepository.save(any(PetCategory.class))).thenReturn(saved);

        PetCategory result = petCategoryService.createPetCategory(dto);
        assertEquals("Dog", result.getCategoryName());
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindPetCategoryById() throws PetCategoryNotFoundException {
        PetCategory category = new PetCategory();
        category.setId(1L);
        category.setCategoryName("Cat");

        when(petCategoryRepository.findById(1L)).thenReturn(Optional.of(category));

        PetCategoryResponseDTO response = petCategoryService.findPetCategoryById(1L);
        assertEquals("Cat", response.getCategoryName());
    }

    @Test
    void testFindPetCategoryByIdNotFound() {
        when(petCategoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PetCategoryNotFoundException.class,
                () -> petCategoryService.findPetCategoryById(1L));
    }

    @Test
    void testGetAllPetCategories() {
        PetCategory c1 = new PetCategory(); c1.setId(1L); c1.setCategoryName("Cat");
        PetCategory c2 = new PetCategory(); c2.setId(2L); c2.setCategoryName("Dog");

        when(petCategoryRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<PetCategoryResponseDTO> result = petCategoryService.getAllPetCategories();
        assertEquals(2, result.size());
    }

    @Test
    void testUpdatePetCategory() throws PetCategoryNotFoundException {
        PetCategory existing = new PetCategory(); existing.setId(1L); existing.setCategoryName("Old");

        when(petCategoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(petCategoryRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        CreatePetCategoryRequestDTO dto = new CreatePetCategoryRequestDTO();
        dto.setCategoryName("New");

        PetCategory updated = petCategoryService.updatePetCategory(1L, dto);
        assertEquals("New", updated.getCategoryName());
    }

    @Test
    void testDeletePetCategory() {
        doNothing().when(petCategoryRepository).deleteById(1L);
        petCategoryService.deletePetCategoryById(1L);
        verify(petCategoryRepository, times(1)).deleteById(1L);
    }
}
