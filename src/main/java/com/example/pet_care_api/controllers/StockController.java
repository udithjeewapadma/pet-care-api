package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateStockRequestDTO;
import com.example.pet_care_api.controllers.dto.response.StockResponseDTO;
import com.example.pet_care_api.models.Stock;
import com.example.pet_care_api.service.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
@AllArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponseDTO createStock(@RequestParam Long petClinicId,
                                        @RequestParam Long stockCategoryId,
                                        @RequestBody CreateStockRequestDTO createStockRequestDTO) {
        Stock stock = stockService.createStock(petClinicId,stockCategoryId,createStockRequestDTO);

        StockResponseDTO stockResponseDTO = new StockResponseDTO();
        stockResponseDTO.setId(stock.getId());
        stockResponseDTO.setName(stock.getName());
        stockResponseDTO.setDescription(stock.getDescription());
        stockResponseDTO.setItemCode(stock.getItemCode());
        stockResponseDTO.setAvailabilityStatus(stock.getAvailabilityStatus());
        stockResponseDTO.setStockCategoryId(stockCategoryId);
        stockResponseDTO.setPetClinicId(petClinicId);
        return stockResponseDTO;
    }

}
