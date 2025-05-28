package com.example.pet_care_api.service.impl.integration;

import com.example.pet_care_api.controllers.dto.request.CreateBreedRequestDTO;
import com.example.pet_care_api.models.Breed;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.repositories.BreedRepository;
import com.example.pet_care_api.repositories.PetCategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BreedServiceImplSpringIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetCategoryRepository petCategoryRepository;

    @Autowired
    private BreedRepository breedRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private PetCategory petCategory;

    @BeforeEach
    public void setup() {
        breedRepository.deleteAll();
        petCategoryRepository.deleteAll();

        petCategory = new PetCategory();
        petCategory.setCategoryName("Dog");
        petCategory = petCategoryRepository.save(petCategory);
    }

    @Test
    public void testCreateBreed() throws Exception {
        CreateBreedRequestDTO dto = new CreateBreedRequestDTO();
        dto.setBreedName("Labrador");

        mockMvc.perform(post("/breeds")
                        .param("petCategoryId", petCategory.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.breedName").value("Labrador"))
                .andExpect(jsonPath("$.petCategoryId").value(petCategory.getId()));
    }

    @Test
    public void testGetAllBreeds() throws Exception {
        Breed breed = new Breed();
        breed.setBreedName("Poodle");
        breed.setPetCategory(petCategory);
        breedRepository.save(breed);

        mockMvc.perform(get("/breeds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].breedName").value("Poodle"));
    }

    @Test
    public void testGetBreedById() throws Exception {
        Breed breed = new Breed();
        breed.setBreedName("Beagle");
        breed.setPetCategory(petCategory);
        breed = breedRepository.save(breed);

        mockMvc.perform(get("/breeds/" + breed.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.breedName").value("Beagle"));
    }

    @Test
    public void testDeleteBreedById() throws Exception {
        Breed breed = new Breed();
        breed.setBreedName("Golden Retriever");
        breed.setPetCategory(petCategory);
        breed = breedRepository.save(breed);

        mockMvc.perform(delete("/breeds/" + breed.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateBreed() throws Exception {
        Breed breed = new Breed();
        breed.setBreedName("Bulldog");
        breed.setPetCategory(petCategory);
        breed = breedRepository.save(breed);

        CreateBreedRequestDTO dto = new CreateBreedRequestDTO();
        dto.setBreedName("French Bulldog");

        mockMvc.perform(put("/breeds/" + breed.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.breedName").value("French Bulldog"));
    }
}
