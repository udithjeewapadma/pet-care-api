package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateDealerRequestDTO;
import com.example.pet_care_api.controllers.dto.response.DealerResponseDTO;
import com.example.pet_care_api.controllers.dto.response.PetClinicDTO;
import com.example.pet_care_api.exceptions.DealerNotFoundException;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.models.Dealer;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.DealerRepository;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.service.DealerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public DealerResponseDTO findDealerById(Long id) {

        Dealer dealer = dealerRepository.findById(id)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found"));
        DealerResponseDTO dealerResponseDTO = new DealerResponseDTO();
        dealerResponseDTO.setId(dealer.getId());
        dealerResponseDTO.setDealerName(dealer.getDealerName());
        dealerResponseDTO.setPhoneNumber(dealer.getPhoneNumber());
        dealerResponseDTO.setEmail(dealer.getEmail());
        dealerResponseDTO.setItemName(dealer.getItemName());

        List<PetClinicDTO> petClinicDTOS = dealer.getPetClinics().stream()
                .map(petClinic -> {
                    PetClinicDTO petClinicDTO = new PetClinicDTO();
                    petClinicDTO.setId(petClinic.getId());
                    petClinicDTO.setPetClinicName(petClinic.getClinicName());
                    return petClinicDTO;
                })
                .toList();

        dealerResponseDTO.setPetClinics(petClinicDTOS);
        return dealerResponseDTO;
    }

    @Override
    @Transactional
    public List<DealerResponseDTO> findAllDealers() {
        List<Dealer> dealers = dealerRepository.findAll();
        return dealers.stream().map(dealer -> {
            DealerResponseDTO dealerResponseDTO = new DealerResponseDTO();
            dealerResponseDTO.setId(dealer.getId());
            dealerResponseDTO.setDealerName(dealer.getDealerName());
            dealerResponseDTO.setPhoneNumber(dealer.getPhoneNumber());
            dealerResponseDTO.setEmail(dealer.getEmail());
            dealerResponseDTO.setItemName(dealer.getItemName());

            List<PetClinicDTO> petClinicDTOS = dealer.getPetClinics().stream()
                    .map(petClinic -> {
                        PetClinicDTO petClinicDTO = new PetClinicDTO();
                        petClinicDTO.setId(petClinic.getId());
                        petClinicDTO.setPetClinicName(petClinic.getClinicName());
                        return petClinicDTO;
                    })
                    .toList();
            dealerResponseDTO.setPetClinics(petClinicDTOS);
            return dealerResponseDTO;

        }).collect(Collectors.toList());
    }
}
