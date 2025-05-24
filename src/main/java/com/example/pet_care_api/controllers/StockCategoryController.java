package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateStockCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.StockCategoryResponseDTO;
import com.example.pet_care_api.models.StockCategory;
import com.example.pet_care_api.service.StockCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stockCategories")
public class StockCategoryController {

    @Autowired
    private StockCategoryService stockCategoryService;

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
}
