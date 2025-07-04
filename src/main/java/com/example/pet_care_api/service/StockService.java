package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateStockRequestDTO;
import com.example.pet_care_api.controllers.dto.response.StockResponseDTO;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.exceptions.StockCategoryNotFoundException;
import com.example.pet_care_api.exceptions.StockNotFoundException;
import com.example.pet_care_api.models.Stock;

import java.util.List;

public interface StockService {

    Stock createStock(Long stockCategoryId,
                      Long petClinicId,
                      CreateStockRequestDTO createStockRequestDTO)
            throws PetClinicNotFoundException, StockCategoryNotFoundException;

    StockResponseDTO findStockById(Long id) throws StockNotFoundException;

    List<StockResponseDTO> findAllStocks();

    void deleteStockById(Long id);

    Stock updateStock(Long id,CreateStockRequestDTO createStockRequestDTO) throws StockNotFoundException;
}
