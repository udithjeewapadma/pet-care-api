package com.example.pet_care_api.service.impl.unit;

import com.example.pet_care_api.controllers.dto.request.CreateStockRequestDTO;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.exceptions.StockCategoryNotFoundException;
import com.example.pet_care_api.exceptions.StockNotFoundException;
import com.example.pet_care_api.models.AvailabilityStatus;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.models.Stock;
import com.example.pet_care_api.models.StockCategory;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.repositories.StockCategoryRepository;
import com.example.pet_care_api.repositories.StockRepository;
import com.example.pet_care_api.service.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceImplMockitoUnitTest {

    @InjectMocks
    private StockServiceImpl stockService;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockCategoryRepository stockCategoryRepository;

    @Mock
    private PetClinicRepository petClinicRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStock_success() throws PetClinicNotFoundException, StockCategoryNotFoundException {
        Long petClinicId = 1L;
        Long stockCategoryId = 2L;

        CreateStockRequestDTO dto = new CreateStockRequestDTO();
        dto.setName("Pet Medicine");
        dto.setDescription("Pain relief");
        dto.setItemCode("PM001");

        PetClinic clinic = new PetClinic();
        clinic.setId(petClinicId);

        StockCategory category = new StockCategory();
        category.setId(stockCategoryId);

        when(petClinicRepository.findById(petClinicId)).thenReturn(Optional.of(clinic));
        when(stockCategoryRepository.findById(stockCategoryId)).thenReturn(Optional.of(category));
        when(stockRepository.save(any(Stock.class))).thenAnswer(i -> i.getArgument(0));

        Stock created = stockService.createStock(stockCategoryId, petClinicId, dto);

        assertEquals("Pet Medicine", created.getName());
        assertEquals("Pain relief", created.getDescription());
        assertEquals("PM001", created.getItemCode());
        assertEquals(AvailabilityStatus.ACTIVE, created.getAvailabilityStatus());
        assertEquals(clinic, created.getPetClinic());
        assertEquals(category, created.getStockCategory());
    }

    @Test
    void findStockById_success() throws StockNotFoundException {
        PetClinic petClinic = new PetClinic();
        petClinic.setId(1L);

        StockCategory stockCategory = new StockCategory();
        stockCategory.setId(1L);

        Stock stock = new Stock();
        stock.setId(1L);
        stock.setName("Food");
        stock.setDescription("Dog food");
        stock.setItemCode("DOG123");
        stock.setAvailabilityStatus(AvailabilityStatus.ACTIVE);
        stock.setPetClinic(petClinic);
        stock.setStockCategory(stockCategory);

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        var result = stockService.findStockById(1L);

        assertEquals("Food", result.getName());
        assertEquals("DOG123", result.getItemCode());
    }


    @Test
    void updateStock_success() throws StockNotFoundException {
        Stock existing = new Stock();
        existing.setId(1L);
        existing.setName("Old Name");
        existing.setItemCode("OLD001");

        CreateStockRequestDTO dto = new CreateStockRequestDTO();
        dto.setName("New Name");
        dto.setDescription("Updated Desc");
        dto.setItemCode("NEW001");
        dto.setAvailabilityStatus(AvailabilityStatus.INACTIVE);

        when(stockRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(stockRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Stock updated = stockService.updateStock(1L, dto);

        assertEquals("New Name", updated.getName());
        assertEquals("NEW001", updated.getItemCode());
        assertEquals(AvailabilityStatus.INACTIVE, updated.getAvailabilityStatus());
    }

    @Test
    void deleteStockById_success() {
        stockService.deleteStockById(1L);
        verify(stockRepository, times(1)).deleteById(1L);
    }
}
