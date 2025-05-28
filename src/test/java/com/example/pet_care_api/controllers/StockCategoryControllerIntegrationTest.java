package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateStockCategoryRequestDTO;
import com.example.pet_care_api.repositories.StockCategoryRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
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
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // Use this to avoid static vars
public class StockCategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StockCategoryRepository stockCategoryRepository;

    private Long createdId;

    @BeforeAll
    void setup() {
        stockCategoryRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testCreateStockCategory() throws Exception {
        CreateStockCategoryRequestDTO request = new CreateStockCategoryRequestDTO();
        request.setCategoryName("Pet Food");

        String response = mockMvc.perform(post("/stockCategories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryName").value("Pet Food"))
                .andReturn().getResponse().getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        createdId = json.get("id").asLong();
        Assertions.assertNotNull(createdId, "Created ID should not be null");
    }

    @Test
    @Order(2)
    void testGetStockCategoryById() throws Exception {
        Assertions.assertNotNull(createdId, "Created ID must be set from previous test");

        mockMvc.perform(get("/stockCategories/{id}", createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdId))
                .andExpect(jsonPath("$.categoryName").value("Pet Food"));
    }

    @Test
    @Order(3)
    void testGetAllStockCategories() throws Exception {
        mockMvc.perform(get("/stockCategories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].categoryName").value("Pet Food"));
    }

    @Test
    @Order(4)
    void testUpdateStockCategory() throws Exception {
        CreateStockCategoryRequestDTO updateRequest = new CreateStockCategoryRequestDTO();
        updateRequest.setCategoryName("Updated Food");

        mockMvc.perform(put("/stockCategories/{id}", createdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdId))
                .andExpect(jsonPath("$.categoryName").value("Updated Food"));
    }

    @Test
    @Order(5)
    void testDeleteStockCategory() throws Exception {
        mockMvc.perform(delete("/stockCategories/{id}", createdId))
                .andExpect(status().isOk());

        // Confirm deletion returns 404 Not Found
        mockMvc.perform(get("/stockCategories/{id}", createdId))
                .andExpect(status().isNotFound());
    }
}
