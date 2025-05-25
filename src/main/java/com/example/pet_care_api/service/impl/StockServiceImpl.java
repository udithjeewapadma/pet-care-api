package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateStockRequestDTO;
import com.example.pet_care_api.controllers.dto.response.StockResponseDTO;
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
import com.example.pet_care_api.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public StockResponseDTO findStockById(Long id) {

        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new StockNotFoundException("Stock Not Found"));
        StockResponseDTO stockResponseDTO = new StockResponseDTO();
        stockResponseDTO.setId(stock.getId());
        stockResponseDTO.setName(stock.getName());
        stockResponseDTO.setDescription(stock.getDescription());
        stockResponseDTO.setItemCode(stock.getItemCode());
        stockResponseDTO.setAvailabilityStatus(stock.getAvailabilityStatus());
        stockResponseDTO.setStockCategoryId(stock.getStockCategory().getId());
        stockResponseDTO.setPetClinicId(stock.getPetClinic().getId());
        return stockResponseDTO;
    }

    @Override
    public List<StockResponseDTO> findAllStocks() {
        List<Stock> stocks = stockRepository.findAll();
        return stocks.stream().map(stock -> {
            StockResponseDTO stockResponseDTO = new StockResponseDTO();
            stockResponseDTO.setId(stock.getId());
            stockResponseDTO.setName(stock.getName());
            stockResponseDTO.setDescription(stock.getDescription());
            stockResponseDTO.setItemCode(stock.getItemCode());
            stockResponseDTO.setAvailabilityStatus(stock.getAvailabilityStatus());
            stockResponseDTO.setStockCategoryId(stock.getStockCategory().getId());
            stockResponseDTO.setPetClinicId(stock.getPetClinic().getId());
            return stockResponseDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteStockById(Long id) {
        stockRepository.deleteById(id);
    }
}
