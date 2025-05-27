package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateDealerRequestDTO;
import com.example.pet_care_api.controllers.dto.response.DealerResponseDTO;
import com.example.pet_care_api.models.Dealer;

import java.util.List;

public interface DealerService {

    Dealer createDealer(CreateDealerRequestDTO createDealerRequestDTO);

    DealerResponseDTO findDealerById(Long id);

    List<DealerResponseDTO> findAllDealers();
}
