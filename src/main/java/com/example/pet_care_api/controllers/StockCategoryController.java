package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateStockCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.StockCategoryResponseDTO;
import com.example.pet_care_api.models.StockCategory;
import com.example.pet_care_api.service.StockCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stockCategories")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class StockCategoryController {


    private final StockCategoryService stockCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockCategoryResponseDTO createStockCategory(
            @RequestBody CreateStockCategoryRequestDTO createStockCategoryRequestDTO) {

        StockCategory stockCategory= stockCategoryService.createStockCategory(createStockCategoryRequestDTO);

        StockCategoryResponseDTO stockCategoryResponseDTO = new StockCategoryResponseDTO();
        stockCategoryResponseDTO.setId(stockCategory.getId());
        stockCategoryResponseDTO.setCategoryName(stockCategory.getCategoryName());
        return stockCategoryResponseDTO;

    }

    @GetMapping("/{stock-category-id}")
    public StockCategoryResponseDTO getStockCategoryById(@PathVariable("stock-category-id") Long stockCategoryId) {
        return stockCategoryService.findStockCategoryById(stockCategoryId);
    }

    @GetMapping
    public List<StockCategoryResponseDTO> getAllStockCategories() {
        return stockCategoryService.findAllStockCategories();
    }

    @DeleteMapping("/{stock-category-id}")
    public void deleteStockCategoryById(@PathVariable("stock-category-id") Long stockCategoryId) {
        stockCategoryService.deleteStockCategoryById(stockCategoryId);
    }

    @PutMapping("/{stock-category-id}")
    public StockCategoryResponseDTO updateStockCategory(
            @PathVariable("stock-category-id") Long id,
            @RequestBody CreateStockCategoryRequestDTO createStockCategoryRequestDTO) {

        StockCategory stockCategory = stockCategoryService.updateStockCategory(id, createStockCategoryRequestDTO);
        StockCategoryResponseDTO stockCategoryResponseDTO = new StockCategoryResponseDTO();
        stockCategoryResponseDTO.setId(stockCategory.getId());
        stockCategoryResponseDTO.setCategoryName(stockCategory.getCategoryName());
        return stockCategoryResponseDTO;
    }
}
