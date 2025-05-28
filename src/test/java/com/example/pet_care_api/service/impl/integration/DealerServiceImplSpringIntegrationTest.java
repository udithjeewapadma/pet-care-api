package com.example.pet_care_api.service.impl.integration;

import com.example.pet_care_api.controllers.dto.request.CreateDealerRequestDTO;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.DealerRepository;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DealerServiceImplSpringIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private PetClinicRepository petClinicRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long petClinicId;

    @BeforeEach
    void setup() {
        dealerRepository.deleteAll();
        petClinicRepository.deleteAll();

        PetClinic petClinic = new PetClinic();
        petClinic.setClinicName("Happy Pet Clinic");
        petClinicId = petClinicRepository.save(petClinic).getId();
    }

    @Test
    void testCreateAndGetDealerById() throws Exception {
        CreateDealerRequestDTO dto = new CreateDealerRequestDTO();
        dto.setDealerName("John Dealer");
        dto.setPhoneNumber("0712345678");
        dto.setEmail("john@dealer.com");
        dto.setItemName("Pet Food");
        dto.setPetClinicIds(List.of(petClinicId));

        // Create dealer
        String createResponse = mockMvc.perform(post("/dealers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long dealerId = objectMapper.readTree(createResponse).get("id").asLong();

        // Get dealer by ID
        mockMvc.perform(get("/dealers/" + dealerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dealerName").value("John Dealer"))
                .andExpect(jsonPath("$.petClinics[0].petClinicName").value("Happy Pet Clinic"));
    }


    @Test
    void testUpdateDealer() throws Exception {
        CreateDealerRequestDTO dto = new CreateDealerRequestDTO();
        dto.setDealerName("Original Dealer");
        dto.setPhoneNumber("0712345678");
        dto.setEmail("original@dealer.com");
        dto.setItemName("Medicine");
        dto.setPetClinicIds(List.of(petClinicId));

        // Create dealer
        String createResponse = mockMvc.perform(post("/dealers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andReturn().getResponse().getContentAsString();

        Long dealerId = objectMapper.readTree(createResponse).get("id").asLong();

        // Update DTO
        dto.setDealerName("Updated Dealer");

        // Update dealer
        mockMvc.perform(put("/dealers/" + dealerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        // Verify update
        mockMvc.perform(get("/dealers/" + dealerId))
                .andExpect(jsonPath("$.dealerName").value("Updated Dealer"));
    }

    @Test
    void testDeleteDealer() throws Exception {
        CreateDealerRequestDTO dto = new CreateDealerRequestDTO();
        dto.setDealerName("Dealer To Delete");
        dto.setPhoneNumber("0722222222");
        dto.setEmail("delete@dealer.com");
        dto.setItemName("Antibiotics");
        dto.setPetClinicIds(List.of(petClinicId));

        // Create dealer and expect 201 CREATED status
        String createResponse = mockMvc.perform(post("/dealers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long dealerId = objectMapper.readTree(createResponse).get("id").asLong();

        // Delete dealer and expect 200 OK status
        mockMvc.perform(delete("/dealers/" + dealerId))
                .andExpect(status().isOk());

        // Verify dealer not found - expect 404 NOT FOUND
//        mockMvc.perform(get("/dealers/" + dealerId))
//                .andExpect(status().isNotFound());
    }



    @Test
    void testGetAllDealers() throws Exception {
        CreateDealerRequestDTO dto = new CreateDealerRequestDTO();
        dto.setDealerName("Dealer A");
        dto.setPhoneNumber("0700000000");
        dto.setEmail("a@dealer.com");
        dto.setItemName("Snacks");
        dto.setPetClinicIds(List.of(petClinicId));

        // Create dealer
        mockMvc.perform(post("/dealers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        // Get all dealers
        mockMvc.perform(get("/dealers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].dealerName").value("Dealer A"));
    }

}
