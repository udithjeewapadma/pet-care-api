package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateDealerRequestDTO;
import com.example.pet_care_api.controllers.dto.response.DealerResponseDTO;
import com.example.pet_care_api.controllers.dto.response.PetClinicDTO;
import com.example.pet_care_api.models.Dealer;
import com.example.pet_care_api.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//        dealerResponseDTO.setPetClinics(dealer.getPetClinics().stream()
//                .map(petClinic -> {
//                    PetClinicDTO petClinicDTO = new PetClinicDTO();
//                    petClinicDTO.setId(petClinic.getId());
//                    petClinicDTO.setPetClinicName(petClinic.getClinicName());
//                    return petClinicDTO;
//                })
//                .toList());
        return dealerResponseDTO;
    }

    @GetMapping("/{dealer-id}")
    private DealerResponseDTO getDealerById(@PathVariable("dealer-id") Long dealerId) {
        return dealerService.findDealerById(dealerId);
    }

    @GetMapping
    private List<DealerResponseDTO> findAllDealers() {
        return dealerService.findAllDealers();
    }

    @DeleteMapping("/{dealer-id}")
    private void deleteDealerById(@PathVariable("dealer-id") Long dealerId) {
        dealerService.deleteDealerById(dealerId);
    }

    @PutMapping("/{dealer-id}")
    private DealerResponseDTO updateDealerById(@PathVariable("dealer-id") Long dealerId, @RequestBody CreateDealerRequestDTO createDealerRequestDTO) {

        Dealer updatedDealer = dealerService.updateDealerById(dealerId, createDealerRequestDTO);

        DealerResponseDTO responseDTO = new DealerResponseDTO();
        responseDTO.setId(updatedDealer.getId());
        responseDTO.setDealerName(updatedDealer.getDealerName());
        responseDTO.setPhoneNumber(updatedDealer.getPhoneNumber());
        responseDTO.setEmail(updatedDealer.getEmail());
        responseDTO.setItemName(updatedDealer.getItemName());

        List<PetClinicDTO> petClinicDTOS = updatedDealer.getPetClinics().stream()
                .map(petClinic -> {
                    PetClinicDTO petClinicDTO = new PetClinicDTO();
                    petClinicDTO.setId(petClinic.getId());
                    petClinicDTO.setPetClinicName(petClinic.getClinicName());
                    return petClinicDTO;
                })
                .toList();
        responseDTO.setPetClinics(petClinicDTOS);
        return responseDTO;

    }
}
