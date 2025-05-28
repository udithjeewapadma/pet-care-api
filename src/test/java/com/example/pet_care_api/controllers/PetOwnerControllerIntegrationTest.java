package com.example.pet_care_api.controllers;

import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.repositories.PetOwnerRepository;
import com.example.pet_care_api.controllers.dto.request.CreatePetOwnerRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
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
public class PetOwnerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    private Long existingPetOwnerId;

    @BeforeEach
    @Transactional
    public void setup() {
        petOwnerRepository.deleteAll();

        PetOwner petOwner = new PetOwner();
        petOwner.setOwnerName("Existing Owner");
        petOwner.setAddress("Test Address");
        petOwner.setPhoneNumber("123456789");
        petOwner = petOwnerRepository.save(petOwner);

        existingPetOwnerId = petOwner.getId();
    }

    @Test
    public void testCreatePetOwner() throws Exception {
        CreatePetOwnerRequestDTO requestDTO = new CreatePetOwnerRequestDTO();
        requestDTO.setOwnerName("John Doe");
        requestDTO.setAddress("123 Main St");
        requestDTO.setPhoneNumber("555-1234");

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.ownerName", is("John Doe")))
                .andExpect(jsonPath("$.address", is("123 Main St")))
                .andExpect(jsonPath("$.phoneNumber", is("555-1234")));
    }

    @Test
    public void testGetPetOwnerById() throws Exception {
        mockMvc.perform(get("/owners/{pet-owner-id}", existingPetOwnerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingPetOwnerId))
                .andExpect(jsonPath("$.ownerName", is("Existing Owner")));
    }

    @Test
    public void testGetAllPetOwners() throws Exception {
        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(existingPetOwnerId));
    }

    @Test
    public void testDeletePetOwnerById() throws Exception {
        mockMvc.perform(delete("/owners/{pet-owner-id}", existingPetOwnerId))
                .andExpect(status().isOk());

        // Optionally, verify it no longer exists
        mockMvc.perform(get("/owners/{pet-owner-id}", existingPetOwnerId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePetOwner() throws Exception {
        CreatePetOwnerRequestDTO updateRequest = new CreatePetOwnerRequestDTO();
        updateRequest.setOwnerName("Jane Smith");
        updateRequest.setAddress("456 Elm St");
        updateRequest.setPhoneNumber("555-6789");

        mockMvc.perform(put("/owners/{pet-owner-id}", existingPetOwnerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingPetOwnerId))
                .andExpect(jsonPath("$.ownerName", is("Jane Smith")))
                .andExpect(jsonPath("$.address", is("456 Elm St")))
                .andExpect(jsonPath("$.phoneNumber", is("555-6789")));
    }
}
