package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreatePetClinicRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PetClinicControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateGetUpdateDeletePetClinic() throws Exception {
        // --- Create PetClinic ---
        CreatePetClinicRequestDTO createRequest = new CreatePetClinicRequestDTO();
        createRequest.setClinicName("Happy Pets Clinic");
        createRequest.setAddress("123 Pet St");
        createRequest.setPhoneNumber("555-1234");

        String createResponse = mockMvc.perform(post("/clinics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clinicName").value("Happy Pets Clinic"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long clinicId = objectMapper.readTree(createResponse).get("id").asLong();

        // --- Get PetClinic by ID ---
        mockMvc.perform(get("/clinics/{pet-clinic-id}", clinicId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clinicName").value("Happy Pets Clinic"))
                .andExpect(jsonPath("$.address").value("123 Pet St"))
                .andExpect(jsonPath("$.phoneNumber").value("555-1234"));

        // --- Get all PetClinics ---
        mockMvc.perform(get("/clinics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].clinicName").exists());

        // --- Update PetClinic ---
        CreatePetClinicRequestDTO updateRequest = new CreatePetClinicRequestDTO();
        updateRequest.setClinicName("Happy Pets Updated");
        updateRequest.setAddress("456 Animal Rd");
        updateRequest.setPhoneNumber("555-5678");

        mockMvc.perform(put("/clinics/{pet-clinic-id}", clinicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clinicName").value("Happy Pets Updated"))
                .andExpect(jsonPath("$.address").value("456 Animal Rd"))
                .andExpect(jsonPath("$.phoneNumber").value("555-5678"));

        // --- Delete PetClinic ---
        mockMvc.perform(delete("/clinics/{pet-clinic-id}", clinicId))
                .andExpect(status().isOk());

        // --- Verify Deletion: get should return 404 ---
        mockMvc.perform(get("/clinics/{pet-clinic-id}", clinicId))
                .andExpect(status().isNotFound());
    }
}
