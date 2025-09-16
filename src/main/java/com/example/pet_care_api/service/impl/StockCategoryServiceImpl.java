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
    public StockCategoryResponseDTO findStockCategoryById(Long id) throws StockCategoryNotFoundException {

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

    @Override
    public void deleteStockCategoryById(Long id) throws StockCategoryNotFoundException {
        if(!stockCategoryRepository.existsById(id)) {
            throw new StockCategoryNotFoundException("Stock Category with ID " + id + " not found.");
        }
        stockCategoryRepository.deleteById(id);
    }

    @Override
    public StockCategory updateStockCategory(Long id, CreateStockCategoryRequestDTO createStockCategoryRequestDTO)
            throws StockCategoryNotFoundException {

        StockCategory existingStockCategory = stockCategoryRepository.findById(id)
                .orElseThrow(() -> new StockCategoryNotFoundException("Stock Category Not Found"));
        existingStockCategory.setCategoryName(createStockCategoryRequestDTO.getCategoryName());
        return stockCategoryRepository.save(existingStockCategory);
    }
}
