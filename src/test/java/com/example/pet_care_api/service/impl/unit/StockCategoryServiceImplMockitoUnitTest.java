package com.example.pet_care_api.service.impl.unit;

import com.example.pet_care_api.controllers.dto.request.CreateStockCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.StockCategoryResponseDTO;
import com.example.pet_care_api.exceptions.StockCategoryNotFoundException;
import com.example.pet_care_api.models.StockCategory;
import com.example.pet_care_api.repositories.StockCategoryRepository;
import com.example.pet_care_api.service.impl.StockCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StockCategoryServiceImplMockitoUnitTest {

    @InjectMocks
    private StockCategoryServiceImpl stockCategoryService;

    @Mock
    private StockCategoryRepository stockCategoryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createStockCategory_success() {
        CreateStockCategoryRequestDTO dto = new CreateStockCategoryRequestDTO();
        dto.setCategoryName("Toys");

        StockCategory saved = new StockCategory();
        saved.setId(1L);
        saved.setCategoryName("Toys");

        when(stockCategoryRepository.save(any())).thenReturn(saved);

        StockCategory result = stockCategoryService.createStockCategory(dto);
        assertEquals("Toys", result.getCategoryName());
    }

    @Test
    void findStockCategoryById_success() throws StockCategoryNotFoundException {
        StockCategory category = new StockCategory();
        category.setId(1L);
        category.setCategoryName("Food");

        when(stockCategoryRepository.findById(1L)).thenReturn(Optional.of(category));

        StockCategoryResponseDTO response = stockCategoryService.findStockCategoryById(1L);
        assertEquals("Food", response.getCategoryName());
    }

    @Test
    void findStockCategoryById_notFound() {
        when(stockCategoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(StockCategoryNotFoundException.class, () -> stockCategoryService.findStockCategoryById(1L));
    }

    @Test
    void findAllStockCategories_success() {
        StockCategory c1 = new StockCategory();
        c1.setId(1L);
        c1.setCategoryName("Accessories");

        StockCategory c2 = new StockCategory();
        c2.setId(2L);
        c2.setCategoryName("Medications");

        when(stockCategoryRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<StockCategoryResponseDTO> result = stockCategoryService.findAllStockCategories();
        assertEquals(2, result.size());
        assertEquals("Accessories", result.get(0).getCategoryName());
    }

    @Test
    void updateStockCategory_success() throws StockCategoryNotFoundException {
        CreateStockCategoryRequestDTO dto = new CreateStockCategoryRequestDTO();
        dto.setCategoryName("Updated");

        StockCategory existing = new StockCategory();
        existing.setId(1L);
        existing.setCategoryName("Old");

        when(stockCategoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(stockCategoryRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        StockCategory updated = stockCategoryService.updateStockCategory(1L, dto);
        assertEquals("Updated", updated.getCategoryName());
    }

    @Test
    void deleteStockCategoryById_success() {
        stockCategoryService.deleteStockCategoryById(1L);
        verify(stockCategoryRepository, times(1)).deleteById(1L);
    }
}
