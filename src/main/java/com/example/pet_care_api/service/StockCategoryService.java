package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateStockCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.StockCategoryResponseDTO;
import com.example.pet_care_api.models.StockCategory;

import java.util.List;

public interface StockCategoryService {

    StockCategory createStockCategory(CreateStockCategoryRequestDTO createStockCategoryRequestDTO);

    StockCategoryResponseDTO findStockCategoryById(Long id);

    List<StockCategoryResponseDTO> findAllStockCategories();
}
