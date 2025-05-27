package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateDealerRequestDTO;
import com.example.pet_care_api.controllers.dto.response.DealerResponseDTO;
import com.example.pet_care_api.controllers.dto.response.PetClinicDTO;
import com.example.pet_care_api.models.Dealer;
import com.example.pet_care_api.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dealers")
public class DealerController {

    @Autowired
    private DealerService dealerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private DealerResponseDTO createDealer(@RequestBody CreateDealerRequestDTO createDealerRequestDTO) {
        Dealer dealer = dealerService.createDealer(createDealerRequestDTO);
        DealerResponseDTO dealerResponseDTO = new DealerResponseDTO();
        dealerResponseDTO.setId(dealer.getId());
        dealerResponseDTO.setDealerName(dealer.getDealerName());
        dealerResponseDTO.setEmail(dealer.getEmail());
        dealerResponseDTO.setPhoneNumber(dealer.getPhoneNumber());
        dealerResponseDTO.setItemName(dealer.getItemName());

        dealerResponseDTO.setPetClinics(dealer.getPetClinics().stream()
                .map(product -> {
                    PetClinicDTO petClinicDTO = new PetClinicDTO();
                    petClinicDTO.setId(product.getId());
                    petClinicDTO.setPetClinicName(product.getClinicName());
                    return petClinicDTO;
                })
                .toList());
        return dealerResponseDTO;
    }

}
