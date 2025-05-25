package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateStockRequestDTO;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.exceptions.StockCategoryNotFoundException;
import com.example.pet_care_api.models.AvailabilityStatus;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.models.Stock;
import com.example.pet_care_api.models.StockCategory;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.repositories.StockCategoryRepository;
import com.example.pet_care_api.repositories.StockRepository;
import com.example.pet_care_api.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockCategoryRepository stockCategoryRepository;

    @Autowired
    private PetClinicRepository petClinicRepository;


    @Override
    public Stock createStock(Long stockCategoryId, Long petClinicId,
                             CreateStockRequestDTO createStockRequestDTO) {

        StockCategory stockCategory = stockCategoryRepository.findById(stockCategoryId)
                .orElseThrow(() -> new StockCategoryNotFoundException("Stock Category Not Found"));

        PetClinic petClinic = petClinicRepository.findById(petClinicId)
                .orElseThrow(() -> new PetClinicNotFoundException("Pet Clinic Not Found"));

        Stock stock = new Stock();
        stock.setName(createStockRequestDTO.getName());
        stock.setDescription(createStockRequestDTO.getDescription());
        stock.setItemCode(createStockRequestDTO.getItemCode());
        stock.setAvailabilityStatus(AvailabilityStatus.ACTIVE);
        stock.setStockCategory(stockCategory);
        stock.setPetClinic(petClinic);
        return stockRepository.save(stock);
    }
}
