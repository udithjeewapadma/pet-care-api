package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateStockRequestDTO;
import com.example.pet_care_api.models.Stock;

public interface StockService {

    Stock createStock(Long stockCategoryId,
                      Long petClinicId,
                      CreateStockRequestDTO createStockRequestDTO);
}
