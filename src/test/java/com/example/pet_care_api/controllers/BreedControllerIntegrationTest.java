package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateBreedRequestDTO;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BreedControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PetCategoryRepository petCategoryRepository;

    @Autowired
    private BreedRepository breedRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long petCategoryId;

    @BeforeEach
    void setup() {
        breedRepository.deleteAll();
        petCategoryRepository.deleteAll();

        PetCategory category = new PetCategory();
        category.setCategoryName("Dog");
        petCategoryRepository.save(category);
        petCategoryId = category.getId();
    }

    @Test
    void testCreateBreed() throws Exception {
        CreateBreedRequestDTO requestDTO = new CreateBreedRequestDTO();
        requestDTO.setBreedName("Labrador");

        mockMvc.perform(post("/breeds")
                        .param("petCategoryId", petCategoryId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.breedName").value("Labrador"))
                .andExpect(jsonPath("$.petCategoryId").value(petCategoryId));
    }

    @Test
    void testGetAllBreeds() throws Exception {
        // Create one breed to ensure we get at least one
        CreateBreedRequestDTO requestDTO = new CreateBreedRequestDTO();
        requestDTO.setBreedName("Beagle");

        mockMvc.perform(post("/breeds")
                        .param("petCategoryId", petCategoryId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/breeds"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void testGetBreedById() throws Exception {
        CreateBreedRequestDTO requestDTO = new CreateBreedRequestDTO();
        requestDTO.setBreedName("Poodle");

        String response = mockMvc.perform(post("/breeds")
                        .param("petCategoryId", petCategoryId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andReturn().getResponse().getContentAsString();

        Long breedId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/breeds/" + breedId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.breedName").value("Poodle"));
    }

    @Test
    void testUpdateBreed() throws Exception {
        CreateBreedRequestDTO createDTO = new CreateBreedRequestDTO();
        createDTO.setBreedName("Bulldog");

        String response = mockMvc.perform(post("/breeds")
                        .param("petCategoryId", petCategoryId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andReturn().getResponse().getContentAsString();

        Long breedId = objectMapper.readTree(response).get("id").asLong();

        CreateBreedRequestDTO updateDTO = new CreateBreedRequestDTO();
        updateDTO.setBreedName("French Bulldog");

        mockMvc.perform(put("/breeds/" + breedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.breedName").value("French Bulldog"));
    }

    @Test
    void testDeleteBreedById() throws Exception {
        CreateBreedRequestDTO createDTO = new CreateBreedRequestDTO();
        createDTO.setBreedName("Husky");

        String response = mockMvc.perform(post("/breeds")
                        .param("petCategoryId", petCategoryId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andReturn().getResponse().getContentAsString();

        Long breedId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/breeds/" + breedId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/breeds/" + breedId))
                .andExpect(status().is4xxClientError());
    }
}
