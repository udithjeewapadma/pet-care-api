package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateDoctorRequestDTO;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.PetClinicRepository;
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
public class DoctorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetClinicRepository petClinicRepository;

    private Long petClinicId;

    @BeforeEach
    void setup() {
        // Make sure a PetClinic exists, because doctor creation requires petClinicId
        PetClinic petClinic = new PetClinic();
        petClinic.setClinicName("Test Clinic");
        petClinic = petClinicRepository.save(petClinic);
        petClinicId = petClinic.getId();
    }

    @Test
    void testCreateGetUpdateDeleteDoctor() throws Exception {
        // --- Create Doctor ---
        CreateDoctorRequestDTO createDoctorRequest = new CreateDoctorRequestDTO();
        createDoctorRequest.setDoctorName("Dr. Smith");
        createDoctorRequest.setPhoneNumber(1234567890);
        createDoctorRequest.setQualifications("MD");

        String createResponse = mockMvc.perform(post("/doctors")
                        .param("petClinicId", petClinicId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDoctorRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.doctorName").value("Dr. Smith"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long doctorId = objectMapper.readTree(createResponse).get("id").asLong();

        // --- Get Doctor by ID ---
        mockMvc.perform(get("/doctors/{doctor-id}", doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorName").value("Dr. Smith"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.qualifications").value("MD"));

        // --- Get All Doctors (should contain at least one doctor) ---
        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].doctorName").exists());

        // --- Update Doctor ---
        CreateDoctorRequestDTO updateDoctorRequest = new CreateDoctorRequestDTO();
        updateDoctorRequest.setDoctorName("Dr. John Smith");
        updateDoctorRequest.setPhoneNumber(987654321);
        updateDoctorRequest.setQualifications("PhD");

        mockMvc.perform(put("/doctors/{doctor-id}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDoctorRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctorName").value("Dr. John Smith"))
                .andExpect(jsonPath("$.phoneNumber").value(987654321))
                .andExpect(jsonPath("$.qualifications").value("PhD"));

        // --- Delete Doctor ---
        mockMvc.perform(delete("/doctors/{doctor-id}", doctorId))
                .andExpect(status().isOk());

        // --- Verify deletion: getting doctor now should return 404 ---
        mockMvc.perform(get("/doctors/{doctor-id}", doctorId))
                .andExpect(status().isNotFound());
    }
}
