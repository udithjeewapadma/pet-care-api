package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateDealerRequestDTO;
import com.example.pet_care_api.models.Dealer;

public interface DealerService {

    Dealer createDealer(CreateDealerRequestDTO createDealerRequestDTO);
}
