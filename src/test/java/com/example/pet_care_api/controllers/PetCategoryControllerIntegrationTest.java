package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreatePetCategoryRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PetCategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private CreatePetCategoryRequestDTO sampleRequest;

    @BeforeEach
    void setup() {
        sampleRequest = new CreatePetCategoryRequestDTO();
        sampleRequest.setCategoryName("Mammals");
    }

    @Test
    void testCreateGetUpdateDeletePetCategory() throws Exception {
        // Create
        String response = mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryName").value("Mammals"))
                .andReturn().getResponse().getContentAsString();

        Long categoryId = objectMapper.readTree(response).get("id").asLong();

        // Get by ID
        mockMvc.perform(get("/categories/" + categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryId))
                .andExpect(jsonPath("$.categoryName").value("Mammals"));

        // Update
        sampleRequest.setCategoryName("Reptiles");

        mockMvc.perform(put("/categories/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryName").value("Reptiles"));

        // Get All
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // Delete
        mockMvc.perform(delete("/categories/" + categoryId))
                .andExpect(status().isOk());
    }
}
