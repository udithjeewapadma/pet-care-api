package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateStockRequestDTO;
import com.example.pet_care_api.controllers.dto.response.StockResponseDTO;
import com.example.pet_care_api.models.Stock;

import java.util.List;

public interface StockService {

    Stock createStock(Long stockCategoryId,
                      Long petClinicId,
                      CreateStockRequestDTO createStockRequestDTO);

    StockResponseDTO findStockById(Long id);

    List<StockResponseDTO> findAllStocks();

    void deleteStockById(Long id);
}
