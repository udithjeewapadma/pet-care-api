package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateStockCategoryRequestDTO;
import com.example.pet_care_api.models.StockCategory;

public interface StockCategoryService {

    StockCategory createStockCategory(CreateStockCategoryRequestDTO createStockCategoryRequestDTO);
}
