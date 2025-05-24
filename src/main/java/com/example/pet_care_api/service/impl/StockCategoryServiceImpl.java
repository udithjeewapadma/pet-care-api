package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateStockCategoryRequestDTO;
import com.example.pet_care_api.models.StockCategory;
import com.example.pet_care_api.repositories.StockCategoryRepository;
import com.example.pet_care_api.service.StockCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockCategoryServiceImpl implements StockCategoryService {

    @Autowired
    private StockCategoryRepository stockCategoryRepository;

    @Override
    public StockCategory createStockCategory(CreateStockCategoryRequestDTO createStockCategoryRequestDTO) {

        StockCategory stockCategory = new StockCategory();
        stockCategory.setCategoryName(createStockCategoryRequestDTO.getCategoryName());
        return stockCategoryRepository.save(stockCategory);
    }
}
