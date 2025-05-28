package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateDealerRequestDTO;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.DealerRepository;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class DealerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetClinicRepository petClinicRepository;

    @Autowired
    private DealerRepository dealerRepository;

    private static Long createdDealerId;
    private static Long petClinicId;

    @BeforeAll
    static void init(@Autowired PetClinicRepository petClinicRepository,
                     @Autowired DealerRepository dealerRepository,
                     @Autowired ObjectMapper objectMapper,
                     @Autowired MockMvc mockMvc) throws Exception {
        dealerRepository.deleteAll();
        petClinicRepository.deleteAll();

        PetClinic petClinic = new PetClinic();
        petClinic.setClinicName("Happy Paws Clinic");
        petClinic.setAddress("123 Main St");
        petClinic.setPhoneNumber("1112223333");
        petClinicId = petClinicRepository.save(petClinic).getId();

        CreateDealerRequestDTO request = new CreateDealerRequestDTO();
        request.setDealerName("Test Dealer");
        request.setEmail("test@dealer.com");
        request.setPhoneNumber("1234567890");
        request.setItemName("Dog Food");
        request.setPetClinicIds(Collections.singletonList(petClinicId));

        String response = mockMvc.perform(post("/dealers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        createdDealerId = json.get("id").asLong();
    }

    @Test
    @Order(1)
    void testGetDealerById() throws Exception {
        mockMvc.perform(get("/dealers/{dealer-id}", createdDealerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdDealerId))
                .andExpect(jsonPath("$.dealerName").value("Test Dealer"));
    }

    @Test
    @Order(2)
    void testFindAllDealers() throws Exception {
        mockMvc.perform(get("/dealers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].dealerName", is("Test Dealer")));
    }

    @Test
    @Order(3)
    void testUpdateDealer() throws Exception {
        CreateDealerRequestDTO updateRequest = new CreateDealerRequestDTO();
        updateRequest.setDealerName("Updated Dealer");
        updateRequest.setEmail("updated@dealer.com");
        updateRequest.setPhoneNumber("9876543210");
        updateRequest.setItemName("Cat Food");
        updateRequest.setPetClinicIds(List.of(petClinicId));

        mockMvc.perform(put("/dealers/{dealer-id}", createdDealerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dealerName").value("Updated Dealer"))
                .andExpect(jsonPath("$.itemName").value("Cat Food"));
    }

    @Test
    @Order(4)
    void testDeleteDealer() throws Exception {
        mockMvc.perform(delete("/dealers/{dealer-id}", createdDealerId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/dealers/{dealer-id}", createdDealerId))
                .andExpect(status().isNotFound());
    }
    @Test
    @Order(5)
    void testCreateDealer() throws Exception {
        CreateDealerRequestDTO request = new CreateDealerRequestDTO();
        request.setDealerName("Test Dealer");
        request.setEmail("test@dealer.com");
        request.setPhoneNumber("1234567890");
        request.setItemName("Dog Food");
        request.setPetClinicIds(Collections.singletonList(petClinicId));

        String response = mockMvc.perform(post("/dealers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dealerName").value("Test Dealer"))
                .andReturn().getResponse().getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        createdDealerId = json.get("id").asLong(); // Save the created ID for further tests
    }

}
