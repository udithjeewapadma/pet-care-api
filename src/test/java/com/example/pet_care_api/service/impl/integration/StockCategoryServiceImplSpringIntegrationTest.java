package com.example.pet_care_api.service.impl.integration;

import com.example.pet_care_api.controllers.dto.request.CreateStockCategoryRequestDTO;
import com.example.pet_care_api.exceptions.StockCategoryNotFoundException;
import com.example.pet_care_api.models.StockCategory;
import com.example.pet_care_api.repositories.StockCategoryRepository;
import com.example.pet_care_api.service.StockCategoryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StockCategoryServiceImplSpringIntegrationTest {

    @Autowired
    private StockCategoryService stockCategoryService;

    @Autowired
    private StockCategoryRepository stockCategoryRepository;

    private static Long stockCategoryId;

    @Test
    @Order(1)
    void createStockCategory_success() {
        CreateStockCategoryRequestDTO dto = new CreateStockCategoryRequestDTO();
        dto.setCategoryName("Health");

        StockCategory saved = stockCategoryService.createStockCategory(dto);
        stockCategoryId = saved.getId();

        assertNotNull(saved.getId());
        assertEquals("Health", saved.getCategoryName());
    }

    @Test
    @Order(2)
    void findStockCategoryById_success() throws StockCategoryNotFoundException {
        var response = stockCategoryService.findStockCategoryById(stockCategoryId);
        assertEquals("Health", response.getCategoryName());
    }

    @Test
    @Order(3)
    void updateStockCategory_success() throws StockCategoryNotFoundException {
        CreateStockCategoryRequestDTO update = new CreateStockCategoryRequestDTO();
        update.setCategoryName("Updated Health");

        StockCategory updated = stockCategoryService.updateStockCategory(stockCategoryId, update);
        assertEquals("Updated Health", updated.getCategoryName());
    }

    @Test
    @Order(4)
    void deleteStockCategoryById_success() {
        stockCategoryService.deleteStockCategoryById(stockCategoryId);
        assertFalse(stockCategoryRepository.findById(stockCategoryId).isPresent());
    }
}
