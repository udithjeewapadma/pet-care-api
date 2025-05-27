package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateDealerRequestDTO;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.models.Dealer;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.DealerRepository;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.service.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DealerServiceImpl implements DealerService {

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private PetClinicRepository petClinicRepository;

    @Override
    public Dealer createDealer(CreateDealerRequestDTO createDealerRequestDTO) {

        List<PetClinic> petClinics = petClinicRepository.findAllById(createDealerRequestDTO.getPetClinicIds());

        if (petClinics.isEmpty() || petClinics.size() != createDealerRequestDTO.getPetClinicIds().size()) {
            throw new PetClinicNotFoundException("Some product IDs are invalid");
        }

        Dealer dealer = new Dealer();
        dealer.setDealerName(createDealerRequestDTO.getDealerName());
        dealer.setPhoneNumber(createDealerRequestDTO.getPhoneNumber());
        dealer.setEmail(createDealerRequestDTO.getEmail());
        dealer.setItemName(createDealerRequestDTO.getItemName());

        dealer.setPetClinics(new ArrayList<>(petClinics));

        return dealerRepository.save(dealer);
    }
}
