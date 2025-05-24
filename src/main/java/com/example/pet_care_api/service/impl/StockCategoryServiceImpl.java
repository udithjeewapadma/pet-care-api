package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateStockCategoryRequestDTO;
import com.example.pet_care_api.controllers.dto.response.StockCategoryResponseDTO;
import com.example.pet_care_api.exceptions.StockCategoryNotFoundException;
import com.example.pet_care_api.models.StockCategory;
import com.example.pet_care_api.repositories.StockCategoryRepository;
import com.example.pet_care_api.service.StockCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public StockCategoryResponseDTO findStockCategoryById(Long id) {

        StockCategory stockCategory = stockCategoryRepository.findById(id)
                .orElseThrow(() -> new StockCategoryNotFoundException("Stock Category Not Found"));
        StockCategoryResponseDTO stockCategoryResponseDTO = new StockCategoryResponseDTO();
        stockCategoryResponseDTO.setId(stockCategory.getId());
        stockCategoryResponseDTO.setCategoryName(stockCategory.getCategoryName());
        return stockCategoryResponseDTO;
    }

    @Override
    public List<StockCategoryResponseDTO> findAllStockCategories() {
        List<StockCategory> stockCategories = stockCategoryRepository.findAll();
        return stockCategories.stream().map(stockCategory -> {
            StockCategoryResponseDTO stockCategoryResponseDTO = new StockCategoryResponseDTO();
            stockCategoryResponseDTO.setId(stockCategory.getId());
            stockCategoryResponseDTO.setCategoryName(stockCategory.getCategoryName());
            return stockCategoryResponseDTO;
        }).collect(Collectors.toList());
    }
}
