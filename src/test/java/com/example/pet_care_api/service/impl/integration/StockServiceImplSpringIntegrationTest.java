package com.example.pet_care_api.service.impl.integration;

import com.example.pet_care_api.controllers.dto.request.CreateStockRequestDTO;
import com.example.pet_care_api.models.AvailabilityStatus;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.models.Stock;
import com.example.pet_care_api.models.StockCategory;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.repositories.StockCategoryRepository;
import com.example.pet_care_api.repositories.StockRepository;
import com.example.pet_care_api.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class StockServiceImplSpringIntegrationTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockCategoryRepository stockCategoryRepository;

    @Autowired
    private PetClinicRepository petClinicRepository;

    private StockCategory stockCategory;
    private PetClinic petClinic;

    @BeforeEach
    public void setUp() {
        stockCategory = new StockCategory();
        stockCategory.setCategoryName("Food");
        stockCategory = stockCategoryRepository.save(stockCategory);

        petClinic = new PetClinic();
        petClinic.setClinicName("Happy Tails Vet");
        petClinic.setAddress("123 Pet Street");
        petClinic = petClinicRepository.save(petClinic);
    }

    @Test
    public void testCreateAndFindStock() throws Exception {
        CreateStockRequestDTO dto = new CreateStockRequestDTO();
        dto.setName("Dog Food");
        dto.setDescription("High quality dog food");
        dto.setItemCode("DF123");

        Stock savedStock = stockService.createStock(stockCategory.getId(), petClinic.getId(), dto);

        assertNotNull(savedStock.getId());
        assertEquals("Dog Food", savedStock.getName());
        assertEquals(AvailabilityStatus.ACTIVE, savedStock.getAvailabilityStatus());
        assertEquals(stockCategory.getId(), savedStock.getStockCategory().getId());
        assertEquals(petClinic.getId(), savedStock.getPetClinic().getId());

        var found = stockService.findStockById(savedStock.getId());
        assertEquals("DF123", found.getItemCode());
        assertEquals("High quality dog food", found.getDescription());
    }

    @Test
    public void testUpdateStock() throws Exception {
        CreateStockRequestDTO dto = new CreateStockRequestDTO();
        dto.setName("Dog Food");
        dto.setDescription("High quality dog food");
        dto.setItemCode("DF123");

        Stock savedStock = stockService.createStock(stockCategory.getId(), petClinic.getId(), dto);

        CreateStockRequestDTO updateDTO = new CreateStockRequestDTO();
        updateDTO.setName("Updated Food");
        updateDTO.setDescription("Updated description");
        updateDTO.setItemCode("UP123");
        updateDTO.setAvailabilityStatus(AvailabilityStatus.INACTIVE);

        Stock updated = stockService.updateStock(savedStock.getId(), updateDTO);

        assertEquals("Updated Food", updated.getName());
        assertEquals(AvailabilityStatus.INACTIVE, updated.getAvailabilityStatus());
    }

    @Test
    public void testDeleteStock() throws Exception {
        CreateStockRequestDTO dto = new CreateStockRequestDTO();
        dto.setName("Cat Food");
        dto.setDescription("High protein cat food");
        dto.setItemCode("CF456");

        Stock savedStock = stockService.createStock(stockCategory.getId(), petClinic.getId(), dto);
        Long id = savedStock.getId();

        stockService.deleteStockById(id);

        assertFalse(stockRepository.findById(id).isPresent());
    }
}
