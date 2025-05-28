package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateBreedRequestDTO;
import com.example.pet_care_api.controllers.dto.response.BreedResponseDTO;
import com.example.pet_care_api.exceptions.BreedNotFoundException;
import com.example.pet_care_api.exceptions.PetCategoryNotFoundException;
import com.example.pet_care_api.models.Breed;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.repositories.BreedRepository;
import com.example.pet_care_api.repositories.PetCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.MockitoAnnotations;

public class BreedServiceImplMockitoUnitTest {

    @Mock
    private BreedRepository breedRepository;

    @Mock
    private PetCategoryRepository petCategoryRepository;

    @InjectMocks
    private BreedServiceImpl breedService;

    private PetCategory petCategory;
    private Breed breed;
    private CreateBreedRequestDTO createBreedRequestDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        petCategory = new PetCategory();
        petCategory.setId(1L);

        breed = new Breed();
        breed.setId(1L);
        breed.setBreedName("Golden Retriever");
        breed.setPetCategory(petCategory);

        createBreedRequestDTO = new CreateBreedRequestDTO();
        createBreedRequestDTO.setBreedName("Golden Retriever");
    }

    @Test
    public void testCreateBreed_Success() throws PetCategoryNotFoundException {
        when(petCategoryRepository.findById(1L)).thenReturn(Optional.of(petCategory));
        when(breedRepository.save(any(Breed.class))).thenReturn(breed);

        Breed result = breedService.createBreed(1L, createBreedRequestDTO);
        assertEquals("Golden Retriever", result.getBreedName());
    }

    @Test
    public void testCreateBreed_CategoryNotFound() {
        when(petCategoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PetCategoryNotFoundException.class,
                () -> breedService.createBreed(1L, createBreedRequestDTO));
    }

    @Test
    public void testGetBreedById_Success() throws BreedNotFoundException {
        when(breedRepository.findById(1L)).thenReturn(Optional.of(breed));

        BreedResponseDTO dto = breedService.getBreedById(1L);
        assertEquals("Golden Retriever", dto.getBreedName());
        assertEquals(1L, dto.getPetCategoryId());
    }

    @Test
    public void testGetBreedById_NotFound() {
        when(breedRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BreedNotFoundException.class, () -> breedService.getBreedById(1L));
    }

    @Test
    public void testGetAllBreeds() {
        when(breedRepository.findAll()).thenReturn(List.of(breed));

        List<BreedResponseDTO> breeds = breedService.getAllBreeds();
        assertEquals(1, breeds.size());
        assertEquals("Golden Retriever", breeds.get(0).getBreedName());
    }

    @Test
    public void testDeleteBreedById() {
        doNothing().when(breedRepository).deleteById(1L);
        breedService.deleteBreedById(1L);
        verify(breedRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testUpdateBreedById_Success() throws BreedNotFoundException {
        when(breedRepository.findById(1L)).thenReturn(Optional.of(breed));
        when(breedRepository.save(any(Breed.class))).thenReturn(breed);

        Breed updatedBreed = breedService.updateBreeById(1L, createBreedRequestDTO);
        assertEquals("Golden Retriever", updatedBreed.getBreedName());
    }

    @Test
    public void testUpdateBreedById_NotFound() {
        when(breedRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BreedNotFoundException.class, () -> breedService.updateBreeById(1L, createBreedRequestDTO));
    }
}

