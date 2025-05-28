package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateOrderRequestDTO;
import com.example.pet_care_api.models.Dealer;
import com.example.pet_care_api.repositories.DealerRepository;
import com.example.pet_care_api.repositories.OrderRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Long dealerId;
    private Long createdOrderId;

    @BeforeAll
    void setup() {
        // Clear previous data
        orderRepository.deleteAll();
        dealerRepository.deleteAll();

        Dealer dealer = new Dealer();
        dealer.setDealerName("Test Dealer");
        dealer = dealerRepository.saveAndFlush(dealer);
        dealerId = dealer.getId();

        assertNotNull(dealerId, "Dealer ID should not be null after saving");
    }

    @Test
    @Order(1)
    void testCreateOrder() throws Exception {
        CreateOrderRequestDTO requestDTO = new CreateOrderRequestDTO();
        requestDTO.setOrderName("Test Order");
        requestDTO.setQuantity(5);
        requestDTO.setTotalAmount(150.0);

        String response = mockMvc.perform(post("/orders")
                        .param("dealerId", dealerId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderName").value("Test Order"))
                .andExpect(jsonPath("$.quantity").value(5))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(response);
        createdOrderId = jsonNode.get("id").asLong();
        assertNotNull(createdOrderId, "Created order ID should not be null");
    }

    @Test
    @Order(2)
    void testFindOrderById() throws Exception {
        assertNotNull(createdOrderId, "createdOrderId must not be null");

        mockMvc.perform(get("/orders/{order-id}", createdOrderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdOrderId))
                .andExpect(jsonPath("$.orderName").value("Test Order"));
    }

    @Test
    @Order(3)
    void testFindAllOrders() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(createdOrderId));
    }

    @Test
    @Order(4)
    void testUpdateOrder() throws Exception {
        CreateOrderRequestDTO updateRequest = new CreateOrderRequestDTO();
        updateRequest.setOrderName("Updated Order");
        updateRequest.setQuantity(10);
        updateRequest.setTotalAmount(300.0);

        mockMvc.perform(put("/orders/{order-id}", createdOrderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdOrderId))
                .andExpect(jsonPath("$.orderName").value("Updated Order"))
                .andExpect(jsonPath("$.quantity").value(10));
    }

    @Test
    @Order(5)
    void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/{order-id}", createdOrderId))
                .andExpect(status().isOk());

        // Confirm deletion - expect 404 or similar, depends on your exception handling
        mockMvc.perform(get("/orders/{order-id}", createdOrderId))
                .andExpect(status().isNotFound());
    }
}
